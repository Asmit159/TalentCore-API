package com.luv2code.springboot.cruddemo.service;

import com.luv2code.springboot.cruddemo.dto.EmployeeDTO;
import com.luv2code.springboot.cruddemo.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    // ENTITY-based (internal / legacy)
    List<Employee> findAll();
    Page<Employee> findAllPaged(int page, int size, String sortBy, String direction);
    Employee findById(int id);
    Employee save(Employee employee);
    void deleteById(int id);

    // DTO-based (API-facing)
    List<EmployeeDTO> findAllDTO();
    Page<EmployeeDTO> findAllPagedDTO(int page, int size, String sortBy, String direction);
    EmployeeDTO findByIdDTO(int id);
    EmployeeDTO saveDTO(EmployeeDTO dto);
}
