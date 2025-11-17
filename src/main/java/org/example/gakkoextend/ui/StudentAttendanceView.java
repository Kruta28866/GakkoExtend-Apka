// src/main/java/org/example/gakkoextend/ui/StudentAttendanceView.java
package org.example.gakkoextend.ui;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.example.gakkoextend.entity.Attendance;
import org.example.gakkoextend.entity.AppUser;
import org.example.gakkoextend.repository.AppUserRepo;
import org.example.gakkoextend.repository.AttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "student/attendance", layout = MainLayout.class)
@PageTitle("Moje obecności")
//@RolesAllowed("STUDENT") // nie łącz z @PermitAll
@PermitAll
public class StudentAttendanceView extends VerticalLayout {

    private final AttendanceRepo attendanceRepo;
    private final AppUserRepo users;

    private final AuthenticationContext auth;

    private final Grid<Attendance> grid = new Grid<>(Attendance.class, false);

    public StudentAttendanceView(AttendanceRepo attendanceRepo,
                                 AppUserRepo users,
                                 AuthenticationContext auth) {
        this.attendanceRepo = attendanceRepo;
        this.users = users;
        this.auth = auth;

        setSizeFull();

        grid.addColumn(a -> a.getSession().getCourse().getName()).setHeader("Kurs").setAutoWidth(true);
        grid.addColumn(a -> a.getSession().getStartsAt()).setHeader("Sesja od");
        grid.addColumn(Attendance::getScannedAt).setHeader("Zarejestrowano");

        add(grid);
    }

    @Override
    protected void onAttach(AttachEvent e) {
        String email = auth.getPrincipalName()
                .orElseThrow(() -> new IllegalStateException("Brak zalogowanego użytkownika"));
        AppUser me = users.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Użytkownik nie istnieje: " + email));

        grid.setItems(attendanceRepo.findByUserIdOrderByScannedAtDesc(me.getId()));
    }
}
