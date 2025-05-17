package com.example.drools;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

import com.example.base.Afiliacion;
import com.example.base.Servicio;

public class Drools {
    private DMNModel reglas;
    private DMNRuntime runtime;
    private AfiliacionMapper mapper;

    public Drools(Servicio servicio) {
        mapper = new AfiliacionMapper(servicio);
        String drl;
        try {
            drl = new String(Files.readAllBytes(Paths.get("./src/main/resources/Calculo.dmn")));
        } catch (Exception e) {
            throw new RuntimeException("Error abriendo archivo con reglas: " + e.getMessage());
        }

        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();

        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.write("src/main/resources/Calculo.dmn", drl);

        KieBuilder kb = ks.newKieBuilder(kfs);
        kb.buildAll();
        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Error building rules: " + kb.getResults().toString());
        }

        KieContainer kieContainer = ks.newKieContainer(kr.getDefaultReleaseId());
        // KieContainer kieContainer = ks.getKieClasspathContainer();
        runtime = KieRuntimeFactory.of(kieContainer.getKieBase()).get(DMNRuntime.class);

        final String namespace = "https://github.com/kiegroup/drools/kie-dmn/_A4BCA8B8-CF08-433F-93B2-A2598F19ECFF";
        final String modelName = "Calculo";
        reglas = runtime.getModel(namespace, modelName);
    }

    public Double calcularCuota(Afiliacion afiliacion) {
        DMNContext dmnContext = runtime.newContext();
        dmnContext.set("Afiliado", mapper.map(afiliacion));

        DMNResult dmnResult = runtime.evaluateAll(this.reglas, dmnContext);
        if (dmnResult.hasErrors()) {
            throw new RuntimeException("Error calculando cuota" + dmnResult.getMessages());
        }
        DMNDecisionResult decisionResult = dmnResult.getDecisionResultByName("Monto Cuota");
        if (decisionResult == null || decisionResult.getResult() == null) {
            throw new RuntimeException("Error en el calculo cuota");
        }

        return ((BigDecimal) decisionResult.getResult()).doubleValue();
    }
}
