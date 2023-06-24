package com.jmnoland.expensetrackerapi.interfaces.repositories;

import com.jmnoland.expensetrackerapi.models.entities.ReportingData;
import com.jmnoland.expensetrackerapi.models.enums.ReportingDataType;

import java.util.Date;
import java.util.List;

public interface ReportingDataRepositoryInterface {
    ReportingData findLastReportingData(String clientId, ReportingDataType dataType);
    List<ReportingData> findReportingDataBetween(String clientId, Date startDate, Date endDate, ReportingDataType dataType);
    void insert(ReportingData reportingData);
    void bulkInsert(List<ReportingData> reportingDataList);
}
