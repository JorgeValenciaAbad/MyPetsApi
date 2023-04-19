package it.jorge.protectora.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="Pets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int pet_id;

    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    @Column(name="age")
    private int age;

    @Column(name="breed")
    private String breed;

    @Column(name="location")
    private String location;

    @Column(name="summary")
    private String summary;

    @Column(name="adoption")
    private boolean adoption;

    @ManyToMany(mappedBy = "pets", fetch= FetchType.LAZY)
    @JsonManagedReference
    private List<User> users;
}
