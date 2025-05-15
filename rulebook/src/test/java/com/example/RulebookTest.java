package com.example;

import org.junit.Before;
import org.junit.Test;

import com.example.base.Afiliacion;
import com.example.base.Afiliacion.Categoria;
import com.example.base.Afiliacion.Subcategoria;
import com.example.rulebook.Rulebook;
import com.example.base.Servicio;
import com.example.base.SinMotor;

public class RulebookTest {
    private Servicio servicio;
    private SinMotor reglas;
    private Rulebook rulebook;
    private Afiliacion juan;
    private Afiliacion pedro;
    private Afiliacion maria;
    private Afiliacion ana;
    private double haberAna = 500_000;

    @Before
    public void setup() {
        juan = new Afiliacion(0, "Juan", 26, Categoria.FAMILIAR, Subcategoria.DESCENDIENTE_PRIMER_GRADO);
        pedro = new Afiliacion(1, "Pedro", 22, Categoria.FAMILIAR, Subcategoria.CONYUGE);
        maria = new Afiliacion(2, "María", 24, Categoria.VOLUNTARIO_ADHERENTE, Subcategoria.BECARIO);
        ana = new Afiliacion(3, "Ana", 35, Categoria.VOLUNTARIO_ADHERENTE,
                Subcategoria.AGENTE_UNSL_CON_LICENCIA);
        servicio = new Servicio(new Servicio.CargaAfiliado[] {
                new Servicio.CargaAfiliado(pedro, maria),
                new Servicio.CargaAfiliado(ana, haberAna),
        });
        reglas = new SinMotor(servicio);
        rulebook = new Rulebook(servicio);
    }

    @Test
    public void descendientePrimerGrado() {
        assertEquals(reglas.calcularCuota(juan), rulebook.calcularCuota(juan));
    }

    @Test
    public void becario() {
        assertEquals(reglas.calcularCuota(maria), rulebook.calcularCuota(maria));
    }

    @Test
    public void conyuge() {
        assertEquals(reglas.calcularCuota(pedro), rulebook.calcularCuota(pedro));
    }

    @Test
    public void agenteUNSLConLicencia() {
        assertEquals(reglas.calcularCuota(ana), rulebook.calcularCuota(ana));
    }

    private void assertEquals(double v1, double v2) {
        final double epsilon = 0.0001;
        assert (Math.abs(v1 - v2) < epsilon);
    }
}
