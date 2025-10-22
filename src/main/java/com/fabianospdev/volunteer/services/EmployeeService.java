package com.fabianospdev.volunteer.services;

import com.fabianospdev.volunteer.dto.EmployeeDTO;
import com.fabianospdev.volunteer.models.EmployeeModel;
import com.fabianospdev.volunteer.repositories.EmployeeRepository;
import com.fabianospdev.volunteer.usecases.employee.EmployeeUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService{

    @Autowired(required = true)
    private EmployeeRepository repository;

    @Autowired(required = true)
    private EmployeeUseCase useCase;


    public List<EmployeeModel> findAll() {
        return useCase.findAll();
    }


    public List<EmployeeDTO> findAllDTO() {
        return useCase.findAllDTO();
    }

    private EmployeeDTO convertToEmployeeDTO( EmployeeModel employee ) {
        EmployeeDTO employeeDTO = new EmployeeDTO( employee );
        return employeeDTO;
    }


    public EmployeeModel findById(String id ) {
        EmployeeModel obj = useCase.findById( id );
        return obj;
    }

    public EmployeeModel insert(EmployeeModel obj ) {
        return useCase.insert( obj );
    }

    public void delete( String id ) {
        useCase.deleteById( id );
    }

    public EmployeeModel update(EmployeeModel obj ) {
        return useCase.update( obj );
    }

    private void updateData(EmployeeModel newObj, EmployeeModel obj ) {
        newObj.setName( obj.getName() );
        newObj.setEmail( obj.getEmail() );
    }

    public EmployeeModel fromDTO(EmployeeDTO objDto ) {
        return new EmployeeModel( objDto.getId(), objDto.getName(), objDto.getEmail(), objDto.getPhone() );
    }
}