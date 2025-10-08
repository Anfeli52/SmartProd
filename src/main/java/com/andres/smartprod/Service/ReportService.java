package com.andres.smartprod.Service;

import com.andres.smartprod.Model.Report;
import com.andres.smartprod.Repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
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
