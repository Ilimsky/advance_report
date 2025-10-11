package org.example.employeeservice;

import org.example.common_utils.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController extends GenericController<EmployeeDTO, Long> {

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        super(employeeService);
    }{}

}