package com.andres.smartprod.Repository;

import com.andres.smartprod.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByCorreo(String correo);
}
