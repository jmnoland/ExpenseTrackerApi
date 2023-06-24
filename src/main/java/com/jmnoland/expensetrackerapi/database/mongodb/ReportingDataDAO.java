package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.ReportingData;
import com.jmnoland.expensetrackerapi.models.enums.ReportingDataType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReportingDataDAO extends MongoRepository<ReportingData, String> {

    @Query("{ 'clientId' : ?0, 'dataType' : ?1 }")
    Page<ReportingData> findReportingData(String clientId, String dataType, Pageable pageable);
    @Query("{'clientId' : ?0, 'createdAt' : { '$gte': ?1, '$lt': ?2 }, 'dataType': ?3 }")
    List<ReportingData> findReportingDataBetween(String clientId, Date startDate, Date endDate, ReportingDataType dataType, Sort sort);
}
