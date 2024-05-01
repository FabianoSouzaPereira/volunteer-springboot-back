package com.fabianospdev.volunteer.controller;

import com.fabianospdev.volunteer.models.Leader;
import com.fabianospdev.volunteer.dto.LeaderDTO;
import com.fabianospdev.volunteer.services.LeaderService;
import com.fabianospdev.volunteer.usecases.leader.LeaderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/leaders")
public class LeaderController {

    @Autowired
    private LeaderService service;

    @Autowired(required=true)
    private LeaderUseCase leader;

    @RequestMapping(value="/find-all-list", method=RequestMethod.GET)
    public ResponseEntity<List<LeaderDTO>> findAllDTO() {
        List<LeaderDTO> list = service.findAllDTO();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Leader>> findAll() {
        List<Leader> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<LeaderDTO> findById(@PathVariable String id) {
        Leader obj = service.findById(id);
        return ResponseEntity.ok().body(new LeaderDTO(obj));
    }

    @RequestMapping(value="/{id}/find-by-id", method=RequestMethod.GET)
    public ResponseEntity<Leader> findByIdList(@PathVariable String id) {
        Leader obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Leader obj) {
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
    public ResponseEntity<Void> update(@RequestBody LeaderDTO objDto, @PathVariable String id) {
        Leader obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> updatefull(@RequestBody Leader obj, @PathVariable String id) {
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }
}