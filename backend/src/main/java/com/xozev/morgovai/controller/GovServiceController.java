package com.xozev.morgovai.controller;

import com.xozev.morgovai.dto.GovServiceDTO;
import com.xozev.morgovai.entity.GovService;
import com.xozev.morgovai.service.GovServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/services")
@RequiredArgsConstructor
public class GovServiceController {

    private final GovServiceService govServiceService;

    @GetMapping
    public ResponseEntity<Page<GovService>> getAll(
            Pageable pageable,
            @RequestParam(required = false) String name) {
        return ResponseEntity.ok(govServiceService.getAll(pageable, name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GovService> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(govServiceService.getById(id));
    }

    @PostMapping
    public ResponseEntity<GovService> create(@Valid @RequestBody GovServiceDTO dto) {
        return ResponseEntity.ok(govServiceService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GovService> update(@PathVariable UUID id, @Valid @RequestBody GovServiceDTO dto) {
        return ResponseEntity.ok(govServiceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        govServiceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
