package com.andres.smartprod.Controller;

import com.andres.smartprod.Model.Report;
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

    @Autowired
    public ReportViewController(ReportService reportService){
        this.reportService = reportService;
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
        return "analista/nuevo_reporte";
    }

    @PostMapping("/analista/reportes/nuevo")
    @PreAuthorize("hasRole('ANALISTA')")
    public String saveReporte(@ModelAttribute("reporte") Report reporte) {
        reportService.saveReport(reporte);
        return "redirect:/analista/reportes";
    }

    @GetMapping("/analista/reportes/edit/{id}")
    @PreAuthorize("hasRole('ANALISTA')")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Report reporte = reportService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de Reporte inválido:" + id));
        model.addAttribute("reporte", reporte);
        return "supervisor/nuevo_reporte";
    }

    @GetMapping("/analista/reportes/delete/{id}")
    @PreAuthorize("hasRole('ANALISTA')")
    public String deleteReporte(@PathVariable("id") Long id) {
        reportService.deleteById(id);
        return "redirect:/analista/reportes";
    }
}
