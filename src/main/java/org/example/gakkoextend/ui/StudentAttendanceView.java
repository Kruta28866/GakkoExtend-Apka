// src/main/java/org/example/gakkoextend/ui/StudentAttendanceView.java
package org.example.gakkoextend.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.RouteScope;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.example.gakkoextend.entity.Attendance;
import org.example.gakkoextend.entity.AppUser;
import org.example.gakkoextend.repository.AppUserRepo;
import org.example.gakkoextend.repository.AttendanceRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Route(value = "student/attendance", layout = MainLayout.class)
@PageTitle("Moje obecności")
@RolesAllowed("STUDENT")
@PermitAll
@Component
public class StudentAttendanceView extends VerticalLayout {

    public StudentAttendanceView(AttendanceRepo attendanceRepo, AppUserRepo users) {
        setSizeFull();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser me = users.findByEmail(email).orElseThrow();

        Grid<Attendance> grid = new Grid<>(Attendance.class, false);
        grid.addColumn(a -> a.getSession().getCourse().getName()).setHeader("Kurs").setAutoWidth(true);
        grid.addColumn(a -> a.getSession().getStartsAt()).setHeader("Sesja od");
        grid.addColumn(Attendance::getScannedAt).setHeader("Zarejestrowano");

        grid.setItems(attendanceRepo.findByUserIdOrderByScannedAtDesc(me.getId()));
        add(grid);
    }
}
