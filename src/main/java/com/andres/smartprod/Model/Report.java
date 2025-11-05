package com.andres.smartprod.Model;

import com.andres.smartprod.Enum.Actividad;
import com.andres.smartprod.Enum.DisposicionPNC;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_operario")
    private String nombreOperario;

    @Enumerated(EnumType.STRING)
    private Actividad actividad;

    private Long cantidad;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_item", referencedColumnName = "numero_item", nullable = false)
    private Item item;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_final")
    private LocalTime horaFinal;

    @Column(name = "motivo_paro")
    private String motivoParo;

    @Column(name = "tiempo_paro")
    private Double tiempoParo;

    private Long conforme;

    @Column(name = "no_conforme")
    private Long noConforme;

    private String motivo;

    @Column(name = "disposicion_pnc")
    @Enumerated(EnumType.STRING)
    private DisposicionPNC disposicionPnc;

}
