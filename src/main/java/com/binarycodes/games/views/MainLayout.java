package com.binarycodes.games.views;

import com.binarycodes.games.util.GenericUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        this.setPrimarySection(Section.DRAWER);
        this.addDrawerContent();
        this.addHeaderContent();
    }

    private void addHeaderContent() {
        final DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        this.viewTitle = new H2();
        this.viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        this.addToNavbar(true, toggle, this.viewTitle);
    }

    private void addDrawerContent() {
        final H1 appName = new H1("Minesweeper");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        final Header header = new Header(appName);

        final Scroller scroller = new Scroller(this.createNavigation());

        this.addToDrawer(header, scroller, this.createFooter());
    }

    private SideNav createNavigation() {
        final SideNav nav = new SideNav();

        GenericUtil.findAllGames().forEach((clazz, game) -> {
            nav.addItem(new SideNavItem(game.title(), clazz, game.icon().create()));
        });

        return nav;
    }

    private Footer createFooter() {
        return new Footer();
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        this.viewTitle.setText(this.getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        final PageTitle title = this.getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
