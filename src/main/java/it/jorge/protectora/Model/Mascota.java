package it.jorge.protectora.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="mascota")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pet_id")
    private int pet_id;

    @Column(name="name")
    private String name;

    @Column(name="tipo")
    private String type;

    @Column(name="age")
    private int age;

    @Column(name="categoria")
    private int category;

    @Column(name="sumary")
    private String description;

    @Column(name="reserva")
    private boolean reserva;
    @ManyToMany(mappedBy = "pets", fetch= FetchType.LAZY)
    @JsonManagedReference
    private List<Usuario> users;
}
