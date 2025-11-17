package org.example.gakkoextend.repository;
import org.example.gakkoextend.entity.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface ClassSessionRepo extends JpaRepository<ClassSession, UUID> {}
