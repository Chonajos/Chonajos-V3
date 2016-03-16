/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Juan
 */
public class Utilerias {

    private static DateFormat formatoFechaDiaMesAnioHoraMinuto = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    /**
     * Metodo para rellenar a 4 espacio
     *
     * @param value
     * @return String
     */
    public static String rellenaEspacios(int value) {
        int espacios = 4 - String.valueOf(value).length();
        String strValue = "";
        for (int i = 0; i < espacios; i++) {
            strValue += "0";
        }
        return strValue += value;
    }

    /**
     * Combierte date a formato DD/MM/YYYY HH:MM y lo retorna como String
     * @param fecha
     * @return String fecha en formato DD/MM/YYYY HH:MM
     */
    public static String getFechaDDMMYYYYHHMM(Date fecha) {
        if (fecha == null) {
            return "";
        }
        String convertido = formatoFechaDiaMesAnioHoraMinuto.format(fecha);
        return convertido;

    }

}
