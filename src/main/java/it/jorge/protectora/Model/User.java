package it.jorge.protectora.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "pass")
    private String pass;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "image")
    private String image;

    @Column(name = "ld")
    private boolean delete;

    @ManyToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "Adoption",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="pet_id"))
    @JsonIgnore
    private List<Pet> pets;

    public User(String email , String pass ){
        this.email = email;
        this.pass = pass;
    }

    public User(String name , String pass, String email ){
        this.name = name;
        this.email = email;
        this.pass = pass;
    }
}