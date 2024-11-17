package io.bcn.springConference.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "speakers")
@NoArgsConstructor
@AllArgsConstructor
public class Speaker {
    @Id
   //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false,
            nullable = false)
    private UUID id;
    @Column(name = "name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "speakerMapped", cascade = CascadeType.ALL)
    private List<Conference> conferences;



    //method to add
    public void addConference(Conference conference) {
        this.getConferences().add(conference);
        if (conference.getSpeakerMapped() != null) conference.getSpeakerMapped().getConferences().remove(conference);
        conference.setSpeakerMapped(this);
    }



}