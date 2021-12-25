DROP TABLE IF EXISTS T_EMPLOYEE;

CREATE TABLE T_EMPLOYEE
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name  VARCHAR(250) NOT NULL,
    username   VARCHAR(250) NOT  NULL,
    password   VARCHAR(250) NOT NULL,
    UNIQUE (username)
);

DROP TABLE IF EXISTS T_TICKET;

CREATE TABLE T_TICKET
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    issue      VARCHAR(250) NOT NULL,
    date_of_creation DATE,
    employee_id int,
    FOREIGN KEY (employee_id) REFERENCES T_EMPLOYEE (id)
);