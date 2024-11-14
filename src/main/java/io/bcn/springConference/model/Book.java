package io.bcn.springConference.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false,
            nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String ISBN;

    @OneToMany(mappedBy = "bookMapped", cascade = CascadeType.ALL)
    private List<Conference> conferences ;


    //method to add
    public void addConference(Conference conference) {
        this.getConferences().add(conference);
        if (conference.getBookMapped() != null) conference.getBookMapped().getConferences().remove(conference);
        conference.setBookMapped(this);
    }
}