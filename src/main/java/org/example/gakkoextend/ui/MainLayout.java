// src/main/java/org/example/gakkoextend/ui/MainLayout.java
package org.example.gakkoextend.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import jakarta.annotation.security.RolesAllowed;
import jakarta.annotation.security.PermitAll;
import org.springframework.stereotype.Component;

@Route(value = "/main/main")
public class MainLayout extends AppLayout {

    public MainLayout(AuthenticationContext auth, AccessAnnotationChecker accessChecker) {
        // Top bar
        var title = new H1("GakkoExtend");
        title.getStyle().set("font-size", "1.2rem").set("margin", "0");
        var header = new HorizontalLayout(new DrawerToggle(), title,
                new Anchor("/logout", "Wyloguj"));
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.expand(title);
        addToNavbar(header);

        // Drawer menu
        addToDrawer(new RouterLink("Start", HomeView.class));

        if (accessChecker.hasAccess(TeacherSessionsView.class)) {
            addToDrawer(new RouterLink("Sesje (nauczyciel)", TeacherSessionsView.class));
        }
        if (accessChecker.hasAccess(StudentAttendanceView.class)) {
            addToDrawer(new RouterLink("Moje obecności", StudentAttendanceView.class));
        }
    }
}
