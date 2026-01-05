package com.xozev.morgovai.service;

import com.xozev.morgovai.dto.GovServiceDTO;
import com.xozev.morgovai.entity.GovService;
import com.xozev.morgovai.repository.GovServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GovServiceService {

    private final GovServiceRepository govServiceRepository;

    public GovService create(GovServiceDTO dto) {
        GovService govService = new GovService();
        govService.setName(dto.getName());
        return govServiceRepository.save(govService);
    }

    public GovService update(UUID id, GovServiceDTO dto) {
        GovService govService = getById(id);
        govService.setName(dto.getName());
        return govServiceRepository.save(govService);
    }

    public void delete(UUID id) {
        govServiceRepository.deleteById(id);
    }

    public GovService getById(UUID id) {
        return govServiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GovService not found"));
    }

    public Page<GovService> getAll(Pageable pageable, String nameFilter) {
        Specification<GovService> spec = Specification.where(null);
        if (nameFilter != null && !nameFilter.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + nameFilter.toLowerCase() + "%"));
        }
        return govServiceRepository.findAll(spec, pageable);
    }
}
