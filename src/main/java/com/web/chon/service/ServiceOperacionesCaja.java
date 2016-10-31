/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;


import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.TipoOperacion;
import com.web.chon.dominio.Usuario;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import com.web.chon.negocio.NegocioOperacionesCaja;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author JesusAlfredo
 */
@Service
public class ServiceOperacionesCaja implements IfaceOperacionesCaja {
    NegocioOperacionesCaja ejb;
    private void getEjb() 
    {
        if (ejb == null) {
            try {
                ejb = (NegocioOperacionesCaja) Utilidades.getEJBRemote("ejbOperacionesCaja", NegocioOperacionesCaja.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceOperacionesCaja.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    @Override
    public int getNextVal() {
       getEjb();
       return ejb.getNextVal();
    
    }

    @Override
    public int insertaOperacion(OperacionesCaja es) {
       getEjb();
       return ejb.insertaOperacion(es);
    }

    @Override
    public int updateOperacion(OperacionesCaja es) {
        getEjb();
        return ejb.updateOperacion(es);
    }

    @Override
    public OperacionesCaja getOperacionByIdPk(BigDecimal idPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<OperacionesCaja> getOperacionesBy(BigDecimal idCajaFk, BigDecimal idOperacionFk, BigDecimal idConceptoFk, String fechaInicio, String fechaFin, BigDecimal idStatusFk, BigDecimal idUserFk,BigDecimal idCorte,BigDecimal inout) {
        getEjb();
        int i = 1;
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getOperacionesBy(idCajaFk, idOperacionFk, idConceptoFk, fechaInicio, fechaFin, idStatusFk, idUserFk,idCorte,inout);
        for (Object[] obj : lstObject) 
        {
            OperacionesCaja op = new OperacionesCaja();
            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setIdCorteCajaFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setIdCajaFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setIdCajaDestinoFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            op.setIdConceptoFk(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            op.setFecha(obj[5] == null ? null : (Date)obj[5]);
            op.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setIdUserFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            op.setComentarios(obj[8] == null ? null : obj[8].toString());
            op.setMonto(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            op.setEntradaSalida(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            op.setIdCuentaDestinoFk(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            op.setIdSucursalFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
            op.setNombreCaja(obj[13] == null ? null : obj[13].toString());
            op.setNombreConcepto(obj[14] == null ? "" : obj[14].toString());
            op.setNombreOperacion(obj[15] == null ? null : obj[15].toString());
            op.setNombreUsuario(obj[16] == null ? null : obj[16].toString());
            op.setNumero(i);
            i+=1;
            if(op.getIdStatusFk().intValue()==1)
            {
                op.setNombreStatus("APLICADO");
            }
            else
            {
                op.setNombreStatus("PENDIENTE");
            }
            if(op.getEntradaSalida().intValue()==1)
            {
                op.setNombreEntradaSalida("E");
            }
            else
            {
                op.setNombreEntradaSalida("S");
            }
            listaOperaciones.add(op);
        }
        return listaOperaciones;
    
    }

    @Override
    public ArrayList<OperacionesCaja> getTransferenciasEntrantes(BigDecimal idCajaFk) {
        getEjb();
        int i = 1;
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getTransferenciasEntrantes(idCajaFk);
        for (Object[] obj : lstObject) 
        {
            OperacionesCaja op = new OperacionesCaja();
            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setIdCorteCajaFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setIdCajaFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setIdCajaDestinoFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            op.setIdConceptoFk(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            op.setFecha(obj[5] == null ? null : (Date)obj[5]);
            op.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setIdUserFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            op.setComentarios(obj[8] == null ? null : obj[8].toString());
            op.setMonto(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            op.setEntradaSalida(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            op.setIdCuentaDestinoFk(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            op.setIdSucursalFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
            
            op.setNombreCaja(obj[13] == null ? null : obj[13].toString());
            op.setNombreConcepto(obj[14] == null ? "" : obj[14].toString());
            op.setNombreOperacion(obj[15] == null ? null : obj[15].toString());
            op.setNombreUsuario(obj[16] == null ? null : obj[16].toString());
            op.setNumero(i);
            i+=1;
            if(op.getIdStatusFk().intValue()==1)
            {
                op.setNombreStatus("APLICADO");
            }
            else
            {
                op.setNombreStatus("PENDIENTE");
            }
            if(op.getEntradaSalida().intValue()==1)
            {
                op.setNombreEntradaSalida("E");
            }
            else
            {
                op.setNombreEntradaSalida("S");
            }
            listaOperaciones.add(op);
        }
        return listaOperaciones;
    
    
    }

    @Override
    public int updateStatusConcepto(BigDecimal idOperacionPk, BigDecimal idStatusFk,BigDecimal idConceptoFk) {
        getEjb();
        return ejb.updateStatusConceptoOperacion(idOperacionPk, idStatusFk,idConceptoFk);
    }

    @Override
    public ArrayList<TipoOperacion> getOperacionesCorteBy(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal idES) {
        getEjb();
        int i = 1;
        ArrayList<TipoOperacion> lista = new ArrayList<TipoOperacion>();
        List<Object[]> lstObject = ejb.getOperacionesCorteBy(idCajaFk, idUserFk, idES);
        for (Object[] obj : lstObject) 
        {
            TipoOperacion op = new TipoOperacion();
            op.setIdTipoOperacionPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setNombre(obj[1] == null ? null : obj[1].toString());
            op.setMontoTotal(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setNumero(i);
            i+=1;
            lista.add(op);
        }
        return lista;
    
    
    }

    @Override
    public ArrayList<OperacionesCaja> getOperaciones(BigDecimal idCajaFk, BigDecimal idUserFk) {
        getEjb();
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getOperaciones(idCajaFk,idUserFk);
        for (Object[] obj : lstObject) 
        {
            OperacionesCaja op = new OperacionesCaja();
            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setIdCorteCajaFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setIdCajaFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setIdCajaDestinoFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            op.setIdConceptoFk(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            op.setFecha(obj[5] == null ? null : (Date)obj[5]);
            op.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setIdUserFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            op.setComentarios(obj[8] == null ? null : obj[8].toString());
            op.setMonto(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            op.setEntradaSalida(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            op.setIdCuentaDestinoFk(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            listaOperaciones.add(op);
        }
        return listaOperaciones;
    
    
    }

    @Override
    public int updateCorte(BigDecimal idOperacionPk, BigDecimal idCorteFk) {
        getEjb();
        return ejb.updateCorteCaja(idOperacionPk, idCorteFk);
    
    }

    @Override
    public ArrayList<OperacionesCaja> getCheques(BigDecimal idCajaFk, BigDecimal idUserFk,BigDecimal idINOUT) {
       getEjb();
       int i = 1;
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getCheques(idCajaFk, idUserFk,idINOUT);
        for (Object[] obj : lstObject) 
        {
            OperacionesCaja op = new OperacionesCaja();
            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setIdCorteCajaFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setIdCajaFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setIdCajaDestinoFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            op.setIdConceptoFk(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            op.setFecha(obj[5] == null ? null : (Date)obj[5]);
            op.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setIdUserFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            op.setComentarios(obj[8] == null ? null : obj[8].toString());
            op.setMonto(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            op.setEntradaSalida(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            op.setNumero(i);
            i +=1;
            listaOperaciones.add(op);
        }
        return listaOperaciones;
    
    }

    @Override
    public ArrayList<OperacionesCaja> getDepositosEntrantes() 
    {
        getEjb();
        int i = 1;
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getDepositosEntrantes();
        for (Object[] obj : lstObject) 
        {
            OperacionesCaja op = new OperacionesCaja();
            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setMonto(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setNombreCaja(obj[2] == null ? null : obj[2].toString());
            op.setNombreConcepto(obj[3] == null ? null : obj[3].toString());
            op.setNombreOperacion(obj[4] == null ? null : obj[4].toString());
            op.setFecha(obj[5] == null ? null : (Date)obj[5]);
            op.setNombreUsuario(obj[6] == null ? null : obj[6].toString());
            op.setNombreBanco(obj[7] == null ? null : obj[7].toString());
            op.setCuentaBanco(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            op.setComentarios(obj[9] == null ? null : obj[9].toString());
            op.setNumero(i);
            i+=1;
            listaOperaciones.add(op);
        }
        return listaOperaciones;
    
    }

    @Override
    public ArrayList<Usuario> getResponsables(BigDecimal idCajaFk) {
        getEjb();
        ArrayList<Usuario> listaResponsables = new ArrayList<Usuario>();
        List<Object[]> lstObject = ejb.getResponsables(idCajaFk);
        for (Object[] obj : lstObject) 
        {
            Usuario u = new Usuario();
            u.setIdUsuarioPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            u.setNombreUsuario(obj[1] == null ? null : obj[1].toString());
            System.out.println("-------------------------------------"+u.toString());
            listaResponsables.add(u);
        }
        return listaResponsables;
    }

    @Override
    public ArrayList<OperacionesCaja> getDetalles(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal entrada_salida, BigDecimal idStatusFk) {
        System.out.println("Entro a Metodo Get Detalles");
        getEjb();
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getDetalles(idCajaFk,idUserFk,entrada_salida,idStatusFk);
        int count = 1;
        for (Object[] obj : lstObject) 
        {
            OperacionesCaja op = new OperacionesCaja();
            
            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setNombreConcepto(obj[1] == null ? null : obj[1].toString());
            op.setIdSucursalFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setNombreSucursal(obj[3] == null ? null : obj[3].toString());
            op.setMonto(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            op.setIdConceptoFk(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            op.setEntradaSalida(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setNumero(count);
            if(op.getEntradaSalida().intValue()==1)
            {
                op.setNombreEntradaSalida("E");
            }
            else
            {
                op.setNombreEntradaSalida("S");
            }
            /*op.setIdCorteCajaFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setIdCajaFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setIdCajaDestinoFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            
            op.setFecha(obj[5] == null ? null : (Date)obj[5]);
            op.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setIdUserFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            op.setComentarios(obj[8] == null ? null : obj[8].toString());
            op.setMonto(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            op.setEntradaSalida(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            op.setIdCuentaDestinoFk(obj[11] == null ? null : new BigDecimal(obj[11].toString()));*/
            System.out.println("Objeto: "+op.toString());
            count += 1;
            listaOperaciones.add(op);
        }
        for (OperacionesCaja c : listaOperaciones)
        {
            System.out.println("c: "+c.toString());
        }
        System.out.println("Saliendo de Metodo");
        return listaOperaciones;
    
    }
    
    @Override
    public ArrayList<TipoOperacion> getOperacionesByIdCorteCajaFk(BigDecimal idCorteCajaFk,BigDecimal entrada_salida) {
        getEjb();
        getEjb();
        int i = 1;
        ArrayList<TipoOperacion> lista = new ArrayList<TipoOperacion>();
         
        List<Object[]> lstObject = ejb.getOperacionesByIdCorteCajaFk(idCorteCajaFk,entrada_salida);
        
        //List<Object[]> lstObject = ejb.getOperacionesCorteBy(idCajaFk, idUserFk, idES);
        for (Object[] obj : lstObject) 
        {
            TipoOperacion op = new TipoOperacion();
            op.setIdTipoOperacionPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setNombre(obj[1] == null ? null : obj[1].toString());
            op.setMontoTotal(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setNumero(i);
            i+=1;
            lista.add(op);
        }
        return lista;
       
    }

    
    
}
