package com.jmnoland.expensetrackerapi.interfaces.repositories;

import com.jmnoland.expensetrackerapi.models.entities.ReportingData;
import com.jmnoland.expensetrackerapi.models.enums.ReportingDataType;

public interface ReportingDataRepositoryInterface {
    ReportingData findLastReportingData(String clientId, ReportingDataType dataType);
}
