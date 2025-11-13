package com.andres.smartprod.Service;

import com.andres.smartprod.Model.Report;
import com.andres.smartprod.Repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    public String calcularHorasTrabajadas(){
        List<Report> todosLosReportes = reportRepository.findAll();
        long totalSegundos = 0;

        for (Report reporte : todosLosReportes) {
            LocalTime inicio = reporte.getHoraInicio();
            LocalTime fin = reporte.getHoraFinal();

            if(inicio != null && fin != null){
                Duration duracion = Duration.between(inicio, fin);
                if(duracion.isNegative()){
                    duracion = duracion.plusDays(1);
                }
                totalSegundos += duracion.getSeconds();
            }
        }

        double totalHoras = totalSegundos/3600;
        return String.format("%.2f", totalHoras);

    }

    public Long contarTotalReportes(){
        return reportRepository.count();
    }

    public Long calcularPiezasDesechadas() {
        Long totalDesechado = reportRepository.sumCantidadByDisposicionDesechar();
        return (totalDesechado != null) ? totalDesechado : 0L;
    }

    public List<Object[]> getTopUsedItems() {
        return reportRepository.sumTop5ItemsByQuantity();
    }

    public List<Long> getCalidadTotal() {
        List<Object[]> resultados = reportRepository.sumConformeAndNoConforme();
        if (resultados == null || resultados.isEmpty()) {
            return List.of(0L, 0L);
        }

        Object[] fila = resultados.get(0);
        Long conforme = fila[0] != null ? ((Number) fila[0]).longValue() : 0L;
        Long noConforme = fila[1] != null ? ((Number) fila[1]).longValue() : 0L;

        return List.of(conforme, noConforme);
    }

    public Report saveReport(Report report){
        return reportRepository.save(report);
    }

    public List<Report> findAllReportes(){
        return reportRepository.findAll();
    }

    public Optional<Report> findById(Long id){
        return reportRepository.findById(id);
    }

    public void deleteById(Long id){
        reportRepository.deleteById(id);
    }
}