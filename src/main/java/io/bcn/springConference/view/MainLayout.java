package io.bcn.springConference.view;



import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import io.bcn.springConference.model.Book;
import io.bcn.springConference.repository.BookRepository;

@PageTitle("Main Layout")
@Route("/mainlayout")
public class MainLayout extends AppLayout {

    public MainLayout() {
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







//    private final BookRepository repository;
//
//
//    private final TextField title = new TextField("Title");
//    private final TextField author = new TextField("Author");
//    private final TextField isbn = new TextField("ISBN");
//    private final Button save = new Button("Save");
//    private final Button delete = new Button("Delete");
//
//    private final Avatar avatar = new Avatar();
//    private final ComboBox<Book> comboBox = new ComboBox<>("Book");
//    private final DatePicker datePicker = new DatePicker("Pick a date");
//
//
//    public MainLayout(BookRepository repository) {
//        this.repository = repository;
//
//        // Components
//        configureAvatar();
//        configureComboBox();
//        configureDatePicker();
//
//
//        // Add components to the layout
//        add(createHeader(), createMainContent());
//        setWidthFull();
//        setSpacing(false);
//        setPadding(false);
//
//    }
//
//    private void configureAvatar() {
//        avatar.setName("John Doe");
//        avatar.setImage("https://i.pravatar.cc/150"); // Example for the picture
//    }
//
//    private void configureComboBox() {
//        comboBox.setItems(repository.findAll());
//        comboBox.setItemLabelGenerator(Book::getTitle);
//        comboBox.setPlaceholder("Select a book");
//    }
//
//    private void configureDatePicker() {
//        datePicker.setPlaceholder("Select a date");
//    }
//
//    private Component createHeader() {
//        HorizontalLayout header = new HorizontalLayout(avatar, comboBox, datePicker);
//        header.setWidthFull();
//        header.setSpacing(true);
//        header.setAlignItems(Alignment.CENTER);
//        header.setPadding(true);
//        return header;
//    }
//
//    private Component createMainContent() {
//        // Form layout
//        HorizontalLayout formLayout = new HorizontalLayout(title, author, isbn);
//        formLayout.setWidthFull();
//        formLayout.setSpacing(true);
//
//        // Button layout
//        HorizontalLayout buttonLayout = new HorizontalLayout(save, delete);
//        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
//
//        // Main content layout
//        VerticalLayout mainContent = new VerticalLayout(
//                new H2("Book Management"),
//                formLayout,
//                buttonLayout
//        );
//        mainContent.setWidthFull();
//        mainContent.setAlignItems(Alignment.CENTER);
//
//        return mainContent;
//    }




