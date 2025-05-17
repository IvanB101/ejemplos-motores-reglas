package com.example.drools;

import java.util.HashMap;
import java.util.Map;

import com.example.base.Afiliacion;
import com.example.base.Servicio;

public class AfiliacionMapper {
    private Servicio servicio;

    public AfiliacionMapper(Servicio servicio) {
        this.servicio = servicio;
    }

    public Map<String, Object> map(Afiliacion afiliacion) {
        long id = afiliacion.getId();

        Map<String, Object> ret = new HashMap<>();

        ret.put("id", id);
        ret.put("categoria", afiliacion.getCategoria().toString());
        ret.put("subcategoria", afiliacion.getSubcategoria().toString());
        ret.put("edad", afiliacion.getEdad());
        ret.put("haberPercibido", servicio.getHaberPercibido(id));
        ret.put("CMMU", servicio.getCMMU());
        Afiliacion conyuge;
        if ((conyuge = servicio.getConyuge(id)) != null) {
            ret.put("conyuge", map(conyuge));
        }
        return ret;
    }
}
