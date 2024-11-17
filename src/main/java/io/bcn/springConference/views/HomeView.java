package io.bcn.springConference.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import io.bcn.springConference.view.ConferenceView;
import io.bcn.springConference.view.SpeakerView;
@PageTitle("Main Layout")
@Route("")
public class HomeView extends AppLayout {

    public HomeView() {

        // Logo
        Image logo = new Image("logo.png", "My Logo");
        logo.setHeight("50px");
        addToNavbar(logo);

        // Navigation
        addToDrawer(new RouterLink("Conferences", ConferenceView.class));
        addToDrawer(new RouterLink("Speakers", SpeakerView.class));

        // Pie de página o elementos adicionales
        addToNavbar(new Paragraph("Main page"));

    }
}
