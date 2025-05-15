package com.example.base;

import java.util.HashMap;
import java.util.Map;

public class Servicio {
    static public class CargaAfiliado {
        Afiliacion afiliado;
        Double haberesPercibido;
        Afiliacion conyuge;

        public CargaAfiliado(Afiliacion afiliado, Double haberesPercibido, Afiliacion conyuge) {
            this.afiliado = afiliado;
            this.haberesPercibido = haberesPercibido;
            this.conyuge = conyuge;
        }

        public CargaAfiliado(Afiliacion afiliado, Double haberesPercibido) {
            this.afiliado = afiliado;
            this.haberesPercibido = haberesPercibido;
        }

        public CargaAfiliado(Afiliacion afiliado, Afiliacion conyuge) {
            this.afiliado = afiliado;
            this.conyuge = conyuge;
        }
    }

    private Map<Long, Afiliacion> conyuges;
    private Map<Long, Double> haberesPercibidos;

    static private final double CMMU = 20000;

    public Servicio(CargaAfiliado cargas[]) {
        conyuges = new HashMap<>();
        haberesPercibidos = new HashMap<>();

        for (CargaAfiliado carga : cargas) {
            Afiliacion afiliado = carga.afiliado;
            if (carga.afiliado == null) {
                throw new RuntimeException("No se puede cargar un afiliado sin valor");
            }
            if (carga.haberesPercibido != null) {
                haberesPercibidos.put(afiliado.getId(), carga.haberesPercibido);
            }
            if (carga.conyuge != null) {
                conyuges.put(afiliado.getId(), carga.conyuge);
            }
        }
    }

    public double getHaberPercibido(long afiliacionId) {
        return haberesPercibidos.get(afiliacionId);
    }

    public Afiliacion getConyuge(long afiliacionId) {
        return conyuges.get(afiliacionId);
    }

    public double getCMMU() {
        return CMMU;
    }
}
