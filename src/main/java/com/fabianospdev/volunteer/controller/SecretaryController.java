package com.fabianospdev.volunteer.controller;

import com.fabianospdev.volunteer.dto.PastorDTO;
import com.fabianospdev.volunteer.models.Pastor;
import com.fabianospdev.volunteer.services.PastorService;
import com.fabianospdev.volunteer.usecases.pastor.UseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/Secretaries")
public class SecretaryController{

    @Autowired
    private PastorService service;

    private UseCase pastor;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<PastorDTO>> findAll() {
        List<Pastor> list = service.findAll();
        List<PastorDTO> listDto = list.stream().map(x -> new PastorDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value="/find-all-list", method=RequestMethod.GET)
    public ResponseEntity<List<Pastor>> findAllList() {
        List<Pastor> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<PastorDTO> findById(@PathVariable String id) {
        Pastor obj = service.findById(id);
        return ResponseEntity.ok().body(new PastorDTO(obj));
    }

    @RequestMapping(value="/{id}/find-by-id", method=RequestMethod.GET)
    public ResponseEntity<Pastor> findByIdList(@PathVariable String id) {
        Pastor obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Pastor obj) {
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
    public ResponseEntity<Void> update(@RequestBody PastorDTO objDto, @PathVariable String id) {
        Pastor obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> updatefull(@RequestBody Pastor obj, @PathVariable String id) {
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }
}