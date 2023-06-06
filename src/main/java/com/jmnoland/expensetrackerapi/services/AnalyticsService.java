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

import java.sql.Date;
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

        Float total = 0f;
        for (Expense expense : expenseList) {
            Calendar expenseDate = Calendar.getInstance();
            expenseDate.setTime(expense.getDate());

            if (expenseDate.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)) {
                reportingDataList.add(new ReportingData(
                        UUID.randomUUID().toString(),
                        clientId,
                        total,
                        ReportingDataType.MONTH_TOTAL.toString(),
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        this.dateProvider.getDateNow().getTime()
                ));
                total = 0f;
                calendar.setTime(expense.getDate());
            }
            total += expense.getAmount();
        }

        return new ServiceResponse<>("Monthly totals created", true);
    }
    public ServiceResponse<String> CalculateThreeMonthAverages() {

        return new ServiceResponse<>("Three month averages created", true);
    }
    public ServiceResponse<String> CalculateFiveMonthAverages() {

        return new ServiceResponse<>("Five month averages created", true);
    }
}
