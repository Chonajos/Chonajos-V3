package com.web.chon.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Juan
 */
public class TiempoUtil {

    private static DateFormat formatoFechaDiaMesAnioHoraMinuto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static DateFormat formatoFechaDiaUnoMesAnio = new SimpleDateFormat("01/" + "MM/yyyy");
    private static DateFormat formatoFechaDiaMesAnio = new SimpleDateFormat("dd/MM/yyyy");
    private static DateFormat formatoFechaDiaMesAnioDosDigitos = new SimpleDateFormat("dd/MM/yy");
//    private static String[] diasEspanol = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
    private static String[] diasEspanol = {"Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"};

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
        fecha = sumarRestarAnios(fecha, 1);
        fecha = getDayOneYear(fecha);
        fecha = sumarRestarDias(fecha, -1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);

//        calendar.set(Calendar.DAY_OF_YEAR, 365); // for leap years
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

    /*Obtiene un alista de fechas el cual seria el rango 
	 * de acuerdo a la fecha indicada 
	 * */
    private static List<Date> getListDatesForDate(Date date) {

        List<Date> lstDateResult = new ArrayList<Date>(7);
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        /*Obtiene el Lunes pasado a la fecha*/
        while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            c.add(Calendar.DATE, -1);
        }

        /*Agrega Los dias correspondientes para obtener una lista de fechas*/
        for (int i = 0; i < 7; i++) {
            lstDateResult.add(c.getTime());
            c.add(Calendar.DATE, +1);
        }

        return lstDateResult;

    }

    /*Obtiene el intervalo de fechas con formato DD/MM/YYYY*/
    public static List<String> getintervalWeekDDMMYYYYbyDay(Date fecha) {
        List<String> lstWeek = new ArrayList<String>(7);

        List<Date> lstDates = getListDatesForDate(fecha);

        for (Date day : lstDates) {
            lstWeek.add(getFechaDDMMYYYY(day));
        }
        return lstWeek;
    }

    public static Date sumarRestarDias(Date fecha, int dias) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DAY_OF_YEAR, dias);

        return cal.getTime();

    }

    public static Date sumarRestarMeses(Date fecha, int meses) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.MONTH, meses);

        return cal.getTime();

    }

    public static Date sumarRestarAnios(Date fecha, int anios) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.YEAR, anios);

        return cal.getTime();
    }

    /**
     * Obtiene solo el año de una fecha
     *
     * @param fecha
     * @return
     */
    public static int getYear(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);;

        int anio = cal.get(Calendar.YEAR);

        return anio;
    }

    /**
     * Recibe date y regresa un string solo con el mes y el año Nombre del
     * mes/YYYY
     *
     * @param fecha
     * @return
     */
    public static String getMonthYear(Date fecha) {
        int year = 0;
        String month = "";

        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);

        year = cal.get(Calendar.YEAR);
        month = getNameMonth(cal.get(Calendar.MONTH));

        return month + "/" + year;
    }

    /**
     * Obtiene el nombre del mes
     *
     * @param month
     * @return
     */
    public static String getNameMonth(int month) {
        String nameMonth = "Enero";
        switch (month) {
            case 1:
                nameMonth = "Enero";
                break;
            case 2:
                nameMonth = "Febrero";
                break;
            case 3:
                nameMonth = "Marzo";
                break;
            case 4:
                nameMonth = "Abril";
                break;
            case 5:
                nameMonth = "Mayo";
                break;
            case 6:
                nameMonth = "Junio";
                break;
            case 7:
                nameMonth = "Julio";
                break;
            case 8:
                nameMonth = "Agosto";
                break;
            case 9:
                nameMonth = "Septiembre";
                break;
            case 10:
                nameMonth = "Octubre";
                break;
            case 11:
                nameMonth = "Noviembre";
                break;
            case 12:
                nameMonth = "Diciembre";
                break;
            default:
                break;

        }

        return nameMonth;

    }

    /**
     * Obtiene el nombre del dia
     *
     * @param fecha
     * @return
     */
    public static String nombreDia(Date fecha) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(fecha);
        int numeroDia = cal.get(Calendar.DAY_OF_WEEK);
        return diasEspanol[numeroDia - 1];
    }

    public static Date getFechaDDMMYYYYDate(Date fecha) {
        if (fecha == null) {
            return new Date();
        }
        String convertido = formatoFechaDiaMesAnio.format(fecha);
        return fechaTextoDiaMesAnio(convertido);

    }

    /**
     * Obtiene solo el año de una fecha a dos digitos
     *
     * @param fecha
     * @return
     */
    public static int getYearTwoDosDigitos(Date fecha) {
        
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(fecha);;
        String anioStr = Integer.toString(cal.get(Calendar.YEAR));
        anioStr = anioStr.substring(2, anioStr.length());
        int anio = Integer.parseInt(anioStr);
        
        return anio;
    }
    
    /**
     * Obtiene solo el numero de semana del año de la fecha ingresada
     *
     * @param fecha
     * @return
     */
    public static int getNumberMonthYear(Date fecha) {
        
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(fecha);;
        
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
    
      /**
     * Combierte date a formato DD/MM/YY y lo retorna como String
     *
     * @param fecha
     * @return String fecha en formato DD/MM/YY
     */
    public static String getFechaDDMMYY(Date fecha) {
        if (fecha == null) {
            return "";
        }
        String convertido = formatoFechaDiaMesAnioDosDigitos.format(fecha);
        return convertido;

    }

}
