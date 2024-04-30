package com.fabianospdev.volunteer.controller;

import com.fabianospdev.volunteer.dto.EmployeeDTO;
import com.fabianospdev.volunteer.models.Employee;
import com.fabianospdev.volunteer.services.EmployeeService;
import com.fabianospdev.volunteer.usecases.employee.EmployeeUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/employees")
public class EmployeeController{

    @Autowired
    private EmployeeService service;

    private EmployeeUseCase employee;

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<EmployeeDTO>> findAll() {
        List<Employee> list = service.findAll();
        List<EmployeeDTO> listDto = list.stream().map(x -> new EmployeeDTO(x)).collect( Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value="/find-all-list", method=RequestMethod.GET)
    public ResponseEntity<List<Employee>> findAllList() {
        List<Employee> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<EmployeeDTO> findById(@PathVariable String id) {
        Employee obj = service.findById(id);
        return ResponseEntity.ok().body(new EmployeeDTO(obj));
    }

    @RequestMapping(value="/{id}/find-by-id", method=RequestMethod.GET)
    public ResponseEntity<Employee> findByIdList(@PathVariable String id) {
        Employee obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Employee obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}/dto", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody EmployeeDTO objDto, @PathVariable String id) {
        Employee obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> updatefull(@RequestBody Employee obj, @PathVariable String id) {
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }
}