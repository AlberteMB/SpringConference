package io.bcn.springConference.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import io.bcn.springConference.model.Speaker;
import io.bcn.springConference.repository.SpeakerRepository;

import java.util.UUID;

@PageTitle("Speakers")
@Route(value = "speakers")
public class SpeakerView extends VerticalLayout {

    private final SpeakerRepository repository;
    private Grid<Speaker> grid;
    private TextField nameField;

    public SpeakerView(SpeakerRepository repository) {
        this.repository = repository;
        //createForm();

        HorizontalLayout navigationLayout = createNavigationButtons();

        createGrid();
        add(navigationLayout,grid, createForm());
    }

    private void createGrid() {
        grid = new Grid<>(Speaker.class);
        grid.setColumns("name");
        updateList();
    }

    private Component createForm() {
        nameField = new TextField("Name");
        Button saveButton = new Button("Save", event -> saveSpeaker());

        // Add form components and logic
        VerticalLayout formLayout = new VerticalLayout(nameField, saveButton);
        formLayout.setSpacing(true);
        formLayout.setPadding(true);

        return formLayout;

    }

    private void saveSpeaker() {
        Speaker speaker = new Speaker();
        speaker.setId(UUID.randomUUID());
        speaker.setName(nameField.getValue());
        repository.save(speaker);
        updateList();
        clearForm();
    }

    private void updateList() {
        grid.setItems(repository.findAll());
    }

    private void clearForm() {
        nameField.clear();
    }

    private HorizontalLayout createNavigationButtons() {
        Button conferenceButton = new Button("Go to ConferenceView", event -> UI.getCurrent().navigate("conferences"));
        Button mainLayoutButton = new Button("Go to MainLayout", event -> UI.getCurrent().navigate("mainlayout"));

        // Buttons horizontally
        HorizontalLayout navigationLayout = new HorizontalLayout(mainLayoutButton, conferenceButton);
        // Space between them
        navigationLayout.setSpacing(true);
        // Vertical center
        navigationLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        return navigationLayout;
    }
}
