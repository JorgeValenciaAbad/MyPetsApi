package it.jorge.protectora.DAO;

import it.jorge.protectora.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetDAO extends JpaRepository<Pet, Integer>{

    @Query("SELECT m FROM Pet m WHERE m.adoption = false")
    List<Pet> getPets ();
    @Query("Select m.type from Pet m group by m.type")
    List<String> getPetType();
    @Query("Select m from Pet m where m.type = ?1")
    List<Pet> getPetsType(String type);
}
