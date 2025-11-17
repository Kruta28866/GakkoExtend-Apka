package org.example.gakkoextend.web;

import org.example.gakkoextend.entity.AppUser;
import org.example.gakkoextend.entity.Attendance;
import org.example.gakkoextend.repository.AppUserRepo;
import org.example.gakkoextend.repository.AttendanceRepo;
import org.example.gakkoextend.repository.ClassSessionRepo;
import org.example.gakkoextend.service.TokenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.UUID;

@Controller
public class AttendanceController {

    private final TokenService tokenService;
    private final ClassSessionRepo sessions;
    private final AppUserRepo users;
    private final AttendanceRepo attendanceRepo;

    public AttendanceController(TokenService tokenService,
                                ClassSessionRepo sessions,
                                AppUserRepo users,
                                AttendanceRepo attendanceRepo) {
        this.tokenService = tokenService;
        this.sessions = sessions;
        this.users = users;
        this.attendanceRepo = attendanceRepo;
    }

    @GetMapping("/attend")
    public String attend(@RequestParam UUID session,
                         @RequestParam("t") String token,
                         Principal principal,
                         Model model) {

        if (!tokenService.validate(session, token)) {
            model.addAttribute("msg", "Nieprawidłowy lub wygasły token.");
            return "attend-error";
        }

        AppUser u = users.findByEmail(principal.getName()).orElseThrow();
        var s = sessions.findById(session).orElseThrow();

        attendanceRepo.findBySessionIdAndUserId(session, u.getId())
                .orElseGet(() -> {
                    Attendance a = new Attendance();
                    a.setSession(s);
                    a.setUser(u);
                    return attendanceRepo.save(a);
                });

        model.addAttribute("course", s.getCourse().getName());
        return "attend-ok";
    }
}
