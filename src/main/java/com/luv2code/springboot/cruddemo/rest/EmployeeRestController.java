package com.luv2code.springboot.cruddemo.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luv2code.springboot.cruddemo.dto.EmployeeDTO;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Validated
@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;
    private final Validator validator; // Added to fix PATCH validation issue

    public EmployeeRestController(EmployeeService employeeService,
                                  ObjectMapper objectMapper,
                                  Validator validator) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
        this.validator = validator;
    }


    //mapping to see all employees
    @GetMapping("/employees")
    public List<EmployeeDTO> findAll() {
        return employeeService.findAllDTO();
    }


    //add mapping for GET /employees/{employeeId}
    @GetMapping("/employees/{employeeId}")
    public EmployeeDTO findById(@PathVariable int employeeId) {
        // Service throws EmployeeNotFoundException if not found
        return employeeService.findByIdDTO(employeeId);
    }


    //add mapping for POST-employess - add new employees
    //also just in case they pass an incorrect id in JSON... set id to 0;
    //this is to force a save of new items ... instead of update
    @PostMapping("/employees")
    public EmployeeDTO addEmployee(@Valid @RequestBody EmployeeDTO dto) {
        dto.setId(0);
        return employeeService.saveDTO(dto);
    }


    //updating an employee
    // Fixed: Added {employeeId} to URL to match REST standards
    @PutMapping("/employees/{employeeId}")
    public EmployeeDTO updateEmployee(@PathVariable int employeeId,
                                      @Valid @RequestBody EmployeeDTO dto) {

        // Optional: Ensure ID in path matches ID in body
        dto.setId(employeeId); // Ensure we update the correct record
        return employeeService.saveDTO(dto);
    }


    // add mapping for PATCH
    @PatchMapping("/employees/{employeeId}")
    public EmployeeDTO patchEmployee(
            @PathVariable int employeeId,
            @RequestBody Map<String, Object> patchPayload) {

        EmployeeDTO existing = employeeService.findByIdDTO(employeeId);

        // prevent ID tampering
        if (patchPayload.containsKey("id")) {
            throw new IllegalArgumentException("Employee id cannot be modified");
        }

        // Convert DTO to JSON Node
        ObjectNode existingNode = objectMapper.valueToTree(existing);
        ObjectNode patchNode = objectMapper.valueToTree(patchPayload);

        // Merge the patch into the existing node
        existingNode.setAll(patchNode);

        try {
            // Convert merged JSON Node back to DTO
            EmployeeDTO patched =
                    objectMapper.treeToValue(existingNode, EmployeeDTO.class);

            // FIX: Manually trigger validation on the patched object
            Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(patched);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            return employeeService.saveDTO(patched);

        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Error processing JSON patch", ex);
        }
    }


    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable int employeeId) {
        employeeService.deleteById(employeeId);
    }


    // Pagination Endpoint
    @GetMapping("/employees/paged")
    public Page<EmployeeDTO> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return employeeService.findAllPagedDTO(page, size, sortBy, direction);
    }
}
