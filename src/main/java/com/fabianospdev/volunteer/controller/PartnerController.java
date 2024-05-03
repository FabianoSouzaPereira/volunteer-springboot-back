package com.fabianospdev.volunteer.controller;

import com.fabianospdev.volunteer.dto.PartnerDTO;
import com.fabianospdev.volunteer.models.Partner;
import com.fabianospdev.volunteer.services.PartnerService;
import com.fabianospdev.volunteer.usecases.partner.PartnerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value="/volunteers/partners")
public class PartnerController{

    @Autowired
    private PartnerService service;

    @Autowired(required=true)
    private PartnerUseCase partner;

    private static <T> ResponseEntity<Void> getVoidResponseEntity(T obj, String id, Class<T> clazz) {
        if(obj == null || id != null || !clazz.isInstance(obj)) {
            return ResponseEntity.badRequest().build();
        }

        Field[] fields = clazz.getDeclaredFields();

        for(Field field: fields) {
            try {
                field.setAccessible(true);

                if(field.get(obj) == null) {
                    return ResponseEntity.badRequest().build();
                }
            } catch(IllegalAccessException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        return null;
    }

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

        if(id == null || id.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

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

        ResponseEntity<Void> build = getVoidResponseEntity(obj, null, Partner.class);
        if(build != null) return build;

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
    public ResponseEntity<Void> update(@RequestBody PartnerDTO objDto, @PathVariable String id) {

        ResponseEntity<Void> build = getVoidResponseEntity(objDto, id, PartnerDTO.class);
        if(build != null) return build;

        Optional<Partner> optionalPartner = Optional.ofNullable(service.findById(id));

        if(optionalPartner.isPresent()) {
            Partner oldObj = optionalPartner.get();

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
    public ResponseEntity<Void> updatefull(@RequestBody Partner obj, @PathVariable String id) {

        ResponseEntity<Void> build = getVoidResponseEntity(obj, id, Partner.class);
        if(build != null) return build;

        Optional<Partner> optionalUser = Optional.ofNullable(service.findById(id));

        if(optionalUser.isPresent()) {
            Partner oldObj = optionalUser.get();

            oldObj.setName(Objects.requireNonNullElse(obj.getName(), ""));
            oldObj.setAge(Objects.requireNonNullElse(obj.getAge(), 0));
            oldObj.setGroup(Objects.requireNonNullElse(obj.getGroup(), ""));
            oldObj.setRole(Objects.requireNonNullElse(obj.getRole(), ""));
            oldObj.setFunctions(Objects.requireNonNullElse(obj.getFunctions(), Collections.emptyList()));
            oldObj.setStatus(Objects.requireNonNullElse(obj.getStatus(), ""));
            oldObj.setPhone(Objects.requireNonNullElse(obj.getPhone(), ""));
            oldObj.setEmail(Objects.requireNonNullElse(obj.getEmail(), ""));
            oldObj.setAddress(Objects.requireNonNullElse(obj.getAddress(), ""));
            oldObj.setJob(Objects.requireNonNullElse(obj.getJob(), ""));

            service.update(oldObj);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}