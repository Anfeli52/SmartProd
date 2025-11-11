package com.andres.smartprod.Controller;

import com.andres.smartprod.Service.ItemService;
import com.andres.smartprod.Service.ReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final ReportService reportService;
    private final ItemService itemService;

    public DashboardController(ReportService reportService, ItemService itemService) {
        this.reportService = reportService;
        this.itemService = itemService;
    }

    @GetMapping("/supervisor/dashboard")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String supervisorDashboard() {
        return "supervisor/supervisor_dashboard";
    }

    @GetMapping("/analista/dashboard")
    @PreAuthorize("hasRole('ANALISTA')")
    public String analistaDashboard(Model model) {
        String horasTrabajadas = reportService.calcularHorasTrabajadas();
        Long itemsTotales = itemService.contartTotalItems();
        Long registrosTotales = reportService.contarTotalReportes();
        Long piezasDesechadas = reportService.calcularPiezasDesechadas();
        model.addAttribute("horasTrabajadas", horasTrabajadas);
        model.addAttribute("itemsTotales", itemsTotales);
        model.addAttribute("registrosTotales", registrosTotales);
        model.addAttribute("piezasDesechadas", piezasDesechadas);

        model.addAttribute("cantidadPorActividad", reportService.getCantidadPorActividad());
        model.addAttribute("calidadTotal", reportService.getCalidadTotal());

        return "analista/analista_dashboard";

    }
}