package com.andres.smartprod.Controller;

import com.andres.smartprod.Model.Usuario;
import com.andres.smartprod.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // Constante para el dominio requerido
    private static final String REQUIRED_DOMAIN = "@iltec.com";

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$";
    // El REGEX ahora puede ser más simple, ya que el dominio lo validamos por separado
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@.*";

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // --- MÉTODOS DE VISTA (GET) ---
    @GetMapping("/supervisor/usuarios")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String listUsers(Model model, Authentication authentication){
        String currentUsername = authentication.getName();
        List<Usuario> usuarios = userService.findAllUsuarios(currentUsername);
        model.addAttribute("usuarios", usuarios);
        return "supervisor/usuarios";
    }

    @GetMapping("/supervisor/usuarios/nuevo")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String newUser(Model model){
        model.addAttribute("usuario", new Usuario());
        return "supervisor/nuevo_usuario";
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

    // --- MÉTODOS DE ACCIÓN (POST) ---
    @PostMapping("/supervisor/usuarios/nuevo")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String saveUser(Model model, Usuario usuario){

        // 1. Validación de Nombre
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "El campo Nombre no puede estar vacío.");
            return "supervisor/nuevo_usuario";
        }

        // 2. Validación de Rol
        String rol = String.valueOf(usuario.getRol());
        if (rol == null || !Arrays.asList("ANALISTA", "SUPERVISOR").contains(rol)) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "El rol seleccionado no es válido.");
            return "supervisor/nuevo_usuario";
        }

        // 3. Validación de Formato de Correo (solo estructura básica)
        if (usuario.getCorreo() == null || !usuario.getCorreo().matches(EMAIL_REGEX)) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "El formato del correo electrónico es inválido.");
            usuario.setContrasena(null);
            return "supervisor/nuevo_usuario";
        }

        // 4. VALIDACIÓN DE DOMINIO REQUERIDO (NUEVA REGLA)
        if (!usuario.getCorreo().toLowerCase().endsWith(REQUIRED_DOMAIN)) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "El correo electrónico debe pertenecer al dominio " + REQUIRED_DOMAIN + ".");
            usuario.setContrasena(null);
            return "supervisor/nuevo_usuario";
        }

        // 5. Validación de Contraseña
        String rawPassword = usuario.getContrasena();

        if (rawPassword == null || rawPassword.isEmpty()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "La contraseña no puede estar vacía.");
            return "supervisor/nuevo_usuario";
        }

        if (!rawPassword.matches(PASSWORD_REGEX)) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "La contraseña debe tener mínimo 8 caracteres, incluyendo al menos una mayúscula y un número.");
            usuario.setContrasena(null);
            return "supervisor/nuevo_usuario";
        }

        // 6. Validación de Unicidad
        if (userService.findByCorreo(usuario.getCorreo()).isPresent()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "El correo electrónico ya se encuentra registrado. Por favor, use uno diferente.");
            usuario.setContrasena(null);
            return "supervisor/nuevo_usuario";
        }

        // 7. Guardado
        String encodedPassword = passwordEncoder.encode(rawPassword);
        usuario.setContrasena(encodedPassword);
        userService.save(usuario);

        return "redirect:/supervisor/usuarios";
    }

    @PostMapping("/supervisor/usuarios/update")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String updateUser(@ModelAttribute("usuario") Usuario usuarioForm, Model model) {

        // 1. Validación de Nombre
        if (usuarioForm.getNombre() == null || usuarioForm.getNombre().trim().isEmpty()) {
            model.addAttribute("usuario", usuarioForm);
            model.addAttribute("error", "El campo Nombre no puede estar vacío.");
            return "supervisor/editar_usuario";
        }

        // 2. Validación de Rol
        String rol = String.valueOf(usuarioForm.getRol());
        if (rol == null || !Arrays.asList("ANALISTA", "SUPERVISOR").contains(rol)) {
            model.addAttribute("usuario", usuarioForm);
            model.addAttribute("error", "El rol seleccionado no es válido.");
            return "supervisor/editar_usuario";
        }

        // 3. Validación de Correo (Formato básico)
        if (usuarioForm.getCorreo() == null || !usuarioForm.getCorreo().matches(EMAIL_REGEX)) {
            model.addAttribute("usuario", usuarioForm);
            model.addAttribute("error", "El formato del correo electrónico es inválido.");
            return "supervisor/editar_usuario";
        }

        // 4. VALIDACIÓN DE DOMINIO REQUERIDO
        if (!usuarioForm.getCorreo().toLowerCase().endsWith(REQUIRED_DOMAIN)) {
            model.addAttribute("usuario", usuarioForm);
            model.addAttribute("error", "El correo electrónico debe pertenecer al dominio " + REQUIRED_DOMAIN + ".");
            return "supervisor/editar_usuario";
        }

        Usuario existingUser = userService.findByCorreo(usuarioForm.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado para actualización"));

        // 5. Validación y codificación de Contraseña (solo si se proporciona una nueva)
        String newPassword = usuarioForm.getContrasena();

        if (newPassword != null && !newPassword.isEmpty()) {
            if (!newPassword.matches(PASSWORD_REGEX)) {
                model.addAttribute("usuario", usuarioForm);
                model.addAttribute("error", "La contraseña debe tener mínimo 8 caracteres, incluyendo al menos una mayúscula y un número.");
                usuarioForm.setContrasena(null);
                return "supervisor/editar_usuario";
            }

            String encodedPassword = passwordEncoder.encode(newPassword);
            existingUser.setContrasena(encodedPassword);
        }

        existingUser.setNombre(usuarioForm.getNombre());
        existingUser.setRol(usuarioForm.getRol());

        userService.save(existingUser);

        return "redirect:/supervisor/usuarios";
    }

}