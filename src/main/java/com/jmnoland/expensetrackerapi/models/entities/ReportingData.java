package com.jmnoland.expensetrackerapi.models.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document("reportingdata")
public class ReportingData {

    @Id
    private final String dataId;
    private final String clientId;
    private Float amount;
    private String dataType;
    private int year;
    private int month;
    private Date date;
    private final Date createdAt;

    public ReportingData(String dataId, String clientId, Float amount, String dataType, int year, int month, Date date, Date createdAt) {
        this.dataId = dataId;
        this.clientId = clientId;
        this.amount = amount;
        this.dataType = dataType;
        this.year = year;
        this.month = month;
        this.date = date;
        this.createdAt = createdAt;
    }

    public String getDataId() {
        return dataId;
    }
    public Float getAmount() { return amount; }
    public void setAmount(Float amount) { this.amount = amount; }
    public String getClientId() {
        return clientId;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
