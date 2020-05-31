package com.mindex.challenge.controller;

import com.mindex.challenge.dao.EmployeeRepository;
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
    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/employee/reports/{id}")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received employee reports request for id [{}]", id);
        Employee employee = reportingStructureService.read(id);
        // find number of reports of each employee and its employee recursively till you hit null.
        int numberOfReports = findNumberOfReports(employee);
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(numberOfReports-1);
        return reportingStructure;
    }

    private int findNumberOfReports(Employee startEmployee) {
        if(startEmployee.getDirectReports() == null)
            return 1;

        int numberOfReports = 0;

        //looping through all the direct reports of the employee
        for(Employee employee:startEmployee.getDirectReports()) {

            //the database call to fetch the employee
            Employee emp = employeeRepository.findByEmployeeId(employee.getEmployeeId());
            if (emp == null) {
                //if employee is not present in the database
                throw new RuntimeException("Invalid employeeId: " + employee.getEmployeeId());
            }
            employee.setFirstName(emp.getFirstName());
            employee.setLastName(emp.getLastName());
            employee.setPosition(emp.getPosition());
            employee.setDepartment(emp.getDepartment());
            employee.setDirectReports(emp.getDirectReports());

            numberOfReports+= findNumberOfReports(emp);
        }
        return numberOfReports+1;

    }
}
