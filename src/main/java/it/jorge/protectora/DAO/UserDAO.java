package it.jorge.protectora.DAO;

import it.jorge.protectora.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<User, Integer>{

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User hashLogin(String email);
}
