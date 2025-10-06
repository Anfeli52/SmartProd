package com.andres.smartprod.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/supervisor/dashboard")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String supervisorDashboard() {
        return "supervisor/supervisor_dashboard";
    }

    @GetMapping("/analista/dashboard")
    @PreAuthorize("hasRole('ANALISTA')")
    public String analistaDashboard() {
        return "analista/analista_dashboard";
    }
}