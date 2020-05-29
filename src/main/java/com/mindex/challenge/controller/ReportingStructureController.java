package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;


    @GetMapping("/employee/reports/{id}")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received employee reports request for id [{}]", id);
        Employee employee = reportingStructureService.read(id);
        List<Employee> reports = employee.getDirectReports();
        int numberOfReports = reports.size();
        //TODO find number of reports of each employee and its employee recursively till you hit null.
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(numberOfReports);
        return reportingStructure;
    }
}
