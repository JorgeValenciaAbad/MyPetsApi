package it.jorge.protectora.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "lost")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LostAnimal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "image")
    private String image;

    public LostAnimal (String summary, String image){
        this.summary = summary;
        this.image = image;
    }

}

