package com.xozev.morgovai.repository;

import com.xozev.morgovai.entity.LlmResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LlmResponseRepository extends JpaRepository<LlmResponse, UUID> {
}
