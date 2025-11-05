package com.andres.smartprod.Model;

import com.andres.smartprod.Enum.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @Column(name = "numero_item")
    private Long numeroItem;

    private String nombre;

    @Column(name = "cantidad_pintura")
    private Double cantidadPintura;

    private Double lavado;
    private Double pintura;
    private Double horneo;

    @Enumerated(EnumType.STRING)
    private Estado estado;

}
