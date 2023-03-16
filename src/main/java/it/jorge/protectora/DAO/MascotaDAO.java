package it.jorge.protectora.DAO;

import it.jorge.protectora.Model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MascotaDAO extends JpaRepository<Mascota, Integer>{

    @Query("SELECT m FROM Mascota m WHERE m.reserva = false")
    public List<Mascota> getPets ();
    @Query("Select m.type from Mascota m group by m.type")
    public List<String> getPetType();


}
