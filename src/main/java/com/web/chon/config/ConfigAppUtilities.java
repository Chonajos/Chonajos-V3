
package com.web.chon.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Pattern;
import org.springframework.context.ApplicationContext;

/**
 * Clase de las utilidades para imprimir configuracion
 * 
 */
public class ConfigAppUtilities {

    private static Logger log = LoggerFactory.getLogger(ConfigAppUtilities.class);

     /**
     * Lista de propiedades.
     */
    public static final String[] lstProperties = { "configProperties"};

     /**
     * Despliega configuraciÃ³n de la aplicacion
     * @param context Contexto spring
     */
    public static void despliegaConfiguracion(ApplicationContext context) {
        StringBuilder body = new StringBuilder();
    
        for (String property : lstProperties) {
            String propiedadesString = getPropertiesString(context.getBean(property, Properties.class), property);
            body.append(propiedadesString);
        }

        despliegaLayoutSalida(body.toString());

    }

    private static void despliegaLayoutSalida(String body) {

        log.info("\n========================================================="
                + "\n                                                         " + "\n    Configuracion " + "\n    "
                + body + "\n                                                         "
                + "\n=========================================================");
    }

    @SuppressWarnings("rawtypes")
    private static String getPropertiesString(Properties props, String property) {
        String regex = ".*password|PASSWORD.*";
        StringBuilder builder = new StringBuilder();

        Iterator keys = props.keySet().iterator();
        builder.append("\n=========================================================");
        builder.append("\n\n    Propiedades " + property + "\n");

        while (keys.hasNext()) {

            String key = (String) keys.next();
            if (Pattern.matches(regex, key)) {
                builder.append("\n    " + key + "= ***********");
            }
            else {
                builder.append("\n    " + key + "=" + props.getProperty(key));
            }
        }

        return builder.toString();
    }
}
