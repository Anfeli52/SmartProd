package com.andres.smartprod.Controller;

import com.andres.smartprod.Model.Report;
import com.andres.smartprod.Service.ItemService;
import com.andres.smartprod.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReportViewController {

    private final ReportService reportService;
    private final ItemService itemService;

    @Autowired
    public ReportViewController(ReportService reportService, ItemService itemService) {
        this.reportService = reportService;
        this.itemService = itemService;
    }

    @GetMapping("/analista/reportes")
    @PreAuthorize("hasRole('ANALISTA')")
    public String verReportes(Model model){
        model.addAttribute("reportes", reportService.findAllReportes());
        return "analista/reportes";
    }

    @GetMapping("/analista/reportes/nuevo")
    @PreAuthorize("hasRole('ANALISTA')")
    public String mostrarFormularioNuevoReporte(Model model){
        model.addAttribute("reporte", new Report());
        model.addAttribute("items", itemService.findAllItems());
        return "analista/nuevo_reporte";
    }

    @PostMapping("/analista/reportes/nuevo")
    @PreAuthorize("hasRole('ANALISTA')")
    public String saveReporte(@ModelAttribute("reporte") Report reporte, Model model) {

        model.addAttribute("items", itemService.findAllItems());

        // Se usan Long (clase envolvente) para poder chequear por nulls (espacios vacíos del formulario)
        Long totalPiezas = reporte.getCantidad();
        Long piezasConformes = reporte.getConforme();
        Long piezasNoConformes = reporte.getNoConforme();
        String disposicion = String.valueOf(reporte.getDisposicionPnc());
        String motivo = reporte.getMotivo();
        final String NO_APLICA = "NO_APLICA";

        // ********************************************
        // 1. VALIDACIÓN: Campos Numéricos Obligatorios (Nulidad)
        // ********************************************
        if (totalPiezas == null || piezasConformes == null || piezasNoConformes == null) {
            model.addAttribute("reporte", reporte);
            model.addAttribute("error", "Error: Los campos 'Cantidad (Total de Piezas)', 'Piezas Conformes' y 'Piezas No Conformes' no pueden estar vacíos.");
            return "analista/nuevo_reporte";
        }

        // ********************************************
        // 2. VALIDACIÓN: Suma de Piezas
        // Usamos los Long directamente. Java realiza el unboxing automático a 'long' para la operación.
        // ********************************************
        if (piezasConformes + piezasNoConformes != totalPiezas) {
            model.addAttribute("reporte", reporte);
            model.addAttribute("error", "Error de conteo: La suma de Piezas Conformes (" + piezasConformes +
                    ") y No Conformes (" + piezasNoConformes +
                    ") debe ser igual a la Cantidad total (" + totalPiezas + ").");
            return "analista/nuevo_reporte";
        }

        // ********************************************
        // 3. VALIDACIÓN: Disposición PNC basada en Piezas No Conformes
        // ********************************************
        if (piezasNoConformes > 0) {
            if (NO_APLICA.equals(disposicion)) {
                model.addAttribute("reporte", reporte);
                model.addAttribute("error", "Existen Piezas No Conformes. La Disposición PNC no puede ser 'NO APLICA'.");
                return "analista/nuevo_reporte";
            }
        } else {
            if (!NO_APLICA.equals(disposicion)) {
                model.addAttribute("reporte", reporte);
                model.addAttribute("error", "Dado que no hay Piezas No Conformes (0), la Disposición PNC debe ser 'NO APLICA'.");
                return "analista/nuevo_reporte";
            }
        }

        // ********************************************
        // 4. VALIDACIÓN: Motivo si hay Piezas No Conformes
        // ********************************************
        if (piezasNoConformes > 0) {
            if (motivo == null || motivo.trim().isEmpty()) {
                model.addAttribute("reporte", reporte);
                model.addAttribute("error", "Debe especificar el 'Motivo' del fallo si hay Piezas No Conformes.");
                return "analista/nuevo_reporte";
            }
        }

        reportService.saveReport(reporte);
        return "redirect:/analista/reportes";
    }

    @GetMapping("/analista/reportes/edit/{id}")
    @PreAuthorize("hasRole('ANALISTA')")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Report reporte = reportService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de Reporte inválido:" + id));
        model.addAttribute("reporte", reporte);
        model.addAttribute("items", itemService.findAllItems());
        return "analista/editar_reporte";
    }

    @GetMapping("/analista/reportes/delete/{id}")
    @PreAuthorize("hasRole('ANALISTA')")
    public String deleteReporte(@PathVariable("id") Long id) {
        reportService.deleteById(id);
        return "redirect:/analista/reportes";
    }

    @GetMapping("/supervisor/reportes")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public String verReportesSupervisor(Model model){
        model.addAttribute("reportes", reportService.findAllReportes());
        return "supervisor/reportes";
    }

}