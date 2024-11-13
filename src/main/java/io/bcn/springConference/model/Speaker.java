package io.bcn.springConference.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Speaker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "conference_id")
    private Conference conference;
    // Getters and setters
}