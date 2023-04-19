package it.jorge.protectora.DAO;

import it.jorge.protectora.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioDAO extends JpaRepository<Usuario, Integer>{

    @Query("SELECT u FROM Usuario u WHERE u.email = ?1")
    Usuario hashlogin(String email);
}
