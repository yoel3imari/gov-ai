package com.xozev.morgovai.repository;

import com.xozev.morgovai.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {
}
