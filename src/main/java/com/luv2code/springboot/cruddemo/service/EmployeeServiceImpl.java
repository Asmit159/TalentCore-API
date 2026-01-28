package com.luv2code.springboot.cruddemo.service;

import com.luv2code.springboot.cruddemo.dto.EmployeeDTO;
import com.luv2code.springboot.cruddemo.dto.EmployeeMapper;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.exception.EmployeeNotFoundException;
import com.luv2code.springboot.cruddemo.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> result = employeeRepository.findById(theId);
        Employee employee = null;
        if (result.isPresent()) {
            employee = result.get();
            return employee;
        }
        else{
            throw new EmployeeNotFoundException("Employee id not found - " + theId);
        }
    }


    @Override
    public Employee save(Employee theEmployee) {
        return employeeRepository.save(theEmployee);
    }


    @Transactional
    @Override
    public void deleteById(int theId) {

        if (!employeeRepository.existsById(theId)) {
            throw new EmployeeNotFoundException("Employee id not found - " + theId);
        }

        employeeRepository.deleteById(theId);
    }

    @Override
    public Page<Employee> findAllPaged(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return employeeRepository.findAll(pageRequest);
    }


    @Override
    public EmployeeDTO findByIdDTO(int id) {
        Employee employee = findById(id); // reuse existing logic
        return EmployeeMapper.toDTO(employee);
    }

    @Override
    public EmployeeDTO saveDTO(EmployeeDTO dto) {

        Employee employee = EmployeeMapper.toEntity(dto);

        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.toDTO(savedEmployee);
    }


    @Override
    public Page<EmployeeDTO> findAllPagedDTO(int page, int size, String sortBy, String direction) {
        return findAllPaged(page, size, sortBy, direction)
                .map(EmployeeMapper::toDTO);
    }

    @Override
    public List<EmployeeDTO> findAllDTO() {

        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

}
