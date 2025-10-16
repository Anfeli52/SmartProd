package com.andres.smartprod.Model;

import com.andres.smartprod.Enum.Rol;
import jakarta.persistence.*;
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

    @Enumerated (EnumType.STRING)
    @Column(name="rol", nullable = false)
    private Rol rol;

}
