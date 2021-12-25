package com.nagarro.employee_portal.service;

import com.nagarro.employee_portal.model.Employee;
import com.nagarro.employee_portal.model.Ticket;
import com.nagarro.employee_portal.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements UserDetailsService {
    private static Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    EmployeeRepository employeeRepository;
    private PasswordEncoder bcryptEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder bcryptEncoder) {
        this.employeeRepository = employeeRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    public void saveEmployee(Employee employee) {
        try {
            employee.setPassword(bcryptEncoder.encode(employee.getPassword()));
            employeeRepository.save(employee);
        } catch (Exception e) {
            logger.info("Exception : {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Employee> listEmployees() {
        try {
            List<Employee> employeeList = employeeRepository.findAll();
            if (employeeList.isEmpty()) {
                return null;
            } else {
                return employeeList;
            }
        } catch (Exception e) {
            logger.info("Exception : {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Ticket> ticketsOfAnEmployee(String username) {
        try {
            Optional<Employee> employee = employeeRepository.findEmployeeByUsername(username);
            if (employee.isPresent()) {
                List<Ticket> ticketList = employee.get().getEmployeeTickets();
                return ticketList;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.info("Exception : {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Object> listEmployeeTickets() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<Object> ticketList = new ArrayList<>();
        for (Employee employee : employeeList
        ) {
            ticketList.add(employee.getEmployeeTickets());
        }
        return ticketList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findEmployeeByUsername(username);
        if (employee.isPresent()) {
            return new org.springframework.security.core.userdetails.User(employee.get().getUsername(), employee.get().getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Employee not found with username: " + username);
        }
    }
}
