package com.andres.smartprod.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name="numero_item")
    private Long numeroItem;

    private String nombre;

    @Column(name="cantidad_pintura")
    private Double cantidadPintura;

    private Double lavado;
    private Double pintura;
    private Double horneo;
    private String estado;

}
