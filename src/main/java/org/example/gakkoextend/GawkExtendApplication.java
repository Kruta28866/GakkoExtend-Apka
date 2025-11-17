package org.example.gakkoextend;

import org.example.gakkoextend.entity.*;
import org.example.gakkoextend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;

@SpringBootApplication
public class GawkExtendApplication {
    public static void main(String[] args) {
        SpringApplication.run(GawkExtendApplication.class, args);
    }

    @Bean
    CommandLineRunner seed(RoleRepo roles,
                           AppUserRepo users,
                           CourseRepo courses,
                           ClassSessionRepo sessions) {
        return args -> {
            // ROLE: STUDENT
            Role rStudent = roles.findByName("STUDENT").orElseGet(() -> {
                Role r = new Role();
                r.setName("STUDENT");
                return roles.save(r);
            });

            // ROLE: TEACHER
            Role rTeacher = roles.findByName("TEACHER").orElseGet(() -> {
                Role r = new Role();
                r.setName("TEACHER");
                return roles.save(r);
            });

            // USER: teacher
            AppUser teacher = users.findByEmail("teacher@uczelnia.pl").orElseGet(() -> {
                AppUser u = new AppUser();
                u.setEmail("teacher@uczelnia.pl");
                u.setFullName("Prowadzący");
                u.setPasswordHash("pass");
                u.getRoles().add(rTeacher);
                return users.save(u);
            });

            // USER: student
            users.findByEmail("student@uczelnia.pl").orElseGet(() -> {
                AppUser u = new AppUser();
                u.setEmail("student@uczelnia.pl");
                u.setFullName("Student");
                u.setPasswordHash("pass");
                u.getRoles().add(rStudent);
                return users.save(u);
            });

            // COURSE
            Course course = courses.findAll().stream().findFirst().orElseGet(() -> {
                Course c = new Course();
                c.setOwner(teacher);
                c.setName("Programowanie");
                c.setCode("PRG-101");
                return courses.save(c);
            });

            // CLASS SESSION (jeśli brak)
            if (sessions.count() == 0) {
                ClassSession s = new ClassSession();
                s.setCourse(course);
                s.setStartsAt(OffsetDateTime.now().minusMinutes(5));
                s.setEndsAt(OffsetDateTime.now().plusHours(2));
                sessions.save(s);
            }
        };
    }
}
