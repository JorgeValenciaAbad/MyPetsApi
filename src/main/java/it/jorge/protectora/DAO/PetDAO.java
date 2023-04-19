package it.jorge.protectora.DAO;

import it.jorge.protectora.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetDAO extends JpaRepository<Pet, Integer>{

    @Query("SELECT m FROM Pet m WHERE m.adoption = false")
    public List<Pet> getPets ();
    @Query("Select m.type from Pet m group by m.type")
    public List<String> getPetType();


}
