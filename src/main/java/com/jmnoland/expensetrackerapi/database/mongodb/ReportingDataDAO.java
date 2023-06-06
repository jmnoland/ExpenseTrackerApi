package com.jmnoland.expensetrackerapi.database.mongodb;

import com.jmnoland.expensetrackerapi.models.entities.ReportingData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ReportingDataDAO extends MongoRepository<ReportingData, String> {

    @Query("{ 'clientId' : ?0, 'dataType' : ?1 }")
    Page<ReportingData> findReportingData(String clientId, String dataType, Pageable pageable);
}
