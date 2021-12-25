package com.nagarro.employee_portal.service;

import com.nagarro.employee_portal.model.Employee;
import com.nagarro.employee_portal.model.Ticket;
import com.nagarro.employee_portal.repository.EmployeeRepository;
import com.nagarro.employee_portal.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private static Logger logger = LoggerFactory.getLogger(TicketService.class);

    EmployeeRepository employeeRepository;
    TicketRepository ticketRepository;

    @Autowired
    public TicketService(EmployeeRepository employeeRepository, TicketRepository ticketRepository) {
        this.employeeRepository = employeeRepository;
        this.ticketRepository = ticketRepository;
    }

    public void saveTicket(Ticket ticket) {
        try {
            ticketRepository.save(ticket);
        } catch (Exception e) {
            logger.info("Exception : {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void createTicket(String username, Ticket ticket) {
        try {
            Optional<Employee> employee = employeeRepository.findEmployeeByUsername(username);
            List<Ticket> ticketList = employee.get().getEmployeeTickets();
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            ticket.setDateOfCreation(date);
            ticketList.add(ticket);
            employeeRepository.save(employee.get());
        } catch (Exception e) {
            logger.info("Exception : {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public Ticket searchTicketById(Integer id) {
        try {
            Ticket ticket = ticketRepository.getById(id);
            return ticket;
        } catch (Exception e) {
            logger.info("Exception : {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void deleteTicketById(Integer id) {
        try {
            Optional<Ticket> ticket = ticketRepository.findById(id);
            ticketRepository.delete(ticket.get());
        } catch (Exception e) {
            logger.info("Exception : {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
