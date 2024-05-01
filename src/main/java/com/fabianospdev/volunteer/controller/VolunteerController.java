package com.fabianospdev.volunteer.controller;

import com.fabianospdev.volunteer.dto.VolunteerDTO;
import com.fabianospdev.volunteer.models.Volunteer;
import com.fabianospdev.volunteer.services.VolunteerService;
import com.fabianospdev.volunteer.usecases.volunteer.VolunteerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/volunteers")
public class VolunteerController{

    @Autowired
    private VolunteerService service;

    @Autowired(required=true)
    private VolunteerUseCase volunteer;

    @RequestMapping(value="/find-all-list", method=RequestMethod.GET)
    public ResponseEntity<List<VolunteerDTO>> findAllDTO() {
        List<VolunteerDTO> list = service.findAllDTO();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Volunteer>> findAll() {
        List<Volunteer> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<VolunteerDTO> findById(@PathVariable String id) {

        if(id == null || id.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Volunteer obj = service.findById(id);
        return ResponseEntity.ok().body(new VolunteerDTO(obj));
    }

    @RequestMapping(value="/{id}/find-by-id", method=RequestMethod.GET)
    public ResponseEntity<Volunteer> findByIdList(@PathVariable String id) {
        Volunteer obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Volunteer obj) {

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
    public ResponseEntity<Void> update(@RequestBody VolunteerDTO objDto, @PathVariable String id) {

        if(objDto == null || objDto.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Volunteer> optionalVolunteer = Optional.ofNullable(service.findById(id));

        if(optionalVolunteer.isPresent()) {
            Volunteer oldObj = optionalVolunteer.get();

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
    public ResponseEntity<Void> updatefull(@RequestBody Volunteer obj, @PathVariable String id) {

        if(obj == null || id == null || id.isEmpty() || !id.equals(obj.getId())) {
            return ResponseEntity.badRequest().build();
        }

        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }
}