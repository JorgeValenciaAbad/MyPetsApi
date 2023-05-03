package it.jorge.protectora.DAO;


import it.jorge.protectora.Model.LostAnimal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostDAO extends JpaRepository <LostAnimal, Integer> {

}
