package com.jmnoland.expensetrackerapi.repositories;

import com.jmnoland.expensetrackerapi.database.mongodb.ReportingDataDAO;
import com.jmnoland.expensetrackerapi.interfaces.repositories.ReportingDataRepositoryInterface;
import com.jmnoland.expensetrackerapi.models.entities.ReportingData;
import com.jmnoland.expensetrackerapi.models.enums.ReportingDataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class ReportingDataRepository implements ReportingDataRepositoryInterface {

    private final ReportingDataDAO reportingDataDAO;
    @Autowired
    public ReportingDataRepository(ReportingDataDAO reportingDataDAO) {
        this.reportingDataDAO = reportingDataDAO;
    }

    public ReportingData findLastReportingData(String clientId, ReportingDataType dataType) {
        Sort sort = Sort.by(Sort.Direction.DESC, "year")
                .and(Sort.by(Sort.Direction.DESC, "month"));
        PageRequest request = PageRequest.of(0, 1, sort);
        Page<ReportingData> data = this.reportingDataDAO.findReportingData(clientId, dataType.toString(), request);
        if (data.getContent().isEmpty()) {
            return new ReportingData(
                    UUID.randomUUID().toString(),
                    clientId,
                    0f,
                    dataType.toString(),
                    0,
                    0,
                    new Date(0),
                    new Date(0)
            );
        }
        return data.getContent().get(0);
    }

    public List<ReportingData> findReportingDataBetween(String clientId, Date startDate, Date endDate, ReportingDataType dataType) {
        Sort sort = Sort.by(Sort.Direction.ASC, "year")
                .and(Sort.by(Sort.Direction.ASC, "month"));
        return this.reportingDataDAO.findReportingDataBetween(clientId, startDate, endDate, dataType, sort);
    }

    public void insert(ReportingData reportingData) {
        this.reportingDataDAO.insert(reportingData);
    }

    public void bulkInsert(List<ReportingData> reportingDataList) {
        this.reportingDataDAO.insert(reportingDataList);
    }
}
