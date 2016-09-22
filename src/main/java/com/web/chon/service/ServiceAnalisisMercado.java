package com.web.chon.service;

import com.web.chon.dominio.AnalisisMercado;
import com.web.chon.dominio.Pagina;
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
    public int delete(BigDecimal id) {
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
                        entradaMercacncia.setDescripcionFiltro(TiempoUtil.nombreDia(fechaInicioTemp) + " " + TiempoUtil.getFechaDDMMYY(fechaInicioTemp));
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
//                            dominio.setDescripcionFiltro(rangoFechaInicio.get(0) + "-" + rangoFechaInicio.get(6));
                            dominio.setDescripcionFiltro(TiempoUtil.getFechaDDMMYY(fechaInicio));
                            dominio.setRemantePorSemana(obj[2] != null ? new BigDecimal(obj[2].toString()) : new BigDecimal(0));

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
                case 3:
                    List<Object[]> lstObjectMonth = null;
                    List<Object[]> lstObjectMonthAnterior = null;
                    List<Object[]> lstObjectRemanenteMonth = null;
                    List<Object[]> lstObjectRemanenteMonthAnterior = null;
                    
                    List<String> fechaActual = new ArrayList<String>();
                    List<String> fechaAnterior = new ArrayList<String>();
                    List<String> fechaActualRemanente = new ArrayList<String>();
                    List<String> fechaAnteriorRemanente = new ArrayList<String>();
                    
                    Date fechaInicioAnterior = fechaInicio;
                    fechaInicioAnterior = TiempoUtil.sumarRestarAnios(fechaFin, -1);
                    BigDecimal diasMes = null;
                    
                    for (int i = 0; i <= 11; i++) {
                        
                        fechaActual = TiempoUtil.obtenerFecgaInicioMesFinMesPorMes(i, fechaInicio);
                        fechaActualRemanente = TiempoUtil.getintervalWeekDDMMYYYYbyDay(TiempoUtil.fechaTextoDiaMesAnio(fechaActual.get(1)));
                        rangoFechaInicio = TiempoUtil.getintervalWeekDDMMYYYYbyDay(fechaInicio);
                        fechaAnterior = TiempoUtil.obtenerFecgaInicioMesFinMesPorMes(i, fechaInicioAnterior);
                        fechaAnteriorRemanente = TiempoUtil.getintervalWeekDDMMYYYYbyDay(TiempoUtil.fechaTextoDiaMesAnio(fechaAnterior.get(1)));
                        
                        diasMes = TiempoUtil.obtenerUltimoDiaMes(TiempoUtil.fechaTextoDiaMesAnio(fechaActual.get(0)));
                        
                        lstObjectMonth = ejb.getEntradaProductoByFiltroMonth(fechaActual.get(0), fechaActual.get(1), idProducto);
                        lstObjectMonthAnterior = ejb.getEntradaProductoByFiltroMonth(fechaAnterior.get(0), fechaAnterior.get(1), idProducto);
                        lstObjectRemanenteMonth = ejb.getEntradaProductoByFiltroWeek(fechaActualRemanente.get(0), fechaActualRemanente.get(6), idProducto);
                        lstObjectRemanenteMonthAnterior = ejb.getEntradaProductoByFiltroWeek(fechaAnteriorRemanente.get(0), fechaAnteriorRemanente.get(6), idProducto);
                        
                        for (Object[] object : lstObjectMonth) {
                            AnalisisMercado dominio = new AnalisisMercado();
                            dominio.setPrecio(new BigDecimal(object[0].toString()));
                            dominio.setCantidadToneladas(new BigDecimal(object[1].toString()));
                            dominio.setDescripcionFiltro(TiempoUtil.getNameMonth(i + 1));
                            dominio.setDiasMes(diasMes);
                            
                            for (Object[] objectRemanente : lstObjectRemanenteMonth) {
                                dominio.setRemantePorSemana(new BigDecimal(objectRemanente[2].toString()));
                            }
                            for (Object[] objectRemanenteAnterior : lstObjectRemanenteMonthAnterior) {
                                dominio.setRemantePorSemanaAnterior(new BigDecimal(objectRemanenteAnterior[2].toString()));
                            }
                            for (Object[] objectAnterior : lstObjectMonthAnterior) {
                                dominio.setPrecioAnterior(new BigDecimal(objectAnterior[0].toString()));
                                dominio.setCantidadToneladasAnterior(new BigDecimal(objectAnterior[1].toString()));
                            }
                            
                            lstEntradaMercancia.add(dominio);
                        }
                        
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
    public AnalisisMercado getEntradaProductoByIdProducto(String idProducto,String fecha) {
        List<Object[]> lstObject = null;
        AnalisisMercado dominio = new AnalisisMercado();
        getEjb();
        lstObject = ejb.getEntradaProductoByIdProducto(idProducto, fecha);
        
        getEjb();
        for (Object[] obj : lstObject) {
            
            dominio.setIdEntrada(obj[0] != null ? new BigDecimal(obj[0].toString()) : new BigDecimal(0));
            dominio.setIdProductoFk(idProducto);
            dominio.setPrecio(obj[2] != null ? new BigDecimal(obj[2].toString()) : new BigDecimal(0));
            dominio.setCantidadToneladas(obj[3] != null ? new BigDecimal(obj[3].toString()) : new BigDecimal(0));
            dominio.setFecha((Date) obj[4]);
            dominio.setRemantePorSemana(obj[5] != null ? new BigDecimal(obj[5].toString()) : null);
            
        }
        
        return dominio;
    }
    
    @Override
    public AnalisisMercado getById(String dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public BigDecimal getRemanente(Date fechaRemanente, String idProducto) {
        
        BigDecimal remanete = new BigDecimal(0);
        List<String> lstFecha = TiempoUtil.getintervalWeekDDMMYYYYbyDay(fechaRemanente);
        
        getEjb();
        remanete = ejb.getRemanente(lstFecha.get(0), lstFecha.get(6), idProducto);
        
        return remanete;
        
    }
    
    @Override
    public int updateByIdProductoAndFecha(AnalisisMercado analisiMercado) {
        return ejb.updateByIdProductoAndFecha(analisiMercado);
    }
    
}
