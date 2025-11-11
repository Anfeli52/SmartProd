package com.andres.smartprod.Service;

import com.andres.smartprod.Model.Usuario;
import com.andres.smartprod.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. LISTAR TODOS
    public List<Usuario> findAllUsuarios(){
        return userRepository.findAll();
    }

    // 2. BUSCAR POR ID (CORREO)
    // 🚀 Corrección: Usamos findById ya que el correo es la PK definida en JpaRepository
    public Optional<Usuario> findById(String correo) {
        return userRepository.findById(correo);
    }

    // 3. GUARDAR/ACTUALIZAR
    public Usuario save(Usuario usuario) {
        return userRepository.save(usuario);
    }

    // 4. ELIMINAR POR ID (CORREO)
    public void deleteById(String correo) {
        userRepository.deleteById(correo);
    }

    // Método de Seguridad (UserDetailsService)
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByCorreo(correo).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Set<GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRol())
        );

        return new User(
                usuario.getCorreo(),
                usuario.getContrasena(),
                authorities
        );
    }

}