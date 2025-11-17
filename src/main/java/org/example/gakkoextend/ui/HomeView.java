// src/main/java/org/example/gakkoextend/ui/HomeView.java
package org.example.gakkoextend.ui;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Start")
@AnonymousAllowed
public class HomeView extends VerticalLayout {
    public HomeView() {
        add(new Paragraph("Witaj! Zaloguj się, aby używać aplikacji."));
        add(new Paragraph("Nauczyciel: przejdź do „Sesje (nauczyciel)” -> QR."));
        add(new Paragraph("Student: sprawdź „Moje obecności”."));
    }
}
