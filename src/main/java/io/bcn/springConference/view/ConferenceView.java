
package io.bcn.springConference.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
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
    private ComboBox<Conference> conferenceComboBox;
    private final Binder<Conference> binder = new Binder<>(Conference.class);
    private ComboBox<Conference> updateConferenceComboBox;
    private Button updateButton;
    private TextField nameField;
    private DatePicker datePicker;
    private ComboBox<Book> bookComboBox;
    private ComboBox<Speaker> speakerComboBox;


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

        Component createForm = createForm();
        Component updateForm = createUpdateForm();
        createGrid();
        // Layout
        VerticalLayout gridLayout = new VerticalLayout(grid);
        gridLayout.setSizeFull();
        gridLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // Adding components
        add(navigationLayout, createForm, updateForm, gridLayout);
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
        conferenceComboBox = new ComboBox<>("Select to delete");
        conferenceComboBox.setItemLabelGenerator(Conference::getName);
        conferenceComboBox.setItems(conferenceRepository.findAll());

//        Label createLabel = new Label("Create Conference");
//        createLabel.addClassName("center-text");

        // Save
        Button saveButton = new Button("Save", event -> saveConference());
        // Delete
        Button deleteButton = new Button("Delete", event -> deleteConference());

        // Bind fields to the Conference class
        binder.forField(nameField)
                .asRequired("Name is required")
                .bind(Conference::getName, Conference::setName);

        binder.forField(datePicker)
                .asRequired("Date is required")
                .bind(Conference::getDate, Conference::setDate);

        binder.forField(bookComboBox)
                .asRequired("Book selection is required")
                .bind(Conference::getBook, Conference::setBook);

        binder.forField(speakerComboBox)
                .asRequired("Speaker selection is required")
                .bind(Conference::getSpeaker, Conference::setSpeaker);


        // Add form components name date bookComboBox
        HorizontalLayout nameDateLayout = new HorizontalLayout(nameField, datePicker,bookComboBox );
        nameDateLayout.setSpacing(true);
        nameDateLayout.setPadding(true);
        nameDateLayout.setDefaultVerticalComponentAlignment(Alignment.START);
        // Add form components speakerComboBox and saveButton
        HorizontalLayout bookSpeakerLayout = new HorizontalLayout(speakerComboBox,saveButton);
        saveButton.addClassName("move-down");
        bookSpeakerLayout.setSpacing(true);
        bookSpeakerLayout.setPadding(true);
        bookSpeakerLayout.setDefaultVerticalComponentAlignment(Alignment.START);

        //Add form components conferenceComboBox and deleteButton
        HorizontalLayout conferenceSaveDeleteLayout = new HorizontalLayout(conferenceComboBox, deleteButton);
        deleteButton.addClassName("move-down");
        conferenceSaveDeleteLayout.setSpacing(true);
        conferenceSaveDeleteLayout.setPadding(true);
        conferenceSaveDeleteLayout.setDefaultVerticalComponentAlignment(Alignment.START);



        return new VerticalLayout(nameDateLayout,bookSpeakerLayout, conferenceSaveDeleteLayout);
    }

    private void saveConference() {
        Conference conference = new Conference();

        if (binder.writeBeanIfValid(conference))  {
            conferenceRepository.save(conference);
            updateConferenceComboBox();
            updateList();
            clearForm();
        } else {
            binder.validate();
            Notification.show("Please fill in all required fields", 3000, Notification.Position.MIDDLE);
        }

//        conference.setName(nameField.getValue());
//        conference.setDate(datePicker.getValue());
//        conference.setBook(bookComboBox.getValue());
//        conference.setSpeaker(speakerComboBox.getValue());
//        conferenceRepository.save(conference);
//        // Update list and ComboBox
//        updateList();
//        updateConferenceComboBox();
//        clearForm();
    }

    private void updateList() {
        grid.setItems(conferenceRepository.findAll());
        // Updating manually just in case
        grid.getDataProvider().refreshAll();

        binder.readBean(new Conference());

//        List<Conference> conferences = conferenceRepository.findAll();
//        System.out.println("Conferences in DB: " + conferences.size());
//        for (Conference c : conferences) {
//            System.out.println("Conference: " + c.getName() + " - " + c.getDate());
//        }
//        grid.setItems(conferences);
    }

    private void clearForm() {
        binder.readBean(null);
    }


    private HorizontalLayout createNavigationButtons() {
        Button speakerButton = new Button("Go to SpeakerView", event -> UI.getCurrent().navigate("speakers"));
        Button mainLayoutButton = new Button("Go to MainLayout", event -> UI.getCurrent().navigate(""));

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

    private Component createUpdateForm() {
        updateConferenceComboBox = new ComboBox<>("Select Conference");
        updateConferenceComboBox.setItems(conferenceRepository.findAll());
        updateConferenceComboBox.setItemLabelGenerator(Conference::getName);

        // Add listeners to load selected conference details
        updateConferenceComboBox.addValueChangeListener(event -> {
            Conference selectedConference = event.getValue();
            if (selectedConference != null) {
                loadConferenceDetails(selectedConference);
            } else {
                clearUpdateFields();
            }
        });

        // Fields to update
        TextField updateNameField = new TextField("Name");
        DatePicker updateDatePicker = new DatePicker("Date");
        ComboBox<Book> updateBookComboBox = new ComboBox<>("Book");
        updateBookComboBox.setItemLabelGenerator(Book::getTitle);
        updateBookComboBox.setItems(bookRepository.findAll());

        ComboBox<Speaker> updateSpeakerComboBox = new ComboBox<>("Speaker");
        updateSpeakerComboBox.setItemLabelGenerator(Speaker::getName);
        updateSpeakerComboBox.setItems(speakerRepository.findAll());

        // Update button
        updateButton = new Button("Update", event -> {
            Conference selectedConference = updateConferenceComboBox.getValue();
            if (selectedConference != null) {
                updateConference(selectedConference, updateNameField, updateDatePicker, updateBookComboBox, updateSpeakerComboBox);
            } else {
                Notification.show("No conference selected!", 3000, Notification.Position.MIDDLE);
            }
        });

        // Layout
        HorizontalLayout selectNameDateLayout = new HorizontalLayout(updateConferenceComboBox, updateNameField, updateDatePicker);
        updateButton.addClassName("move-down");
        selectNameDateLayout.setSpacing(true);
        selectNameDateLayout.setPadding(true);
        selectNameDateLayout.setDefaultVerticalComponentAlignment(Alignment.START);

        HorizontalLayout bookSpeakerButtonLayout = new HorizontalLayout(updateBookComboBox, updateSpeakerComboBox, updateButton);
        bookSpeakerButtonLayout.setSpacing(true);
        bookSpeakerButtonLayout.setPadding(true);
        bookSpeakerButtonLayout.setDefaultVerticalComponentAlignment(Alignment.START);
        VerticalLayout twoLinesLayout = new VerticalLayout(selectNameDateLayout, bookSpeakerButtonLayout);
        twoLinesLayout.setSpacing(true);
        twoLinesLayout.setPadding(true);

        return twoLinesLayout;
    }

    private void loadConferenceDetails(Conference conference) {
        nameField.setValue(conference.getName() != null ? conference.getName() : "");
        datePicker.setValue(conference.getDate());
        bookComboBox.setValue(conference.getBook());
        speakerComboBox.setValue(conference.getSpeaker());
    }

    private void updateConference(Conference conference, TextField updateNameField, DatePicker updateDatePicker, ComboBox<Book> updateBookComboBox, ComboBox<Speaker> updateSpeakerComboBox) {
        conference.setName(updateNameField.getValue());
        conference.setDate(updateDatePicker.getValue());
        conference.setBook(updateBookComboBox.getValue());
        conference.setSpeaker(updateSpeakerComboBox.getValue());

        conferenceRepository.save(conference);
        Notification.show("Conference updated successfully!", 3000, Notification.Position.MIDDLE);

        updateConferenceComboBox.setItems(conferenceRepository.findAll()); // Refresh the ComboBox
        updateList(); // Refresh the grid
        clearUpdateFields();
    }

    private void clearUpdateFields() {
        nameField.clear();
        datePicker.clear();
        bookComboBox.clear();
        speakerComboBox.clear();
    }
}

