package org.example.gakkoextend.repository;

import lombok.Builder;
import org.example.gakkoextend.entity.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface SessionTokenRepo extends JpaRepository<SessionToken, UUID> {

    @Query("""
  select (count(t) > 0)
  from SessionToken t
  where t.session.id = :sessionId and t.token = :token and t.isActive = true and t.expiresAt > :now
""")
    boolean existsActive(UUID sessionId, String token, OffsetDateTime now);


}

