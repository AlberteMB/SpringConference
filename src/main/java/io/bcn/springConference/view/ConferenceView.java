
package io.bcn.springConference.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
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



import java.awt.*;
import java.util.List;

@PageTitle("Conferences")
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
    private ComboBox<Conference> conferenceComboBox;


    public ConferenceView(ConferenceRepository conferenceRepository,BookRepository bookRepository,
                          SpeakerRepository speakerRepository) {
        this.conferenceRepository = conferenceRepository;
        this.bookRepository = bookRepository;
        this.speakerRepository = speakerRepository;


        // Full size and center
        setSizeFull();
        setAlignItems(Alignment.START);

        // Navigation buttons
        HorizontalLayout navigationLayout = createNavigationButtons();

        Component form = createForm();
        createGrid();
        // Layout
        VerticalLayout gridLayout = new VerticalLayout(grid);
        gridLayout.setSizeFull();
        gridLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // Adding components
        add(navigationLayout, form, gridLayout);
        // Grid take up the remaining space
        expand(gridLayout);

    }

    private void createGrid() {
        grid = new Grid<>(Conference.class);
        grid.setColumns("name", "date");
        grid.addColumn(conference -> conference.getBook() != null ? conference.getBook().getTitle() : "No Book")
                .setHeader("Book");
        grid.addColumn(conference -> conference.getSpeaker() != null ? conference.getSpeaker().getName() : "No Speaker")
                .setHeader("Speaker");
        grid.setSizeFull();
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
        speakerComboBox.setItems(speakerRepository.findAll());
        // ComboBox with conferences
        conferenceComboBox = new ComboBox<>("Conferences");
        conferenceComboBox.setItemLabelGenerator(Conference::getName);
        conferenceComboBox.setItems(conferenceRepository.findAll());



        // Save
        Button saveButton = new Button("Save", event -> saveConference());
        // Delete
        Button deleteButton = new Button("Delete", event -> deleteConference());

        // Add form components name date in horizontal
        HorizontalLayout nameDateLayout = new HorizontalLayout(nameField,datePicker);
        nameDateLayout.setSpacing(true);
        nameDateLayout.setPadding(true);
        nameDateLayout.setDefaultVerticalComponentAlignment(Alignment.START);
        // Add form components bookComboBox and speakerComboBox
        HorizontalLayout bookSpeakerLayout = new HorizontalLayout(bookComboBox, speakerComboBox);
        bookSpeakerLayout.setSpacing(true);
        bookSpeakerLayout.setPadding(true);
        bookSpeakerLayout.setDefaultVerticalComponentAlignment(Alignment.START);

        //Add form components saveButton conferenceComboBox and deleteButton
        VerticalLayout conferenceSaveDeleteLayout = new VerticalLayout(saveButton,conferenceComboBox, deleteButton);
        conferenceSaveDeleteLayout.setSpacing(true);
        conferenceSaveDeleteLayout.setPadding(true);
        conferenceSaveDeleteLayout.setDefaultHorizontalComponentAlignment(Alignment.START);

        VerticalLayout formLayout = new VerticalLayout(nameDateLayout,bookSpeakerLayout, conferenceSaveDeleteLayout);

        return formLayout;
    }

    private void saveConference() {
        Conference conference = new Conference();
        conference.setName(nameField.getValue());
        conference.setDate(datePicker.getValue());
        conference.setBook(bookComboBox.getValue());
        conference.setSpeaker(speakerComboBox.getValue());
        conferenceRepository.save(conference);
        // Update list and ComboBox
        updateList();
        updateConferenceComboBox();
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


    private HorizontalLayout createNavigationButtons() {
        Button speakerButton = new Button("Go to SpeakerView", event -> UI.getCurrent().navigate("speakers"));
        Button mainLayoutButton = new Button("Go to MainLayout", event -> UI.getCurrent().navigate("mainlayout"));

        // Buttons horizontally
        HorizontalLayout navigationLayout = new HorizontalLayout(mainLayoutButton, speakerButton);
        // Space between them
        navigationLayout.setSpacing(true);
        // Vertical center
        navigationLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        return navigationLayout;
    }

    private void deleteConference(){
        Conference selectedConference = conferenceComboBox.getValue();

        if (selectedConference != null) {
            // Delete from repository
            conferenceRepository.delete(selectedConference);

            // Update list in Grid and ComboBox
            updateList();
            updateConferenceComboBox();

            // Clean selection comboBox
            conferenceComboBox.clear();
        } else {
            Notification.show("Please select a conference to delete.", 3000, Notification.Position.MIDDLE);
        }

    }

    private void updateConferenceComboBox() {
        conferenceComboBox.setItems(conferenceRepository.findAll());
    }
}

