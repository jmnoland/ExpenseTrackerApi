package com.jmnoland.expensetrackerapi.interfaces.services;

import com.jmnoland.expensetrackerapi.models.dtos.ServiceResponse;

import java.util.Date;

public interface AnalyticsServiceInterface {
    ServiceResponse<String> CalculateMonthlyTotals(String clientId, Date startDate, Date endDate);
    ServiceResponse<String> CalculateThreeMonthAverages(String clientId, Date startDate, Date endDate);
    ServiceResponse<String> CalculateFiveMonthAverages(String clientId, Date startDate, Date endDate);
}
