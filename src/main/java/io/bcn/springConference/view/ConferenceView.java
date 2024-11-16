
package io.bcn.springConference.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.bcn.springConference.model.Book;
import io.bcn.springConference.model.Conference;
import io.bcn.springConference.model.Speaker;
import io.bcn.springConference.repository.BookRepository;
import io.bcn.springConference.repository.ConferenceRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import io.bcn.springConference.repository.SpeakerRepository;

import java.util.List;


@Route(value = "conferences"/*, layout = MainLayout.class*/)
public class ConferenceView extends VerticalLayout {
    private final ConferenceRepository conferenceRepository;
    private final BookRepository bookRepository;
    private final SpeakerRepository speakerRepository;
    private Grid<Conference> grid;
    private TextField nameField;
    private DatePicker datePicker;
    private ComboBox<Book> bookComboBox;
    private ComboBox<Speaker> speakerComboBox;


    public ConferenceView(ConferenceRepository conferenceRepository,BookRepository bookRepository, SpeakerRepository speakerRepository ) {
        this.conferenceRepository = conferenceRepository;
        this.bookRepository = bookRepository;
        this.speakerRepository = speakerRepository;
        navigateSpeakerView();
        createGrid();
        add(grid, createForm());

    }

    private void createGrid() {
        grid = new Grid<>(Conference.class);
        grid.setColumns("name", "date");
        grid.addColumn(conference -> conference.getBook() != null ? conference.getBook().getTitle() : "No Book")
                .setHeader("Book");
        grid.addColumn(conference -> conference.getSpeaker() != null ? conference.getSpeaker().getName() : "No Speaker")
                .setHeader("Speaker");
        updateList();
    }

    private Component createForm() {
        nameField = new TextField("Name");
        datePicker = new DatePicker("Date");
        // ComboBox with books
        bookComboBox = new ComboBox<>("Book");
        bookComboBox.setItemLabelGenerator(Book::getTitle);
        bookComboBox.setItems(bookRepository.findAll());
        // ComboBox with speakers
        speakerComboBox = new ComboBox<>("Speaker");
        speakerComboBox.setItemLabelGenerator(Speaker::getName);
        speakerComboBox.setItems(speakerRepository.findAll()); //


        Button saveButton = new Button("Save", event -> saveConference());

        // Add form components and logic
        VerticalLayout formLayout = new VerticalLayout(nameField,bookComboBox, speakerComboBox, datePicker, saveButton);
        formLayout.setSpacing(true);
        formLayout.setPadding(true);

        return formLayout;
    }

    private void saveConference() {
        Conference conference = new Conference();
        conference.setName(nameField.getValue());
        conference.setDate(datePicker.getValue());
        conference.setBook(bookComboBox.getValue());
        conference.setSpeaker(speakerComboBox.getValue());
        conferenceRepository.save(conference);
        updateList();
        clearForm();
    }

    private void updateList() {
        grid.setItems(conferenceRepository.findAll());
        // Updating manually just in case
        grid.getDataProvider().refreshAll();

        List<Conference> conferences = conferenceRepository.findAll();
        System.out.println("Conferences in DB: " + conferences.size());
        for (Conference c : conferences) {
            System.out.println("Conference: " + c.getName() + " - " + c.getDate());
        }
        grid.setItems(conferences);
    }

    private void clearForm() {
        nameField.clear();
        datePicker.clear();
    }

    private void navigateSpeakerView(){
        Button speakerButton = new Button("Go to SpeakerView",
                event -> UI.getCurrent().navigate("speakers"));
        add(speakerButton);
    }
}

