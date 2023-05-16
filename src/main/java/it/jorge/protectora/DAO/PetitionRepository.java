package it.jorge.protectora.DAO;

import it.jorge.protectora.Model.Pet;
import it.jorge.protectora.Model.Petition;
import it.jorge.protectora.Model.PetitionKey;
import it.jorge.protectora.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetitionRepository extends JpaRepository<Petition, PetitionKey> {}
