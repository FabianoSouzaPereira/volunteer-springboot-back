package com.fabianospdev.volunteer.controller;

import com.fabianospdev.volunteer.dto.member.MemberCreateRequest;
import com.fabianospdev.volunteer.dto.member.MemberResponse;
import com.fabianospdev.volunteer.dto.member.MemberUpdateRequest;
import com.fabianospdev.volunteer.model.MemberRole;
import com.fabianospdev.volunteer.services.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAll(@RequestParam(required = false) MemberRole role) {
        return ResponseEntity.ok(memberService.findAll(role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(memberService.findById(id, null));
    }

    @PostMapping
    public ResponseEntity<MemberResponse> create(@Valid @RequestBody MemberCreateRequest request) {
        MemberResponse created = memberService.create(request, null);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> update(@PathVariable String id, @Valid @RequestBody MemberUpdateRequest request) {
        return ResponseEntity.ok(memberService.update(id, request, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        memberService.delete(id, null);
        return ResponseEntity.noContent().build();
    }
}
