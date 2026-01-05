package com.xozev.morgovai.controller;

import com.xozev.morgovai.dto.DocDTO;
import com.xozev.morgovai.entity.Doc;
import com.xozev.morgovai.service.DocService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/docs")
@RequiredArgsConstructor
public class DocController {

    private final DocService docService;

    @GetMapping
    public ResponseEntity<Page<Doc>> getAll(
            Pageable pageable,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) UUID serviceId) {
        return ResponseEntity.ok(docService.getAll(pageable, title, serviceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doc> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(docService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Doc> create(@Valid @RequestBody DocDTO dto) {
        return ResponseEntity.ok(docService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doc> update(@PathVariable UUID id, @Valid @RequestBody DocDTO dto) {
        return ResponseEntity.ok(docService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        docService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
