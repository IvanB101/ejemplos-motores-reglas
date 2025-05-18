package com.example.openl_tablets;

import java.lang.reflect.Method;

import org.openl.rules.project.instantiation.ProjectEngineFactory;
import org.openl.rules.project.instantiation.SimpleProjectEngineFactory;

import com.example.afiliacion_wrapper.AfiliacionWrapper;
import com.example.base.Afiliacion;
import com.example.base.Servicio;

public class OpenLTablets {
    private Method rule;
    private Object instance;
    private Servicio servicio;

    public OpenLTablets(Servicio servicio) {
        try {
            this.servicio = servicio;
            ProjectEngineFactory<Object> projectEngineFactory = new SimpleProjectEngineFactory.SimpleProjectEngineFactoryBuilder<Object>()
                    .setProject("src/main/resources/reglas").build();
            Class<?> clazz = projectEngineFactory.getInterfaceClass();

            this.rule = clazz.getMethod("CalcularCuota", AfiliacionWrapper.class);
            this.instance = projectEngineFactory.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error en inicializacion: " + e.getMessage());
        }
    }

    public Double calcularCuota(Afiliacion afiliacion) {
        try {
            Object res = rule.invoke(this.instance, new AfiliacionWrapper(afiliacion, servicio));
            return (Double) res;
        } catch (Exception e) {
            throw new RuntimeException("Error en cálculo de cuota: " + e.getMessage());
        }
    }
}
