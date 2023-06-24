package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ExpenseRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ReportingDataRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.AnalyticsServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.entities.Expense;
import com.jmnoland.expensetrackerapi.models.entities.ReportingData;
import com.jmnoland.expensetrackerapi.models.enums.ReportingDataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class AnalyticsService implements AnalyticsServiceInterface {

    private final ExpenseRepositoryInterface expenseRepository;
    private final ReportingDataRepositoryInterface reportingDataRepository;
    private final DateProviderInterface dateProvider;
    @Autowired
    public AnalyticsService(ExpenseRepositoryInterface expenseRepository,
                            ReportingDataRepositoryInterface reportingDataRepository,
                            DateProviderInterface dateProvider
    ) {
        this.expenseRepository = expenseRepository;
        this.reportingDataRepository = reportingDataRepository;
        this.dateProvider = dateProvider;
    }

    public ServiceResponse<String> CalculateMonthlyTotals(String clientId, Date startDate, Date endDate) {
        List<Expense> expenseList = expenseRepository.getExpensesDateBetween(clientId, startDate, endDate);
        if (expenseList.isEmpty()) return new ServiceResponse<>("No expenses found", true);

        List<ReportingData> reportingDataList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expenseList.get(0).getDate());
        String dataTypeString = ReportingDataType.MONTH_TOTAL.toString();

        Float total = 0f;
        for (Expense expense : expenseList) {
            Calendar expenseDate = Calendar.getInstance();
            expenseDate.setTime(expense.getDate());
            if (expenseDate.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)) {
                reportingDataList.add(new ReportingData(
                        UUID.randomUUID().toString(),
                        clientId,
                        total,
                        dataTypeString,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        this.dateProvider.getDateNow().getTime()
                ));
                total = 0f;
                calendar.setTime(expense.getDate());
            }
            total += expense.getAmount();
        }

        this.reportingDataRepository.bulkInsert(reportingDataList);
        return new ServiceResponse<>("Monthly totals created", true);
    }
    public ServiceResponse<String> CalculateThreeMonthAverages(String clientId, Date startDate, Date endDate) {
        ReportingDataType dataType = ReportingDataType.MONTH_TOTAL;
        List<ReportingData> pastDataList = this.reportingDataRepository
                .findReportingDataBetween(clientId, startDate, endDate, dataType);

        List<ReportingData> reportingDataList = new ArrayList<>();
        String dataTypeString = ReportingDataType.THREE_MONTH_AVERAGE.toString();

        for (int i = 0; i < pastDataList.size(); i++) {
            if (i > 1) {
                ReportingData twoMonthPrevData = pastDataList.get(i - 2);
                ReportingData prevMonthData = pastDataList.get(i - 1);
                ReportingData currentMonthData = pastDataList.get(i);

                Float threeMonthAvg = (
                    twoMonthPrevData.getAmount() + prevMonthData.getAmount() + currentMonthData.getAmount()
                ) / 3;
                reportingDataList.add(new ReportingData(
                        UUID.randomUUID().toString(),
                        clientId,
                        threeMonthAvg,
                        dataTypeString,
                        currentMonthData.getYear(),
                        currentMonthData.getMonth(),
                        this.dateProvider.getDateNow().getTime()
                ));
            }
        }

        this.reportingDataRepository.bulkInsert(reportingDataList);
        return new ServiceResponse<>("Three month averages created", true);
    }
    public ServiceResponse<String> CalculateFiveMonthAverages(String clientId, Date startDate, Date endDate) {
        ReportingDataType dataType = ReportingDataType.MONTH_TOTAL;
        List<ReportingData> pastDataList = this.reportingDataRepository
                .findReportingDataBetween(clientId, startDate, endDate, dataType);

        List<ReportingData> reportingDataList = new ArrayList<>();
        String dataTypeString = ReportingDataType.FIVE_MONTH_AVERAGE.toString();

        for (int i = 0; i < pastDataList.size(); i++) {
            if (i > 3) {
                ReportingData fourMonthPrevData = pastDataList.get(i - 4);
                ReportingData threeMonthPrevData = pastDataList.get(i - 3);
                ReportingData twoMonthPrevData = pastDataList.get(i - 2);
                ReportingData prevMonthData = pastDataList.get(i - 1);
                ReportingData currentMonthData = pastDataList.get(i);

                Float fiveMonthAvg = (
                        fourMonthPrevData.getAmount() + threeMonthPrevData.getAmount() +
                        twoMonthPrevData.getAmount() + prevMonthData.getAmount() + currentMonthData.getAmount()
                ) / 5;
                reportingDataList.add(new ReportingData(
                        UUID.randomUUID().toString(),
                        clientId,
                        fiveMonthAvg,
                        dataTypeString,
                        currentMonthData.getYear(),
                        currentMonthData.getMonth(),
                        this.dateProvider.getDateNow().getTime()
                ));
            }
        }

        this.reportingDataRepository.bulkInsert(reportingDataList);
        return new ServiceResponse<>("Five month averages created", true);
    }
}
