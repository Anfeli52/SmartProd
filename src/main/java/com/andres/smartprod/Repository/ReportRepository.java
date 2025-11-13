package com.andres.smartprod.Repository;

import com.andres.smartprod.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository <Report, Long>{
    @Query("SELECT SUM(r.noConforme) FROM Report r WHERE r.disposicionPnc = 'DESECHAR'")
    Long sumCantidadByDisposicionDesechar();

    @Query("SELECT r.actividad, SUM(r.conforme) FROM Report r GROUP BY r.actividad")
    List<Object[]> sumCantidadByActividad();

    @Query("SELECT SUM(r.conforme), SUM(r.noConforme) FROM Report r")
    List<Object[]> sumConformeAndNoConforme();

    @Query("SELECT r.item.numeroItem, SUM(r.cantidad) FROM Report r GROUP BY r.item.numeroItem ORDER BY SUM(r.cantidad) DESC LIMIT 5")
    List<Object[]> sumTop5ItemsByQuantity();

}
