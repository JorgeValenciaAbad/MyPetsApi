package it.jorge.protectora.DAO;

import it.jorge.protectora.Model.Adopcion;
import it.jorge.protectora.Model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdoptionDAO extends JpaRepository<Adopcion, Integer> {


}
