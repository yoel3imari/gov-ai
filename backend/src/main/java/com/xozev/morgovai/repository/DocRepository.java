package com.xozev.morgovai.repository;

import com.xozev.morgovai.entity.Doc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocRepository extends JpaRepository<Doc, UUID>, JpaSpecificationExecutor<Doc> {
}
