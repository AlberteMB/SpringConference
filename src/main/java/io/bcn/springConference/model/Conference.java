package io.bcn.springConference.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate date;

    @OneToMany(mappedBy = "conference")
    private List<Speaker> speakers;
    // Getters and setters
}