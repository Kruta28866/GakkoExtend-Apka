// src/main/java/org/example/gakkoextend/ui/TeacherSessionsView.java
package org.example.gakkoextend.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import org.example.gakkoextend.entity.ClassSession;
import org.example.gakkoextend.repository.ClassSessionRepo;
import org.springframework.stereotype.Component;

@Route(value = "teacher/sessions", layout = MainLayout.class)
@PageTitle("Sesje")
@RolesAllowed("TEACHER")
@Component
public class TeacherSessionsView extends VerticalLayout {

    public TeacherSessionsView(ClassSessionRepo sessions) {
        setSizeFull();

        Grid<ClassSession> grid = new Grid<>(ClassSession.class, false);
        grid.addColumn(s -> s.getCourse().getName()).setHeader("Kurs").setAutoWidth(true);
        grid.addColumn(ClassSession::getStartsAt).setHeader("Start");
        grid.addColumn(ClassSession::getEndsAt).setHeader("Koniec");
        grid.addComponentColumn(s ->
                        new Button("QR", e -> getUI().ifPresent(ui ->
                                ui.navigate("teacher/qr",
                                        QueryParameters.simple(java.util.Map.of("session", s.getId().toString()))))))
                .setHeader("Akcje");

        grid.setItems(sessions.findAll());
        add(grid);
    }
}
