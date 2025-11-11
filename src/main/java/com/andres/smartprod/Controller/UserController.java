package com.andres.smartprod.Controller;

import com.andres.smartprod.Model.Usuario;
import com.andres.smartprod.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
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
        if (userService.findByCorreo(usuario.getCorreo()).isPresent()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "El correo electrónico ya se encuentra registrado. Por favor, use uno diferente.");
            return "supervisor/nuevo_usuario";
        }

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

    @GetMapping("/supervisor/usuarios/edit/{correo}")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String showEditForm(@PathVariable("correo") String correo, Model model){
        Usuario user = userService.findByCorreo(correo).orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
        model.addAttribute("usuario", user);
        return "supervisor/editar_usuario";
    }

    @PostMapping("/supervisor/usuarios/update")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String updateUser(@ModelAttribute("usuario") Usuario usuarioForm) {
        Usuario existingUser = userService.findByCorreo(usuarioForm.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado para actualización"));
        String newPassword = usuarioForm.getContrasena();
        existingUser.setNombre(usuarioForm.getNombre());
        existingUser.setRol(usuarioForm.getRol());

        if (newPassword != null && !newPassword.isEmpty()) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setContrasena(encodedPassword);
        } else {
            newPassword = "[No actualizada]";
        }

        userService.save(existingUser);

        System.out.println("Usuario actualizado Correo: " + existingUser.getCorreo());
        System.out.println("Usuario actualizado Nombre: " + existingUser.getNombre());
        System.out.println("Usuario actualizado contraseña Formulario: " + newPassword);
        System.out.println("Usuario actualizado contraseña hasheada: " + existingUser.getContrasena());

        return "redirect:/supervisor/usuarios";
    }

}