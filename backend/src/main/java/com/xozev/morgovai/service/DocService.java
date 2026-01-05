package com.xozev.morgovai.service;

import com.xozev.morgovai.dto.DocDTO;
import com.xozev.morgovai.entity.Doc;
import com.xozev.morgovai.entity.GovService;
import com.xozev.morgovai.repository.DocRepository;
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
public class DocService {

    private final DocRepository docRepository;
    private final GovServiceRepository govServiceRepository;
    private final RagService ragService;

    public Doc create(DocDTO dto) {
        GovService service = govServiceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new EntityNotFoundException("GovService not found"));

        Doc doc = new Doc();
        doc.setTitle(dto.getTitle());
        doc.setPath(dto.getPath());
        doc.setSize(dto.getSize());
        doc.setLang(dto.getLang());
        doc.setService(service);

        Doc savedDoc = docRepository.save(doc);
        
        // Trigger Ingestion
        ragService.ingest(savedDoc);
        
        return savedDoc;
    }

    public Doc update(UUID id, DocDTO dto) {
        Doc doc = getById(id);
        
        if (dto.getServiceId() != null && !dto.getServiceId().equals(doc.getService().getId())) {
             GovService service = govServiceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new EntityNotFoundException("GovService not found"));
             doc.setService(service);
        }

        if (dto.getTitle() != null) doc.setTitle(dto.getTitle());
        if (dto.getPath() != null) doc.setPath(dto.getPath());
        if (dto.getSize() != null) doc.setSize(dto.getSize());
        if (dto.getLang() != null) doc.setLang(dto.getLang());

        return docRepository.save(doc);
    }

    public void delete(UUID id) {
        docRepository.deleteById(id);
    }

    public Doc getById(UUID id) {
        return docRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doc not found"));
    }

    public Page<Doc> getAll(Pageable pageable, String titleFilter, UUID serviceId) {
        Specification<Doc> spec = Specification.where(null);
        if (titleFilter != null && !titleFilter.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + titleFilter.toLowerCase() + "%"));
        }
        if (serviceId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("service").get("id"), serviceId));
        }
        return docRepository.findAll(spec, pageable);
    }
}
