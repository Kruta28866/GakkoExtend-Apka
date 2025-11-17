// src/main/java/org/example/gakkoextend/ui/QrView.java
package org.example.gakkoextend.ui;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Route(value = "teacher/qr", layout = MainLayout.class)
@PageTitle("QR zajęć")
@RolesAllowed("TEACHER")
@Component
public class QrView extends VerticalLayout implements BeforeEnterObserver {

    private final Image img = new Image();
    @Value("${app.base-url}") String baseUrl;

    public QrView() {
        setAlignItems(Alignment.CENTER);
        img.setWidth("320px"); img.setHeight("320px");
        add(new Paragraph("Ten kod odświeża się co 30 s."), img);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        var qp = event.getLocation().getQueryParameters().getParameters();
        String id = qp.getOrDefault("session", List.of("")).get(0);
        if (id == null || id.isBlank()) {
            Notification.show("Brak parametru ?session=<UUID>", 4000, Notification.Position.MIDDLE);
            event.forwardTo(TeacherSessionsView.class);
            return;
        }
        refresh(id);
        // co 30 s nowy obraz
        getElement().executeJs("""
        const img = $0;
        const id = $1;
        function ref(){ img.src = '/api/sessions/'+id+'/qr?ts=' + Date.now(); }
        setInterval(ref, 30000);
      """, img.getElement(), id);
    }

    private void refresh(String id) {
        img.setSrc("/api/sessions/" + id + "/qr?ts=" + System.currentTimeMillis());
    }
}
