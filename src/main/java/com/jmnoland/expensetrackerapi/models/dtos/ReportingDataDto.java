package com.jmnoland.expensetrackerapi.models.dtos;

import com.jmnoland.expensetrackerapi.models.enums.ReportingDataType;

import java.util.Date;

public class ReportingDataDto {
    public String dataId;
    public String clientId;
    public Float amount;
    public ReportingDataType dataType;
    public int year;
    public int month;
    public Date createdAt;

    public ReportingDataDto(String dataId, String clientId, ReportingDataType dataType, int year, int month, Date createdAt) {
        this.dataId = dataId;
        this.clientId = clientId;
        this.dataType = dataType;
        this.year = year;
        this.month = month;
        this.createdAt = createdAt;
    }
}
