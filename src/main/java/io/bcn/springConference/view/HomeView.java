package io.bcn.springConference.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Main Layout")
@Route("")
public class HomeView extends AppLayout {

    public HomeView() {
        // Avatar
        Avatar avatar = new Avatar("Alberte Martínez");
        avatar.setImage("avatar.png"); //
        avatar.setHeight("50px");
        avatar.setWidth("50px");


        // Layout para logo y avatar
        HorizontalLayout navbarLayout = new HorizontalLayout(avatar);
        avatar.addClassName("avatar");
        navbarLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        navbarLayout.setSpacing(true);

        addToNavbar(navbarLayout);


        RouterLink conferencesLink = new RouterLink("Conferences", ConferenceView.class);
        conferencesLink.addClassName("drawer-link");

        RouterLink speakersLink = new RouterLink("Speakers", SpeakerView.class);
        speakersLink.addClassName("drawer-link");

        addToDrawer(conferencesLink, speakersLink);

        HorizontalLayout spaceLayout = new HorizontalLayout();
        spaceLayout.setWidth("20px");
        spaceLayout.setHeight("20px");

        addToNavbar(spaceLayout);

        addToNavbar(new Paragraph("Main page"));
    }
}
