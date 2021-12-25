package com.nagarro.employee_portal.controller;

import com.nagarro.employee_portal.model.Employee;
import com.nagarro.employee_portal.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {
    EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

/*    @PostMapping("/employee/add")
    public void addEmployee(Employee employee) {
        employeeService.saveEmployee(employee);
    }

    @GetMapping("/employee/list")
    public List<Employee> listEmployees() {
        return employeeService.listEmployees();
    }*/

    @GetMapping("/employee/ticket/{username}")
    public String listTickets(@PathVariable(value = "username") String username, Model model) {
        model.addAttribute("employeetickets", employeeService.ticketsOfAnEmployee(username));
        return "employee_tickets";
    }

    /**
     * View homepage string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/")
    public String viewHomepage(Model model) {
        model.addAttribute("employees", employeeService.listEmployees());
        return "index";
    }

    @GetMapping("/index.html")
    public String viewHomepage1(Model model) {
        model.addAttribute("employees", employeeService.listEmployees());
        return "index";
    }

    // Login form
    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }

    // Login form with error
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/signup")
    public String addEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "signup";
    }

    @PostMapping("/createEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        // save student to database
        employeeService.saveEmployee(employee);
        return "redirect:/login.html";
    }

}
