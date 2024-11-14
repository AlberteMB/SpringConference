import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.bcn.springConference.model.Conference;
import io.bcn.springConference.repository.ConferenceRepository;
import io.bcn.springConference.view.MainLayout;

import java.awt.*;

/*

package io.bcn.springConference.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;


@Route("/conference")
public class ConferenceView extends VerticalLayout {



    private final TextField name = new TextField("Name");
    private final TextField email = new TextField("Email");
    private final TextField phoneNumber = new TextField("Phone Number");
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");




    // Method to create the main layout
    private Component createMainLayout() {
        // Create the 3-column layout
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        mainLayout.setPadding(false);
        mainLayout.setSpacing(false);

        // Left column (empty for spacing)
        VerticalLayout leftColumn = new VerticalLayout();
        leftColumn.setWidth("20%");

        // Center column (contains all the components)
        VerticalLayout centerColumn = new VerticalLayout();
        centerColumn.setWidth("60%");
        centerColumn.setAlignItems(Alignment.CENTER);

        // Right column (empty for spacing)
        VerticalLayout rightColumn = new VerticalLayout();
        rightColumn.setWidth("20%");

        // Create a form layout
        HorizontalLayout formLayout = new HorizontalLayout(name, email, phoneNumber);
        formLayout.setWidth("100%");
        formLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        // Create a button layout
        HorizontalLayout buttonLayout = new HorizontalLayout(save, delete);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        // Add components to the center column
        centerColumn.add(
                new H2("Customer Management"),
                formLayout,
                buttonLayout
        );

        // Add all columns to the main layout
        mainLayout.add(leftColumn, centerColumn, rightColumn);

        return mainLayout;
    }

}
*/


@Route(value = "conferences", layout = MainLayout.class)
public class ConferenceView extends VerticalLayout {
    private final ConferenceRepository repository;
    private Grid<Conference> grid;
    private TextField nameField;
    private DatePicker datePicker;

    public ConferenceView(ConferenceRepository repository) {
        this.repository = repository;
        createGrid();
        createForm();
        add(grid, createFormLayout());
    }

    private void createGrid() {
        grid = new Grid<>(Conference.class);
        grid.setColumns("name", "date");
        updateList();
    }

    private void createForm() {
        nameField = new TextField("Name");
        datePicker = new DatePicker("Date");
        Button saveButton = new Button("Save", e -> saveConference());
        // Add form components and logic
    }

    private void saveConference() {
        Conference conference = new Conference();
        conference.setName(nameField.getValue());
        conference.setDate(datePicker.getValue());
        repository.save(conference);
        updateList();
        clearForm();
    }

    private void updateList() {
        grid.setItems(repository.findAll());
    }

    private void clearForm() {
        nameField.clear();
        datePicker.clear();
    }
}