package com.nagarro.employee_portal.controller;

import com.nagarro.employee_portal.model.Ticket;
import com.nagarro.employee_portal.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TicketController {
    TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Show new student form string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/showNewTicketForm")
    public String showNewStudentForm(Model model) {
        // create model attribute to bind form data
        Ticket ticket = new Ticket();
        model.addAttribute("ticket", ticket);
        return "new_ticket";
    }

    @PostMapping("/createTicket/{username}")
    public String createTicketWithUser(@PathVariable(value = "username") String username, @ModelAttribute("ticket") Ticket ticket) {
        // save student to database
        ticketService.createTicket(username,ticket);
        return "redirect:/employee/ticket/"+username;
    }


    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Integer id, Model model) {
        // get employee from the service
        Ticket ticket = ticketService.searchTicketById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("ticket", ticket);
        return "update_ticket";
    }

    @PostMapping("/saveTicket/{username}")
    public String saveTicket(@PathVariable(value = "username") String username, @ModelAttribute("ticket") Ticket ticket) {
        // save student to database
        ticketService.saveTicket(ticket);
        return "redirect:/employee/ticket/"+username;
    }

    @GetMapping("/deleteTicket/{id}/{username}")
    public String deleteEmployee(@PathVariable(value = "id") Integer id, @PathVariable(value = "username") String username) {
        // call delete employee method
        ticketService.deleteTicketById(id);
        return "redirect:/employee/ticket/"+username;
    }
}
