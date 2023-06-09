package it.jorge.protectora.Service;

import it.jorge.protectora.DAO.PetitionRepository;
import it.jorge.protectora.Model.Petition;
import it.jorge.protectora.Model.Pet;
import it.jorge.protectora.Model.PetitionKey;
import it.jorge.protectora.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface PetitionService extends PetitionRepository{

}
