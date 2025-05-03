package com.jmnoland.expensetrackerapi.services;

import com.jmnoland.expensetrackerapi.interfaces.providers.DateProviderInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ApiKeyRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ReportingDataRepositoryInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.AnalyticsServiceInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.ExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.interfaces.services.RecurringExpenseServiceInterface;
import com.jmnoland.expensetrackerapi.models.dtos.RecurringExpenseDto;
import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;
import com.jmnoland.expensetrackerapi.models.entities.ReportingData;
import com.jmnoland.expensetrackerapi.models.enums.ReportingDataType;
import com.jmnoland.expensetrackerapi.models.requests.CreateUpdateExpenseRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SchedulerService {

    private final RecurringExpenseServiceInterface recurringExpenseService;
    private final ExpenseServiceInterface expenseService;
    private final ApiKeyRepositoryInterface apiKeyRepository;
    private final ReportingDataRepositoryInterface reportingDataRepository;
    private final AnalyticsServiceInterface analyticsService;
    private final DateProviderInterface dateProvider;

    public SchedulerService(RecurringExpenseServiceInterface recurringExpenseService,
                            ExpenseServiceInterface expenseService,
                            ApiKeyRepositoryInterface apiKeyRepository,
                            ReportingDataRepositoryInterface reportingDataRepository,
                            AnalyticsServiceInterface analyticsService,
                            DateProviderInterface dateProvider) {
        this.recurringExpenseService = recurringExpenseService;
        this.expenseService = expenseService;
        this.apiKeyRepository = apiKeyRepository;
        this.reportingDataRepository = reportingDataRepository;
        this.analyticsService = analyticsService;
        this.dateProvider = dateProvider;
    }
    @Scheduled(cron = "0 0 12 * * ?")
    public void CreateRecurringExpenses()
    {
        List<String> clientIdList = this.apiKeyRepository.getAllClientIds();
        for(String id : clientIdList) {

            ServiceResponse<List<RecurringExpenseDto>> response = this.recurringExpenseService.getRecurringExpenses(id);
            if (!response.successful) continue;

            List<CreateUpdateExpenseRequest> expenseRequests = CreatePendingExpenses(response.responseObject);
            for(CreateUpdateExpenseRequest request : expenseRequests) {
                this.expenseService.createExpense(request);
            }
        }
    }
    @Scheduled(cron = "0 0 0 1 * ?")
    public void CreateMonthlyReportingData() {
        List<String> clientIdList = this.apiKeyRepository.getAllClientIds();
        for(String id : clientIdList) {
            ReportingData response = this.reportingDataRepository.findLastReportingData(id, ReportingDataType.MONTH_TOTAL);
            this.analyticsService.CalculateMonthlyTotals(id, getStartingDate(response), this.dateProvider.getDateNow().getTime());
        }
    }
    @Scheduled(cron = "0 0 1 1 * ?")
    public void CreateThreeMonthAverageReportingData() {
        List<String> clientIdList = this.apiKeyRepository.getAllClientIds();
        for(String id : clientIdList) {
            ReportingData response = this.reportingDataRepository.findLastReportingData(id, ReportingDataType.THREE_MONTH_AVERAGE);
            this.analyticsService.CalculateThreeMonthAverages(id, getStartingDate(response), this.dateProvider.getDateNow().getTime());
        }
    }
    @Scheduled(cron = "0 0 1 1 * ?")
    public void CreateFiveMonthAverageReportingData() {
        List<String> clientIdList = this.apiKeyRepository.getAllClientIds();
        for(String id : clientIdList) {
            ReportingData response = this.reportingDataRepository.findLastReportingData(id, ReportingDataType.FIVE_MONTH_AVERAGE);
            this.analyticsService.CalculateFiveMonthAverages(id, getStartingDate(response), this.dateProvider.getDateNow().getTime());
        }
    }

    private Date getStartingDate(ReportingData obj) {
        Calendar cal = dateProvider.calendarDateFromParts(obj.getYear(), obj.getMonth(), 1);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    public List<CreateUpdateExpenseRequest> CreatePendingExpenses(List<RecurringExpenseDto> recurExpenseList) {
        List<CreateUpdateExpenseRequest> expenseRequests = new ArrayList<>();
        Calendar currentTime = this.dateProvider.getDateNow();
        for(RecurringExpenseDto recurringExpense : recurExpenseList) {
            ServiceResponse<CreateUpdateExpenseRequest> newExpense = GenerateExpenseForDate(recurringExpense, currentTime);
            if (newExpense.successful) {
                expenseRequests.add(newExpense.responseObject);
                this.recurringExpenseService.update(recurringExpense);
            }
        }
        return expenseRequests;
    }

    public ServiceResponse<CreateUpdateExpenseRequest> GenerateExpenseForDate(RecurringExpenseDto recurringExpense, Calendar chosenCalendar) {
        CreateUpdateExpenseRequest expenseRequest = null;
        Date chosenDate = chosenCalendar.getTime();
        if (recurringExpense.lastExpenseDate == null) {
            if (chosenDate.after(recurringExpense.startDate)
                    || chosenDate.equals(recurringExpense.startDate)) {
                recurringExpense.lastExpenseDate = chosenDate;

                expenseRequest = CreateExpenseRequest(recurringExpense, chosenDate);
            }
        } else {
            Calendar lastExpenseCalendar = new GregorianCalendar();
            lastExpenseCalendar.setTime(recurringExpense.lastExpenseDate);
            lastExpenseCalendar.set(Calendar.MINUTE, 0);
            lastExpenseCalendar.set(Calendar.HOUR, 0);
            lastExpenseCalendar.set(Calendar.SECOND, 0);
            lastExpenseCalendar.set(Calendar.MILLISECOND, 0);
            Calendar calendarBeforeChange = (Calendar) lastExpenseCalendar.clone();
            switch (recurringExpense.frequency) {
                case MONTHLY:
                    lastExpenseCalendar.add(Calendar.MONTH, 1);
                    break;
                case WEEKLY:
                    lastExpenseCalendar.add(Calendar.HOUR, 7 * 24);
                    break;
                case DAILY:
                    lastExpenseCalendar.add(Calendar.HOUR, 24);
                    break;
                case YEARLY:
                    lastExpenseCalendar.add(Calendar.YEAR, 1);
                    break;
                default:
                    break;
            }
            Date lastExpenseDate = lastExpenseCalendar.getTime();
            if (calendarBeforeChange.compareTo(lastExpenseCalendar) != 0) {
                if (lastExpenseDate.before(chosenDate) || lastExpenseDate.equals(chosenDate)) {
                    recurringExpense.lastExpenseDate = lastExpenseCalendar.getTime();

                    expenseRequest = CreateExpenseRequest(recurringExpense, chosenDate);
                }
            }
        }
        return new ServiceResponse<>(expenseRequest, expenseRequest != null);
    }

    private CreateUpdateExpenseRequest CreateExpenseRequest(RecurringExpenseDto recurringExpense, Date currentTime) {
        CreateUpdateExpenseRequest expenseRequest = new CreateUpdateExpenseRequest();
        expenseRequest.clientId = recurringExpense.clientId;
        expenseRequest.categoryId = recurringExpense.categoryId;
        expenseRequest.paymentTypeId = recurringExpense.paymentTypeId;
        expenseRequest.name = recurringExpense.name;
        expenseRequest.date = currentTime;
        expenseRequest.amount = recurringExpense.amount;
        expenseRequest.recurringExpenseId = recurringExpense.recurringExpenseId;
        return expenseRequest;
    }
}
