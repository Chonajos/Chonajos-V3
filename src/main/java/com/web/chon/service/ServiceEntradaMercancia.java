package com.web.chon.service;

import com.web.chon.dominio.CarroDetalleGeneral;
import com.web.chon.dominio.ComprobantesDigitales;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.MayoreoProductoEntradaProducto;
import com.web.chon.dominio.Pagina;

import com.web.chon.negocio.NegocioEntradaMercancia;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author marcogante
 */
@Service
public class ServiceEntradaMercancia implements IfaceEntradaMercancia {

    @Autowired
    IfaceEntradaMercanciaProducto ifaceEntradaMercanciaProducto;
    @Autowired
    IfaceVentaMayoreoProducto ifaceVentaMayoreoProducto;
    @Autowired
    private IfaceComprobantes ifaceComprobantes;
    NegocioEntradaMercancia ejb;

    public void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioEntradaMercancia) Utilidades.getEJBRemote("ejbEntradaMercancia", NegocioEntradaMercancia.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertEntradaMercancia(EntradaMercancia entrada) {

        getEjb();
        return ejb.insertEntradaMercancia(entrada);

    }

    @Override
    public int buscaMaxMovimiento(EntradaMercancia entrada) {
        getEjb();
        try {
            return ejb.buscaMaxMovimiento(entrada);
        } catch (Exception ex) {
            //Logger.getLogger(NegocioEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int getNextVal() {
        getEjb();
        try {
            return ejb.getNextVal();

        } catch (Exception ex) {
            Logger.getLogger(ServiceEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public ArrayList<EntradaMercancia> getEntradaProductoByIntervalDate(Date fechaInicio, Date fechaFin, BigDecimal idSucursal, BigDecimal idProvedor, BigDecimal carro) {
        getEjb();

        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<EntradaMercancia> lstEntradaMercancia2 = new ArrayList<EntradaMercancia>();

        lstObject = ejb.getEntradaProductoByIntervalDate(fechaInicio, fechaFin, idSucursal, idProvedor, carro);

        for (Object[] obj : lstObject) {
            EntradaMercancia dominio = new EntradaMercancia();
            dominio.setIdEmPK(new BigDecimal(obj[0].toString()));
            dominio.setIdProvedorFK(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            dominio.setMovimiento(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            dominio.setFecha(obj[3] == null ? null : (Date) obj[3]);
            dominio.setRemision(obj[4] == null ? null : obj[4].toString());
            dominio.setIdSucursalFK(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            dominio.setFolio(obj[6] == null ? null : obj[6].toString());
            dominio.setIdStatusFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            dominio.setKilosTotales(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            dominio.setKilosTotalesProvedor(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            dominio.setComentariosGenerales(obj[10] == null ? " " : obj[10].toString());
            dominio.setNombreProvedor(obj[12] == null ? " " : obj[12].toString());
            dominio.setNombreSucursal(obj[13] == null ? " " : obj[13].toString());
            dominio.setIdCarroSucursal(obj[14] == null ? null : new BigDecimal(obj[14].toString()));
            dominio.setCantidadEmpaquesReales(obj[15] == null ? null : new BigDecimal(obj[15].toString()));
            dominio.setCantidadEmpaquesProvedor(obj[16] == null ? null : new BigDecimal(obj[16].toString()));
            dominio.setFechaPago(obj[17] == null ? null : (Date) obj[17]);
            dominio.setNombreRecibidor(obj[18] == null ? " " : obj[18].toString());
            dominio.setIdUsuario(obj[19] == null ? null : new BigDecimal(obj[19].toString()));
            dominio.setListaProductos(ifaceEntradaMercanciaProducto.getEntradaProductoByIdEM(dominio.getIdEmPK()));
            
            ArrayList<ComprobantesDigitales> lista_cd = new ArrayList<ComprobantesDigitales>();
            
            lista_cd = ifaceComprobantes.getComprobantesByIdTipoLlave(new BigDecimal(1), dominio.getIdEmPK());
            
            dominio.setListaComprobantes(lista_cd);
            
            lstEntradaMercancia2.add(dominio);
        }

        return lstEntradaMercancia2;
    }

    @Override
    public Pagina<EntradaMercancia> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pagina<EntradaMercancia> findAllDominio(EntradaMercancia filters, int first, int pageSize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EntradaMercancia getById(BigDecimal dominio) {
        getEjb();
        List<Object[]> object = ejb.getEntradaById(dominio);
        EntradaMercancia entra = new EntradaMercancia();
        for (Object[] obj : object) {
            entra.setIdEmPK(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            entra.setFolio(obj[1] == null ? "" : obj[1].toString());
        }

        return entra;
    }

    @Override
    public EntradaMercancia getById(String dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int create(EntradaMercancia dominio) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(EntradaMercancia dominio) {
        getEjb();
        try {
            return ejb.updateEntradaMercancia(dominio);

        } catch (Exception ex) {
            Logger.getLogger(ServiceEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public List<EntradaMercancia> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(BigDecimal id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<EntradaMercancia> getSubEntradaByNombre(String nombre) {
        try {
            ArrayList<EntradaMercancia> lstEntradas = new ArrayList<EntradaMercancia>();

            getEjb();
            List<Object[]> object = ejb.getSubEntradaByNombre(nombre);

            for (Object[] obj : object) {

                EntradaMercancia entrada = new EntradaMercancia();
                entrada.setIdEmPK(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                entrada.setFolio(obj[1] == null ? "" : obj[1].toString());

                lstEntradas.add(entrada);
            }

            return lstEntradas;

        } catch (Exception ex) {
            Logger.getLogger(ServiceEntradaMercancia.class
                    .getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public int getCarroSucursal(BigDecimal idSucursal) {
        getEjb();
        try {
            return ejb.getCarroSucursal(idSucursal);
        } catch (Exception ex) {
            Logger.getLogger(ServiceEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int deleteEntradaMercancia(EntradaMercancia entrada) {
        getEjb();
        try {
            return ejb.deleteEntradaMercancia(entrada);
        } catch (Exception ex) {
            Logger.getLogger(ServiceEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateEntradaMercancia(EntradaMercancia entrada) {
        getEjb();
        try {
            return ejb.updateEntradaMercancia(entrada);
        } catch (Exception ex) {
            Logger.getLogger(ServiceEntradaMercancia.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public EntradaMercancia getEntradaByIdEmPFk(BigDecimal idEmPFk) {
        getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getEntradaByIdEmPFk(idEmPFk);
        EntradaMercancia dominio = new EntradaMercancia();
        for (Object[] obj : lstObject) {
            dominio.setIdEmPK(new BigDecimal(obj[0].toString()));
            dominio.setIdProvedorFK(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            dominio.setMovimiento(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            dominio.setFecha(obj[3] == null ? null : (Date) obj[3]);
            dominio.setRemision(obj[4] == null ? null : obj[4].toString());
            dominio.setIdSucursalFK(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            dominio.setFolio(obj[6] == null ? null : obj[6].toString());
            dominio.setIdStatusFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            dominio.setKilosTotales(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            dominio.setKilosTotalesProvedor(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            dominio.setComentariosGenerales(obj[10] == null ? " " : obj[10].toString());
            dominio.setFechaRemision(obj[11] == null ? null : (Date) obj[11]);

            dominio.setIdUsuario(obj[14] == null ? null : new BigDecimal(obj[14].toString()));
            dominio.setIdCarroSucursal(obj[15] == null ? null : new BigDecimal(obj[15].toString()));

            dominio.setListaProductos(ifaceEntradaMercanciaProducto.getEntradaProductoByIdEM(dominio.getIdEmPK()));
        }
        return dominio;

    }

    @Override
    public EntradaMercancia getEntradaByIdPk(BigDecimal idPk) {
        getEjb();
        List<Object[]> lstObject = new ArrayList<Object[]>();
        lstObject = ejb.getEntradaByIdPk(idPk);
        EntradaMercancia dominio = new EntradaMercancia();
        for (Object[] obj : lstObject) {
            dominio.setIdEmPK(new BigDecimal(obj[0].toString()));
            dominio.setIdProvedorFK(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            dominio.setMovimiento(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            dominio.setFecha(obj[3] == null ? null : (Date) obj[3]);
            dominio.setRemision(obj[4] == null ? null : obj[4].toString());
            dominio.setIdSucursalFK(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            dominio.setFolio(obj[6] == null ? null : obj[6].toString());
            dominio.setIdStatusFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            dominio.setKilosTotales(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            dominio.setKilosTotalesProvedor(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            dominio.setNombreProvedor(obj[12] == null ? " " : obj[12].toString());
            dominio.setNombreSucursal(obj[13] == null ? " " : obj[13].toString());
            dominio.setIdCarroSucursal(obj[14] == null ? null : new BigDecimal(obj[14].toString()));
            dominio.setComentariosGenerales(obj[15] == null ? " " : obj[15].toString());
            dominio.setListaProductos(ifaceEntradaMercanciaProducto.getEntradaProductoByIdEM(dominio.getIdEmPK()));
        }
        return dominio;
    }

    @Override
    public ArrayList<EntradaMercancia> getCarrosByIdSucursalAnsIdProvedor(BigDecimal idSucursal, BigDecimal idProvedor) {

        getEjb();

        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<EntradaMercancia> lstEntradaMercancia = new ArrayList<EntradaMercancia>();

        lstObject = ejb.getCarrosByIdSucursalAndIdProvedor(idSucursal, idProvedor,null,null);
        for (Object[] obj : lstObject) {

            EntradaMercancia dominio = new EntradaMercancia();

            dominio.setIdCarroSucursal(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            dominio.setRemision(obj[1] == null ? null : obj[1].toString());

            lstEntradaMercancia.add(dominio);
        }

        return lstEntradaMercancia;
    }

    @Override
    public ArrayList<CarroDetalleGeneral> getReporteGeneralCarro(BigDecimal idSucursal, BigDecimal idProvedor, BigDecimal carro, String fechaInicio, String fechaFin,BigDecimal estatusCarro) {

        getEjb();
        
        //ESTATUS DE LOS CARROS 1 EN PROCESO 2 CERRADO Y LOS QUE VALLANA EXISTIR

        List<Object[]> lstObject = new ArrayList<Object[]>();
        ArrayList<CarroDetalleGeneral> lstCarroDetalleGeneral = new ArrayList<CarroDetalleGeneral>();

        lstObject = ejb.getCarrosByIdSucursalAndIdProvedor(idSucursal, idProvedor, carro,estatusCarro);

        for (Object[] obj : lstObject) {

            CarroDetalleGeneral dominio = new CarroDetalleGeneral();

            dominio.setCarro(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            dominio.setIdentificador(obj[1] == null ? null : obj[1].toString());
            dominio.setFecha(obj[2] == null ? null : (Date) obj[2]);
            dominio.setNombreProvedor(obj[3] == null ? "" : obj[3].toString());
            dominio.setIdStatus(obj[4] == null ? new BigDecimal(1) : new BigDecimal(obj[4].toString()));
            dominio.setIdEntradaMercancia(obj[5] == null ? null : new BigDecimal(obj[5].toString()));

            ArrayList<MayoreoProductoEntradaProducto> lstMayoreoProductoEntradaProducto = ifaceVentaMayoreoProducto.getVentaByIdSucursalAndCarro(idSucursal, dominio.getCarro(), fechaInicio, fechaFin);
            //Se calculan las ventas, comisiones y el status del  carro
            String status = "En proceso";
            BigDecimal venta = new BigDecimal(0);
            BigDecimal comision = new BigDecimal(0);
            for (MayoreoProductoEntradaProducto mayoreoProducto : lstMayoreoProductoEntradaProducto) {

                venta = venta.add(mayoreoProducto.getTotalVenta());
                comision = comision.add(mayoreoProducto.getComision());
  
            }

            //Si no hay ventas se pone un status en proceso
            if (dominio.getIdStatus().equals(new BigDecimal(1))) {
                status = "En Proceso";
            }else if(dominio.getIdStatus().equals(new BigDecimal(2))){
                status = "Cerrado";
            }

            dominio.setVenta(venta);
            dominio.setStatus(status);
            dominio.setComision(comision);

            lstCarroDetalleGeneral.add(dominio);
        }

        return lstCarroDetalleGeneral;
    }

    @Override
    public int cerrarCarro(BigDecimal idEntradaMercancia) {
        try {
            getEjb();
            return ejb.cerrarCarro(idEntradaMercancia);
        } catch (Exception ex) {
            return 0;
        }

    }
}
