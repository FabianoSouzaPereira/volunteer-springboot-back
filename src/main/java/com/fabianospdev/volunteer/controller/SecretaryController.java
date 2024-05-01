package com.fabianospdev.volunteer.controller;

import com.fabianospdev.volunteer.dto.SecretaryDTO;
import com.fabianospdev.volunteer.models.Secretary;
import com.fabianospdev.volunteer.services.SecretaryService;
import com.fabianospdev.volunteer.usecases.secretary.SecretaryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/secretaries")
public class SecretaryController{

    @Autowired
    private SecretaryService service;

    @Autowired(required=true)
    private SecretaryUseCase secretary;

    @RequestMapping(value="/find-all-list", method=RequestMethod.GET)
    public ResponseEntity<List<SecretaryDTO>> findAllDTO() {
        List<SecretaryDTO> list = service.findAllDTO();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Secretary>> findAll() {
        List<Secretary> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<SecretaryDTO> findById(@PathVariable String id) {

        if(id == null || id.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Secretary obj = service.findById(id);
        return ResponseEntity.ok().body(new SecretaryDTO(obj));
    }

    @RequestMapping(value="/{id}/find-by-id", method=RequestMethod.GET)
    public ResponseEntity<Secretary> findByIdList(@PathVariable String id) {
        Secretary obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Secretary obj) {

        if(obj == null) {
            return ResponseEntity.badRequest().build();
        }

        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {

        if(id == null || id.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}/dto", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody SecretaryDTO objDto, @PathVariable String id) {

        if(objDto == null || objDto.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Secretary> optionalSecretary = Optional.ofNullable(service.findById(id));

        if(optionalSecretary.isPresent()) {
            Secretary oldObj = optionalSecretary.get();

            oldObj.setName(objDto.getName());
            oldObj.setPhone(objDto.getPhone());
            oldObj.setEmail(objDto.getEmail());

            service.update(oldObj);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> updatefull(@RequestBody Secretary obj, @PathVariable String id) {

        if(obj == null || id == null || id.isEmpty() || !id.equals(obj.getId())) {
            return ResponseEntity.badRequest().build();
        }

        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }
}