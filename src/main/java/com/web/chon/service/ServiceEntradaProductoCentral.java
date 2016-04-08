package com.web.chon.service;

import com.web.chon.dominio.BuscaVenta;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.Pagina;
import com.web.chon.negocio.NegocioCatCliente;
import com.web.chon.negocio.NegocioEntradaProductoCentral;
import com.web.chon.util.Utilerias;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan
 */
@Service
public class ServiceEntradaProductoCentral implements IfaceEntradaProductoCentral {

    NegocioEntradaProductoCentral ejb;

    @Override
    public int saveEntradaProductoCentral(EntradaMercancia entradaMercancia) {

        getEjb();
        return ejb.saveEntradaProductoCentral(entradaMercancia);
    }

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioEntradaProductoCentral) Utilidades.getEJBRemote("ejbEntradaProductoCentral", NegocioEntradaProductoCentral.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceEntradaProductoCentral.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Pagina<EntradaMercancia> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public EntradaMercancia getById(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(EntradaMercancia dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(EntradaMercancia dto) {
         getEjb();
        return ejb.update(dto);
    }

    @Override
    public List<EntradaMercancia> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pagina<EntradaMercancia> findAllDominio(EntradaMercancia filters, int first, int pageSize) {

        return null;

    }

    @Override
    public ArrayList<EntradaMercancia> getEntradaMercanciaByFiltro(int filtro, Date fechaInicio, Date fechaFin) {

        List<String> rangoFechaInicio = new ArrayList<String>();
        List<String> rangoFechaFin = new ArrayList<String>();
        List<Object[]> lstObject = null;

        Date fechaDiaUno;
        Date fechaDiaFinal;
        Date fechaDiaFinalTemp;

        getEjb();

        ArrayList<EntradaMercancia> lstEntradaMercancia = new ArrayList<EntradaMercancia>();

        int cont = 1;
        try {
            switch (filtro) {

                case 1:

                    lstObject = ejb.getEntradaProductoByFiltroDay(Utilerias.getFechaDDMMYYYY(fechaInicio), Utilerias.getFechaDDMMYYYY(fechaFin));

                    for (Object[] obj : lstObject) {
                        EntradaMercancia dominio = new EntradaMercancia();

                        dominio.setIdEntrada(obj[0] == null ? new BigDecimal(0) : new BigDecimal(obj[0].toString()));
                        dominio.setPrecio(obj[1] == null ? new BigDecimal(0) : new BigDecimal(obj[1].toString()));
                        dominio.setCantidadToneladas(obj[2] == null ? new BigDecimal(0) : new BigDecimal(obj[2].toString()));
                        dominio.setFecha((Date) obj[3]);
                        dominio.setDescripcionFiltro(Utilerias.getFechaDDMMYYYY((Date) obj[3]));

                        cont++;

                        lstEntradaMercancia.add(dominio);

                    }

                    break;
                case 2:
 
                    rangoFechaInicio = Utilerias.getintervalWeekDDMMYYYYbyDay(fechaInicio);
                    fechaInicio = Utilerias.fechaTextoDiaMesAnio(rangoFechaInicio.get(0));
                    rangoFechaFin = Utilerias.getintervalWeekDDMMYYYYbyDay(fechaFin);

                    while (fechaInicio.before(Utilerias.fechaTextoDiaMesAnio(rangoFechaFin.get(6)))) {

                        lstObject = ejb.getEntradaProductoByFiltroWeek(rangoFechaInicio.get(0), rangoFechaInicio.get(6));
                        fechaInicio = Utilerias.sumarRestarDias(fechaInicio, 7);

                        rangoFechaInicio = Utilerias.getintervalWeekDDMMYYYYbyDay(fechaInicio);

                        for (Object[] obj : lstObject) {
                            EntradaMercancia dominio = new EntradaMercancia();

                            dominio.setPrecio(obj[0] != null ? new BigDecimal(obj[0].toString()) : new BigDecimal(0));
                            dominio.setCantidadToneladas(obj[1] != null ? new BigDecimal(obj[1].toString()) : new BigDecimal(0));
                            dominio.setDescripcionFiltro("Semana " + cont);

                            lstEntradaMercancia.add(dominio);
                            cont++;
                        }

                    }

                    break;

                case 3:

                    fechaDiaUno = Utilerias.getDayOneOfMonth(fechaInicio);
                    fechaDiaFinal = Utilerias.getDayEndOfMonth(fechaFin);
                    fechaDiaFinalTemp = Utilerias.getDayEndOfMonth(fechaInicio);

                    while (fechaDiaUno.before(fechaDiaFinal)) {

                        lstObject = ejb.getEntradaProductoByFiltroMonth(Utilerias.getFechaDDMMYYYY(fechaDiaUno), Utilerias.getFechaDDMMYYYY(fechaDiaFinalTemp));
                        fechaDiaUno = Utilerias.sumarRestarMeses(fechaDiaUno, 1);
                        fechaDiaFinalTemp = Utilerias.getDayEndOfMonth(fechaDiaUno);

                        for (Object[] obj : lstObject) {
                            EntradaMercancia dominio = new EntradaMercancia();

                            dominio.setPrecio(obj[0] != null ? new BigDecimal(obj[0].toString()) : new BigDecimal(0));
                            dominio.setCantidadToneladas(obj[1] != null ? new BigDecimal(obj[1].toString()) : new BigDecimal(0));
                            dominio.setDescripcionFiltro(Utilerias.getMonthYear(fechaDiaUno));

                            lstEntradaMercancia.add(dominio);

                        }
                    }
                    cont++;
                    break;

                case 4:
                    fechaDiaUno = Utilerias.getDayOneYear(fechaInicio);
                    fechaDiaFinal = Utilerias.getDayEndYear(fechaFin);
                    fechaDiaFinalTemp = Utilerias.getDayEndYear(fechaInicio);
                    while (fechaDiaUno.before(fechaDiaFinal)) {
                        
                        lstObject = ejb.getEntradaProductoByFiltroYear(Utilerias.getFechaDDMMYYYY(fechaDiaUno), Utilerias.getFechaDDMMYYYY(fechaDiaFinalTemp));

                        for (Object[] obj : lstObject) {
                            EntradaMercancia dominio = new EntradaMercancia();
                            dominio.setPrecio(obj[0] != null ? new BigDecimal(obj[0].toString()) : new BigDecimal(0));
                            dominio.setCantidadToneladas(obj[1] != null ? new BigDecimal(obj[1].toString()) : new BigDecimal(0));
                            dominio.setDescripcionFiltro(Integer.toString(Utilerias.getYear(fechaDiaUno)));
                            lstEntradaMercancia.add(dominio);

                        }

                        fechaDiaUno = Utilerias.sumarRestarAnios(fechaDiaUno, 1);
                        fechaDiaFinalTemp = Utilerias.getDayEndYear(fechaDiaUno);
                    }
                    cont++;

                    break;

                default:

                    System.out.println("default " + filtro);
                    break;
            }
        } catch (Exception e) {
            System.out.println("error servide " + e.getMessage());
            e.getStackTrace();
        }

        return lstEntradaMercancia;
    }

}
