package org.example.gakkoextend.service;

import org.example.gakkoextend.entity.ClassSession;
import org.example.gakkoextend.entity.SessionToken;
import org.example.gakkoextend.repository.ClassSessionRepo;
import org.example.gakkoextend.repository.SessionTokenRepo;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class TokenService {

    private final SessionTokenRepo tokens;
    private final ClassSessionRepo sessions;
    private final SecureRandom rnd = new SecureRandom();

    public TokenService(SessionTokenRepo tokens, ClassSessionRepo sessions) {
        this.tokens = tokens;
        this.sessions = sessions;
    }

    public String issueToken(UUID sessionId, Duration ttl) {
        ClassSession s = sessions.findById(sessionId).orElseThrow();

        byte[] buf = new byte[6];
        rnd.nextBytes(buf);
        String tok = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(buf).substring(0, 8);

        SessionToken t = new SessionToken();
        t.setSession(s);
        t.setToken(tok);
        t.setExpiresAt(OffsetDateTime.now().plus(ttl));
        // UWAGA: dla pola boolean nazwanego "isActive" setter nazywa się setActive(...)
        t.setActive(true);

        tokens.save(t);
        return tok;
    }

    public boolean validate(UUID sessionId, String token) {
        return tokens.existsActive(sessionId, token, OffsetDateTime.now());
    }
}
