package com.example.afiliacion_wrapper;

import com.example.base.Afiliacion;
import com.example.base.Afiliacion.Categoria;
import com.example.base.Afiliacion.Subcategoria;
import com.example.base.Servicio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AfiliacionWrapper {
    @Getter
    private long id;
    @Getter
    private String nombre;
    @Getter
    private int edad;
    private Categoria categoria;
    private Subcategoria subcategoria;
    private Servicio servicio;

    public AfiliacionWrapper(Afiliacion afiliado, Servicio servicio) {
        this.id = afiliado.getId();
        this.nombre = afiliado.getNombre();
        this.edad = afiliado.getEdad();
        this.categoria = afiliado.getCategoria();
        this.subcategoria = afiliado.getSubcategoria();
        this.servicio = servicio;
    }

    public String getCategoria() {
        return categoria.toString();
    }

    public String getSubcategoria() {
        return subcategoria.toString();
    }

    public double getHaberPercibido() {
        return servicio.getHaberPercibido(id);
    }

    public double getCMMU() {
        return servicio.getCMMU();
    }

    public AfiliacionWrapper getConyuge() {
        return new AfiliacionWrapper(servicio.getConyuge(id), servicio);
    }
}
