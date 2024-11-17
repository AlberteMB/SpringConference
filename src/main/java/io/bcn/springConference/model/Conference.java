package io.bcn.springConference.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "conferences")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Conference {
    @Id
    @Column(name = "id", updatable = false,
            nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "conference_name",nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SPEAKER_FK_ID", nullable = false)
    private Speaker speakerMapped;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BOOK_FK_ID", nullable = false)
    private Book bookMapped;

    public Book getBook() {
        return bookMapped;
    }

    public void setBook(Book book) {
        this.bookMapped = book;
    }

    public Speaker getSpeaker() {
        return speakerMapped;
    }

    public void setSpeaker(Speaker speaker) {
        this.speakerMapped = speaker;
    }

}