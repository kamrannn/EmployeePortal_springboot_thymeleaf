package com.nagarro.employee_portal.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String firstName;
    String lastName;
    @NotBlank(message = "username is mandatory")
    @Column(unique = true)
    String username;
    String password;

    @OneToMany(targetEntity = Ticket.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private List<Ticket> employeeTickets = new ArrayList<>();
}
