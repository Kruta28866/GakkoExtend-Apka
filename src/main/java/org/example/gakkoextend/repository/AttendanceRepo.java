package org.example.gakkoextend.repository;
import org.example.gakkoextend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepo extends JpaRepository<Attendance, UUID> {
    Optional<Attendance> findBySessionIdAndUserId(UUID sessionId, UUID userId);

    List<Attendance> findByUserIdOrderByScannedAtDesc(UUID userId);

}


