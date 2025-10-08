package com.andres.smartprod.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String actividad;

    private LocalDate fecha;

    @Column(name="numero_item")
    private Long numeroItem;

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
    private String disposicionPnc;

}
