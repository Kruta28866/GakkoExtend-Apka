package org.example.gakkoextend.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Pages {
    @GetMapping("/teacher/show-qr")
    public String showQrPage() { return "show-qr"; }

    @GetMapping("/")
    public String home() { return "home"; }
}
