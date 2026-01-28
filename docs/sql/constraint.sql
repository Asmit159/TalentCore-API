SELECT * FROM employee_directory.employee;
ALTER TABLE employee MODIFY email VARCHAR(255) NOT NULL UNIQUE;
SELECT * FROM employee_directory.users;