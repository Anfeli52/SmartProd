package com.andres.smartprod.Controller;

import com.andres.smartprod.Service.ItemService;
import com.andres.smartprod.Service.ReportService;
import com.andres.smartprod.Service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class DashboardController {

    private final ReportService reportService;
    private final ItemService itemService;
    private final UserService userService;

    public DashboardController(ReportService reportService, ItemService itemService,  UserService userService) {
        this.reportService = reportService;
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping("/supervisor/dashboard")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String viewDashboard(Model model) {

        String horasTrabajadas = reportService.calcularHorasTrabajadas();
        Long itemsTotales = itemService.contartTotalItems();
        Long registrosTotales = reportService.contarTotalReportes();
        Long piezasDesechadas = reportService.calcularPiezasDesechadas();

        // --- CÁLCULO DINÁMICO DE USUARIOS ---
        Long totalUsuarios = userService.countTotalUsers();

        model.addAttribute("horasTrabajadas", horasTrabajadas);
        model.addAttribute("itemsTotales", itemsTotales);
        model.addAttribute("registrosTotales", registrosTotales);
        model.addAttribute("piezasDesechadas", piezasDesechadas);
        model.addAttribute("itemsMasUsados", reportService.getTopUsedItems());
        model.addAttribute("calidadTotal", reportService.getCalidadTotal());

        List<Object[]> eficienciaEtapas = Arrays.asList(
                new Object[]{"Lavado", 85},
                new Object[]{"Pintura", 92},
                new Object[]{"Horneo", 78}
        );
        model.addAttribute("eficienciaEtapas", eficienciaEtapas);

        List<Object[]> desperdicioFallo = Arrays.asList(
                new Object[]{"Mala Adherencia", 45},
                new Object[]{"Sobre Pintado", 30},
                new Object[]{"Daño de Horno", 25}
        );
        model.addAttribute("desperdicioFallo", desperdicioFallo);

        model.addAttribute("itemsEnProduccion", 45);
        model.addAttribute("tasaDesperdicio", 4.5);
        // VALOR CORREGIDO: Usando el valor dinámico de la base de datos
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("retrasoPromedio", 8.5);

        // Carga de reportes del día (asumiendo que quieres mantenerla)
        // model.addAttribute("reportesDelDia", reportService.findReportsForToday());

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
        model.addAttribute("itemsMasUsados", reportService.getTopUsedItems());
        model.addAttribute("calidadTotal", reportService.getCalidadTotal());

        return "analista/analista_dashboard";

    }
}