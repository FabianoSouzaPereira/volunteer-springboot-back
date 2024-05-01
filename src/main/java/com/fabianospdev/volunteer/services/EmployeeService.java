package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.models.Employee;
import com.fabianospdev.volunteer.dto.EmployeeDTO;
import com.fabianospdev.volunteer.repositories.EmployeeRepository;
import com.fabianospdev.volunteer.usecases.employee.EmployeeUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired(required=true)
    private EmployeeRepository repository;

    @Autowired(required=true)
    private EmployeeUseCase useCase;


    public List<Employee> findAll() {
        return useCase.findAll();
    }

    public List<Employee> findAllList() {
        return useCase.findAllList();
    }

    public List<EmployeeDTO> findAllDTO() {
        return useCase.findAllDTO();
    }

    private EmployeeDTO convertToEmployeeDTO( Employee employee ) {
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        return employeeDTO;
    }


    public Employee findById(String id) {
        Employee obj = useCase.findById(id);
        return obj;
    }

    public Employee insert(Employee obj) {
        return useCase.insert(obj);
    }

    public void delete(String id) {
        useCase.deleteById(id);
    }

    public Employee update(Employee obj) {
        return useCase.update(obj);
    }

    private void updateData(Employee newObj, Employee obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public Employee fromDTO(EmployeeDTO objDto) {
        return new Employee(objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone());
    }
}