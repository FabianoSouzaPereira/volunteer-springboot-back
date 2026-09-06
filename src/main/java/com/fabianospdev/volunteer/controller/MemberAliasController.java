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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/volunteers/{rolePath:volunteers|leaders|pastors|secretaries|employees|partners}")
public class MemberAliasController {

    private final MemberService memberService;

    public MemberAliasController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAll(@PathVariable String rolePath) {
        return ResponseEntity.ok(memberService.findAll(MemberRole.fromPath(rolePath)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findById(@PathVariable String rolePath, @PathVariable String id) {
        return ResponseEntity.ok(memberService.findById(id, MemberRole.fromPath(rolePath)));
    }

    @PostMapping
    public ResponseEntity<MemberResponse> create(
            @PathVariable String rolePath,
            @Valid @RequestBody MemberCreateRequest request
    ) {
        MemberResponse created = memberService.create(request, MemberRole.fromPath(rolePath));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> update(
            @PathVariable String rolePath,
            @PathVariable String id,
            @Valid @RequestBody MemberUpdateRequest request
    ) {
        return ResponseEntity.ok(memberService.update(id, request, MemberRole.fromPath(rolePath)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String rolePath, @PathVariable String id) {
        memberService.delete(id, MemberRole.fromPath(rolePath));
        return ResponseEntity.noContent().build();
    }
}
