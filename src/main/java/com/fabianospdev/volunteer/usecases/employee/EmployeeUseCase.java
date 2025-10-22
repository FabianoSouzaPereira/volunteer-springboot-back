package com.fabianospdev.volunteer.usecases.employee;

import com.fabianospdev.volunteer.dto.EmployeeDTO;
import com.fabianospdev.volunteer.models.EmployeeModel;
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

    @Autowired(required = true)
    public EmployeeUseCase( EmployeeRepository employeeRepository, MessageSource messageSource ) {
        this.employeeRepository = employeeRepository;
        this.messageSource = messageSource;
    }

    public List<EmployeeDTO> findAllDTO() {
        return employeeRepository.findAllDTO();
    }


    public List<EmployeeModel> findAll() {
        return employeeRepository.findAll();
    }

    public EmployeeModel findById(String id ) {
        Optional<EmployeeModel> obj = employeeRepository.findById( id );
        return obj.orElseThrow( () -> new ObjectNotFoundException( "Object not found" ) );
    }

    public EmployeeModel insert(EmployeeModel employee ) {
        return employeeRepository.insert( employee );
    }

    public EmployeeModel update(EmployeeModel employee ) {
        Optional<EmployeeModel> obj = employeeRepository.findById( employee.getId() );

        if ( obj.isEmpty() ) {
            String message = messageSource.getMessage( "object.not.exists", new Object[]{employee.getId()}, LocaleContextHolder.getLocale() );
            throw new ObjectNotExistsException( message );
        }

        return employeeRepository.save( employee );
    }

    public void deleteById( String id ) {
        employeeRepository.deleteById( id );
    }

    private EmployeeDTO convertToEmployeeDTO( EmployeeModel employee ) {

        if ( employee == null ) {
            return new EmployeeDTO();
        }

        return new EmployeeDTO( employee );
    }
}