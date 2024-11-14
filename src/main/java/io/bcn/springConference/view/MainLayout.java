package io.bcn.springConference.view;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import io.bcn.springConference.model.Conference;
import io.bcn.springConference.repository.ConferenceRepository;


@Route
public class MainLayout extends VerticalLayout {

    private ConferenceRepository repository;
    private Grid<Conference> grid;
    private TextField nameField;
    private DatePicker datePicker;

    public void ConferenceView(ConferenceRepository repository) {
        this.repository = repository;
        createGrid();
        createForm();
        add(grid, createForm());
    }

    private void createGrid() {
        grid = new Grid<>(Conference.class);
        grid.setColumns("name", "date");
        updateList();
    }

    private Component createForm() {
        nameField = new TextField("Name");
        datePicker = new DatePicker("Date");
        Button saveButton = new Button("Save", e -> saveConference());
        // Add form components and logic
        FormLayout formLayout = new FormLayout();
        formLayout.add(nameField, datePicker, saveButton);
        return formLayout;
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