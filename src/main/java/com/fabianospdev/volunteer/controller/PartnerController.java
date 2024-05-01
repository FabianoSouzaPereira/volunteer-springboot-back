package com.fabianospdev.volunteer.controller;

import com.fabianospdev.volunteer.models.Partner;
import com.fabianospdev.volunteer.dto.PartnerDTO;
import com.fabianospdev.volunteer.services.PartnerService;
import com.fabianospdev.volunteer.usecases.partner.PartnerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/partners")
public class PartnerController {

    @Autowired
    private PartnerService service;

    @Autowired(required=true)
    private PartnerUseCase partner;

    @RequestMapping(value="/find-all-list", method=RequestMethod.GET)
    public ResponseEntity<List<PartnerDTO>> findAllDTO() {
        List<PartnerDTO> list = service.findAllDTO();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<Partner>> findAll() {
        List<Partner> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<PartnerDTO> findById(@PathVariable String id) {
        Partner obj = service.findById(id);
        return ResponseEntity.ok().body(new PartnerDTO(obj));
    }

    @RequestMapping(value="/{id}/find-by-id", method=RequestMethod.GET)
    public ResponseEntity<Partner> findByIdList(@PathVariable String id) {
        Partner obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Partner obj) {
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
    public ResponseEntity<Void> update(@RequestBody PartnerDTO objDto, @PathVariable String id) {
        Partner obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> updatefull(@RequestBody Partner obj, @PathVariable String id) {
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }
}