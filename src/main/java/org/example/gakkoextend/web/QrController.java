package org.example.gakkoextend.web;

import org.example.gakkoextend.service.QrService;
import org.example.gakkoextend.service.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@RestController
public class QrController {

    private final TokenService tokenService;
    private final QrService qrService;

    @Value("${app.base-url}")
    private String baseUrl;

    public QrController(TokenService tokenService, QrService qrService) {
        this.tokenService = tokenService;
        this.qrService = qrService;
    }

    @GetMapping(value = "/api/sessions/{id}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] qr(@PathVariable UUID id) {
        String token = tokenService.issueToken(id, Duration.ofSeconds(45));
        String url = baseUrl + "/attend?session=" + id + "&t=" + token;
        return qrService.toPng(url);
    }
}
