package com.mindex.challenge.dao;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportingStructureRepository extends MongoRepository<Employee, String> {
    ReportingStructure findByEmployeeId(String employeeId);
}
