/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Juan
 */
public class Utilerias {

    private static DateFormat formatoFechaDiaMesAnioHoraMinuto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static DateFormat formatoFechaDiaUnoMesAnio = new SimpleDateFormat("01/" + "MM/yyyy");
    private static DateFormat formatoFechaDiaMesAnio = new SimpleDateFormat("dd/MM/yyyy");

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
     *
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

    public static Date getDayOneOfMonth(Date fecha) {
        if (fecha == null) {
            return null;
        }
        String convertido = formatoFechaDiaUnoMesAnio.format(fecha);
        return fechaTextoDiaMesAnio(convertido);

    }

    /**
     * Recibe una fecha en estring y regresa la fecha en date
     *
     * @param fechaString
     * @return
     */
    public static Date fechaTextoDiaMesAnio(String fechaString) {

        try {
            return formatoFechaDiaMesAnio.parse(fechaString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Date getDayEndOfMonth(Date fecha) {
        if (fecha == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, 1);
        fecha = getDayOneOfMonth(calendar.getTime());
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        return calendar.getTime();
    }

    public static Date getDayOneYear(Date fecha) {

        if (fecha == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.DAY_OF_YEAR, 1);

        return calendar.getTime();

    }

    public static Date getDayEndYear(Date fecha) {

        if (fecha == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.DAY_OF_YEAR, 366); // for leap years
        
        return calendar.getTime();

    }
    
     /**
     * Combierte date a formato DD/MM/YYYY y lo retorna como String
     *
     * @param fecha
     * @return String fecha en formato DD/MM/YYYY
     */
    public static String getFechaDDMMYYYY(Date fecha) {
        if (fecha == null) {
            return "";
        }
        String convertido = formatoFechaDiaMesAnio.format(fecha);
        return convertido;

    }

}
