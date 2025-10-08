package com.andres.smartprod.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @Column(name="correo", unique = true, nullable = false, length = 100)
    private String correo;

    @Column(name="nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name="contrasena", nullable = false, length = 100)
    private String contrasena;

    @Column(name="rol", nullable = false, length = 10)
    private String rol;

}
