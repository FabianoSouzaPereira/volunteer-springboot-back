package com.fabianospdev.volunteer.usecases.employee;

import com.fabianospdev.volunteer.models.Employee;
import com.fabianospdev.volunteer.repositories.EmployeeRepository;
import com.fabianospdev.volunteer.services.exception.ObjectNotExistsException;
import com.fabianospdev.volunteer.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeUseCase{

    private final EmployeeRepository employeeRepository;
    private final MessageSource messageSource;

    @Autowired(required=true)
    public EmployeeUseCase( EmployeeRepository employeeRepository, MessageSource messageSource) {
        this.employeeRepository = employeeRepository;
        this.messageSource = messageSource;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(String id) {
        Optional<Employee> obj = employeeRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public Employee insert(Employee employee) {
        return employeeRepository.insert(employee);
    }

    public Employee update(Employee employee) {
                Optional<Employee> obj = employeeRepository.findById(employee.getId());

        if (obj.isEmpty()) {
            String message = messageSource.getMessage("object.not.exists", new Object[]{employee.getId()}, LocaleContextHolder.getLocale());
            throw new ObjectNotExistsException(message);
        }

        return employeeRepository.save(employee);
    }

    public void deleteById(String id) {
        employeeRepository.deleteById(id);
    }
}