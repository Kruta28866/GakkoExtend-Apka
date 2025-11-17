package org.example.gakkoextend.ui;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;

@PermitAll
public class MainLayout extends AppLayout {

    private final AuthenticationContext auth;
    private final SideNav sideNav = new SideNav();

    public MainLayout(AuthenticationContext auth) {
        this.auth = auth;

        setPrimarySection(Section.DRAWER);

        // Górny pasek
        addToNavbar(new DrawerToggle(), new H1("Gakko Extend"), buildUserMenu());

        // Lewy panel
        addToDrawer(new Scroller(sideNav));

        // <<< KLUCZOWE >>> tworzymy elementy zależne od Router/Service PO attachu
        addAttachListener(this::buildNavItemsOnAttach);
    }

    private MenuBar buildUserMenu() {
        MenuBar menu = new MenuBar();
        String username = auth.getPrincipalName().orElse("anonymous");
        MenuItem user = menu.addItem(username);
        user.getSubMenu().addItem("Wyloguj", e -> auth.logout());
        return menu;
    }

    private void buildNavItemsOnAttach(AttachEvent e) {
        sideNav.removeAll();
        // Dodaj tutaj swoje widoki; przykład poniżej:
        sideNav.addItem(
                new SideNavItem("Obecności", StudentAttendanceView.class)
                // , new SideNavItem("Inny widok", SomeOtherView.class)
        );
    }
}
