package com.andres.smartprod.Controller;

import com.andres.smartprod.Model.Usuario;
import com.andres.smartprod.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/supervisor/usuarios")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String listUsers(Model model){
        List<Usuario> usuarios = userService.findAllUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "supervisor/usuarios";
    }

    @GetMapping("/supervisor/usuarios/nuevo")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String newUser(Model model){
        model.addAttribute("usuario", new Usuario());
        return "supervisor/nuevo_usuario";
    }

    @PostMapping("/supervisor/usuarios/nuevo")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String saveUser(Model model, Usuario usuario){
        String rawPassword = usuario.getContrasena();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        usuario.setContrasena(encodedPassword);
        userService.save(usuario);
        return "redirect:/supervisor/usuarios";
    }

    @GetMapping("/supervisor/usuarios/delete/{correo}")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String deleteUser(@PathVariable("correo") String correo){
        userService.deleteById(correo);
        return "redirect:/supervisor/usuarios";
    }


}