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
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Usuario> findAllUsuarios(String currentUsername){
        return userRepository.findAll().stream()
                .filter(u -> !u.getCorreo().equalsIgnoreCase(currentUsername))
                .collect(Collectors.toList());
    }

    public Long countTotalUsers() {
        return userRepository.count();
    }

    public Optional<Usuario> findByCorreo(String correo) {
        return userRepository.findByCorreo(correo);
    }

    public Usuario save(Usuario usuario) {
        return userRepository.save(usuario);
    }

    public void deleteById(String correo) {
        userRepository.deleteById(correo);
    }
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