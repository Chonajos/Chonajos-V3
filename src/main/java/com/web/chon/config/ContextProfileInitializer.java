package com.web.chon.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * Esta clase se ejecuta cuando se carga el contexto de Spring, y determina el
 * profile a utilizar, ya sea produccion (pro) o desarrollo (dev), dependiendo
 * del profile se determina que configuraciones se cargan en el contexto.
 *
 */
public class ContextProfileInitializer implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

    private static Logger log = LoggerFactory.getLogger(ContextProfileInitializer.class);

    /**
     * Metodo inicializa el contexto.
     */
    @Override
    public void initialize(ConfigurableWebApplicationContext ctx) {

        long startTime = System.currentTimeMillis();

        ConfigurableEnvironment environment = ctx.getEnvironment();

        ctx.refresh();

        Properties p = (Properties) ctx.getBean("configProperties");

        Constantes.VERSION = p.getProperty("project.version");
        Constantes.PROFILE = p.getProperty("project.profile");
        Constantes.PATH = p.getProperty("directory.path");

        // Decidiendo el profile a utilizar
        if (Constantes.PROFILE == null || Constantes.PROFILE.equals("")) {
            log.warn("Web App. No se configuro ningun profile! se utilizara el de desarrollo");
            System.out.println("Web App. No se configuro ningun profile! se utilizara el de desarrollo");
            Constantes.PROFILE = "dev";
        }

        log.info("Web App. Profile activo = [{}]", Constantes.PROFILE);
        System.out.println("Web App. Profile activo = [" + Constantes.PROFILE + "]");
        environment.setActiveProfiles(Constantes.PROFILE);

        ctx.refresh();

        // Desplegando informacion
        log.info("\n========================================================"
                + "\n                                                       "
                + "\n    Ejecutando WEB APP                      " + "\n    PROFILE  " + Constantes.PROFILE
                + "                                                       " + "\n    REVISION " + Constantes.VERSION
                + "\n                                                       "
                + "\n=======================================================");

        ConfigAppUtilities.despliegaConfiguracion(ctx);

        long lapsoTiempo = System.currentTimeMillis() - startTime;
        log.info("CHONAJOS APP Tiempo total de arranque {} ms.", lapsoTiempo);
        System.out.println("CHONAJOS APP. Tiempo total de arranque " + lapsoTiempo + " ms.");
    }
}
