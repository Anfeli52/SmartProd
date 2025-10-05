package com.andres.smartprod.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // Importante: usar @Controller para devolver vistas (HTML)
public class LoginController {

    /**
     * Mapea la ruta /login para mostrar el formulario de inicio de sesión.
     * Spring Security se encargará de interceptar el POST del formulario
     * y de manejar el éxito/fracaso.
     */
    @GetMapping("/login")
    public String login() {
        // Devuelve el nombre de la plantilla (src/main/resources/templates/login.html)
        return "login";
    }

    // Opcional: Si quieres un mensaje de bienvenida después del logout
    @RequestMapping("/login-success")
    public String afterLogin() {
        // Esta ruta no se usará directamente si el successHandler está activo,
        // pero es útil para testear.
        return "redirect:/";
    }
}
