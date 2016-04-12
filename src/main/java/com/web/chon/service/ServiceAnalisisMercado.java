package com.web.chon.service;

import com.web.chon.dominio.BuscaVenta;
import com.web.chon.dominio.AnalisisMercado;
import com.web.chon.dominio.Pagina;
import com.web.chon.negocio.NegocioCatCliente;
import com.web.chon.util.TiempoUtil;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.web.chon.negocio.NegocioAnalisisMercado;

/**
 *
 * @author Juan
 */
@Service
public class ServiceAnalisisMercado implements IfaceAnalisisMercado {

    NegocioAnalisisMercado ejb;

    @Override
    public int saveEntradaProductoCentral(AnalisisMercado entradaMercancia) {

        getEjb();
        System.out.println(entradaMercancia.toString());
        return ejb.saveEntradaProductoCentral(entradaMercancia);
    }

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioAnalisisMercado) Utilidades.getEJBRemote("ejbAnalisisMercado", NegocioAnalisisMercado.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceAnalisisMercado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Pagina<AnalisisMercado> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public AnalisisMercado getById(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(AnalisisMercado dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(AnalisisMercado dto) {
        getEjb();
        return ejb.update(dto);
    }

    @Override
    public List<AnalisisMercado> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pagina<AnalisisMercado> findAllDominio(AnalisisMercado filters, int first, int pageSize) {

        return null;

    }

    @Override
    public ArrayList<AnalisisMercado> getEntradaMercanciaByFiltro(int numRegistros, int filtro, Date fechaInicio, String idProducto) {

        List<String> rangoFechaInicio = new ArrayList<String>();
        List<String> rangoFechaFin = new ArrayList<String>();
        List<Object[]> lstObject = null;
        System.out.println("idProducto " + idProducto);

        Date fechaFin = fechaInicio;

        getEjb();

        ArrayList<AnalisisMercado> lstEntradaMercancia = new ArrayList<AnalisisMercado>();
        ArrayList<AnalisisMercado> lstEntradaMercanciaDate = new ArrayList<AnalisisMercado>();

        int cont = 1;
        try {
            switch (filtro) {

                case 1:

                    rangoFechaInicio = TiempoUtil.getintervalWeekDDMMYYYYbyDay(fechaInicio);
                    fechaInicio = TiempoUtil.fechaTextoDiaMesAnio(rangoFechaInicio.get(0));
                    rangoFechaFin = TiempoUtil.getintervalWeekDDMMYYYYbyDay(fechaFin);
                    fechaFin = TiempoUtil.fechaTextoDiaMesAnio(rangoFechaFin.get(6));
                    fechaFin = TiempoUtil.sumarRestarDias(fechaFin, numRegistros);

                    //se crea la lista por fechas
                    Date fechaInicioTemp = fechaInicio;
                    while (fechaInicioTemp.before(fechaFin)) {

                        AnalisisMercado entradaMercacncia = new AnalisisMercado();
                        entradaMercacncia.setFecha(fechaInicioTemp);
                        entradaMercacncia.setPrecio(new BigDecimal(0));
                        entradaMercacncia.setPrecioAnterior(new BigDecimal(0));
                        entradaMercacncia.setCantidadToneladas(new BigDecimal(0));
                        entradaMercacncia.setCantidadToneladasAnterior(new BigDecimal(0));
                        entradaMercacncia.setDescripcionFiltro(TiempoUtil.nombreDia(fechaInicioTemp) + " " + TiempoUtil.getFechaDDMMYYYY(fechaInicioTemp));
                        lstEntradaMercanciaDate.add(entradaMercacncia);

                        fechaInicioTemp = TiempoUtil.sumarRestarDias(fechaInicioTemp, 1);

                    }

                    lstObject = ejb.getEntradaProductoByFiltroDay(TiempoUtil.getFechaDDMMYYYY(fechaInicio), TiempoUtil.getFechaDDMMYYYY(fechaFin), idProducto);

                    for (Object[] obj : lstObject) {
                        for (AnalisisMercado dominioDate : lstEntradaMercanciaDate) {

                            if (TiempoUtil.getFechaDDMMYYYYDate(dominioDate.getFecha()).equals(TiempoUtil.getFechaDDMMYYYYDate((Date) obj[4]))) {

                                dominioDate.setIdEntrada(obj[0] == null ? new BigDecimal(0) : new BigDecimal(obj[0].toString()));
                                dominioDate.setPrecio(obj[2] == null ? new BigDecimal(0) : new BigDecimal(obj[2].toString()));
                                dominioDate.setCantidadToneladas(obj[3] == null ? new BigDecimal(0) : new BigDecimal(obj[3].toString()));
                            }
                        }
                        cont++;

                    }

                    //Consulta para el año anterior
                    fechaInicio = TiempoUtil.sumarRestarAnios(fechaInicio, -1);
                    fechaFin = TiempoUtil.sumarRestarAnios(fechaFin, -1);
                    lstObject = ejb.getEntradaProductoByFiltroDay(TiempoUtil.getFechaDDMMYYYY(fechaInicio), TiempoUtil.getFechaDDMMYYYY(fechaFin), idProducto);

                    for (Object[] obj : lstObject) {
                        for (AnalisisMercado dominioDate : lstEntradaMercanciaDate) {

                            if (TiempoUtil.getFechaDDMMYYYYDate(TiempoUtil.sumarRestarAnios(dominioDate.getFecha(), -1)).equals(TiempoUtil.getFechaDDMMYYYYDate((Date) obj[4]))) {
                                dominioDate.setPrecioAnterior(obj[2] == null ? new BigDecimal(0) : new BigDecimal(obj[2].toString()));
                                dominioDate.setCantidadToneladasAnterior(obj[3] == null ? new BigDecimal(0) : new BigDecimal(obj[3].toString()));
                            }
                        }

                    }
                    lstEntradaMercancia = lstEntradaMercanciaDate;

                    break;
                case 2:

                    fechaInicio = TiempoUtil.sumarRestarDias(fechaInicio, -numRegistros);
                    rangoFechaInicio = TiempoUtil.getintervalWeekDDMMYYYYbyDay(fechaInicio);
                    fechaInicio = TiempoUtil.fechaTextoDiaMesAnio(rangoFechaInicio.get(0));
                    rangoFechaFin = TiempoUtil.getintervalWeekDDMMYYYYbyDay(fechaFin);
                    fechaFin = TiempoUtil.fechaTextoDiaMesAnio(rangoFechaFin.get(6));
                    fechaFin = TiempoUtil.sumarRestarDias(fechaFin, -8);

                    Date anioAnteriorInicio = fechaInicio;
                    Date anioAnteriorFin = fechaInicio;

                    while (fechaInicio.before(fechaFin)) {

                        anioAnteriorInicio = TiempoUtil.fechaTextoDiaMesAnio(rangoFechaInicio.get(0));
                        anioAnteriorFin = TiempoUtil.fechaTextoDiaMesAnio(rangoFechaInicio.get(6));

                        anioAnteriorInicio = TiempoUtil.sumarRestarAnios(anioAnteriorInicio, -1);
                        anioAnteriorFin = TiempoUtil.sumarRestarAnios(anioAnteriorFin, -1);

                        lstObject = ejb.getEntradaProductoByFiltroWeek(rangoFechaInicio.get(0), rangoFechaInicio.get(6), idProducto);
                        List<Object[]> lstObjectAnterior = ejb.getEntradaProductoByFiltroWeek(TiempoUtil.getFechaDDMMYYYY(anioAnteriorInicio), TiempoUtil.getFechaDDMMYYYY(anioAnteriorFin), idProducto);
                        fechaInicio = TiempoUtil.sumarRestarDias(fechaInicio, 8);

                        for (Object[] obj : lstObject) {
                            AnalisisMercado dominio = new AnalisisMercado();

                            dominio.setPrecio(obj[0] != null ? new BigDecimal(obj[0].toString()) : new BigDecimal(0));
                            dominio.setCantidadToneladas(obj[1] != null ? new BigDecimal(obj[1].toString()) : new BigDecimal(0));
                            dominio.setDescripcionFiltro(rangoFechaInicio.get(0) + "-" + rangoFechaInicio.get(6));

                            //Objeto con los datos de la semana del año anterior
                            for (Object[] objAnterior : lstObjectAnterior) {
                                dominio.setPrecioAnterior(objAnterior[0] != null ? new BigDecimal(objAnterior[0].toString()) : new BigDecimal(0));
                                dominio.setCantidadToneladasAnterior(objAnterior[1] != null ? new BigDecimal(objAnterior[1].toString()) : new BigDecimal(0));
                            }

                            lstEntradaMercancia.add(dominio);
                            cont++;
                        }

                        rangoFechaInicio = TiempoUtil.getintervalWeekDDMMYYYYbyDay(fechaInicio);
                    }

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

    @Override
    public AnalisisMercado getEntradaProductoByIdProducto(String idProducto) {
        List<Object[]> lstObject = null;
        AnalisisMercado dominio = new AnalisisMercado();
        getEjb();
        lstObject = ejb.getEntradaProductoByIdProducto(idProducto);

        getEjb();
        for (Object[] obj : lstObject) {

            dominio.setIdEntrada(obj[0] != null ? new BigDecimal(obj[0].toString()) : new BigDecimal(0));
            dominio.setIdProductoFk(idProducto);
            dominio.setPrecio(obj[2] != null ? new BigDecimal(obj[2].toString()) : new BigDecimal(0));
            dominio.setCantidadToneladas(obj[3] != null ? new BigDecimal(obj[3].toString()) : new BigDecimal(0));
            dominio.setFecha((Date) obj[4]);

        }

        return dominio;
    }

}
