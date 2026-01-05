package com.xozev.morgovai.repository;

import com.xozev.morgovai.entity.GovService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GovServiceRepository extends JpaRepository<GovService, UUID>, JpaSpecificationExecutor<GovService> {
}
