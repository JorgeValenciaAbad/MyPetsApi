package it.jorge.protectora.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Adoption")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Demand {

    @EmbeddedId
    private DemandKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("petId")
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Column(name = "Accept")
    private boolean accept = false;
}
