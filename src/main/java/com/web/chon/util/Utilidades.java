/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.util;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author Juan de la Cruz
 */
public class Utilidades {

    public static Object getEJBRemote(String nameEJB, String iface) throws Exception {
        Context context;
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "http://localhost:7001");//TODO arreglar para que tome la url por defecto

        try {
            context = new InitialContext(properties);
            String lookup = nameEJB + "#" + iface;
            System.out.println("Lookup: " + lookup);
            return context.lookup(lookup);
        } catch (Exception ex) {
            throw new Exception("No se encontro  el EJB: '" + nameEJB + "'.");
        }

    }
    

}
