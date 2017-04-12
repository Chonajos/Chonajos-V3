/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.ComprobantesDigitales;
import com.web.chon.dominio.OperacionesCaja;
import com.web.chon.dominio.TipoOperacion;
import com.web.chon.dominio.Usuario;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import com.web.chon.negocio.NegocioOperacionesCaja;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author JesusAlfredo
 */
@Service
public class ServiceOperacionesCaja implements IfaceOperacionesCaja {

    NegocioOperacionesCaja ejb;
    @Autowired
    private IfaceComprobantes ifaceComprobantes;

    private void getEjb() {
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
    public int insertaOperacion(OperacionesCaja o) {
        getEjb();
        int i = ejb.insertaOperacion(o);

        if (i == 1) {
            try {
                byte[] fichero = o.getFichero();
                ejb.insertarDocumento(o.getIdOperacionesCajaPk(), fichero);
            } catch (SQLException ex) {
                Logger.getLogger(ServiceOperacionesCaja.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return i;
    }

    @Override
    public int updateOperacion(OperacionesCaja es) {
        getEjb();
        return ejb.updateOperacion(es);
    }

    @Override
    public OperacionesCaja getOperacionByIdPk(BigDecimal idPk) {
        try {
            OperacionesCaja opcaja = new OperacionesCaja();
            getEjb();
            List<Object[]> object = ejb.getOperacionByIdOperacionPK(idPk);
            for (Object[] obj : object) {
                opcaja.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                opcaja.setIdCorteCajaFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
                opcaja.setIdCajaFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
                opcaja.setIdCajaDestinoFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
                opcaja.setIdConceptoFk(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
                opcaja.setFecha(obj[5] == null ? null : (Date) obj[5]);
                opcaja.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
                opcaja.setIdUserFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                opcaja.setComentarios(obj[8] == null ? null : obj[8].toString());
                opcaja.setMonto(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
                opcaja.setEntradaSalida(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
                opcaja.setIdCuentaDestinoFk(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
                opcaja.setIdSucursalFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
                opcaja.setIdFormaPago(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
                opcaja.setIdTipoOperacionFk(obj[14] == null ? null : new BigDecimal(obj[14].toString()));
                opcaja.setFichero((obj[15] == null ? null : (byte[]) (obj[15])));
            }

            return opcaja;
        } catch (Exception ex) {
            Logger.getLogger(ServiceOperacionesCaja.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public ArrayList<OperacionesCaja> getOperacionesBy(BigDecimal idCajaFk, BigDecimal idOperacionFk, BigDecimal idConceptoFk, String fechaInicio, String fechaFin, BigDecimal idStatusFk, BigDecimal idUserFk, BigDecimal idCorte, BigDecimal inout, BigDecimal idFormaPago) {
        getEjb();
        int i = 1;
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getOperacionesBy(idCajaFk, idOperacionFk, idConceptoFk, fechaInicio, fechaFin, idStatusFk, idUserFk, idCorte, inout, idFormaPago);
        for (Object[] obj : lstObject) {
            OperacionesCaja op = new OperacionesCaja();
            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setIdCorteCajaFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setIdCajaFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setIdCajaDestinoFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            op.setIdConceptoFk(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            op.setFecha(obj[5] == null ? null : (Date) obj[5]);
            op.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setIdUserFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            op.setComentarios(obj[8] == null ? null : obj[8].toString());
            op.setMonto(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            op.setEntradaSalida(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            op.setIdCuentaDestinoFk(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            op.setIdSucursalFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
            op.setIdFormaPago(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
            op.setIdTipoOperacionFk(obj[14] == null ? null : new BigDecimal(obj[14].toString()));
            op.setFichero((obj[15] == null ? null : (byte[]) (obj[15])));

            op.setNombreCaja(obj[16] == null ? null : obj[16].toString());
            op.setNombreConcepto(obj[17] == null ? "" : obj[17].toString());
            op.setNombreOperacion(obj[18] == null ? null : obj[18].toString());
            op.setNombreUsuario(obj[19] == null ? null : obj[19].toString());
            op.setNombreSucursal(obj[20] == null ? null : obj[20].toString());
            op.setNombrePago(obj[21] == null ? null : obj[21].toString());
            op.setNumero(i);
            i += 1;
            switch (op.getIdStatusFk().intValue()) {
                case 1:
                    op.setNombreStatus("APLICADO");
                    break;
                case 2:
                    op.setNombreStatus("PENDIENTE");
                    break;
                case 3:
                    op.setNombreStatus("RECHAZADA");
                    break;
                case 4:
                    op.setNombreStatus("CANCELADA");
                    break;
                default:
                    op.setNombreStatus("ERROR");
                    break;
            }

            if (op.getEntradaSalida().intValue() == 1) {
                op.setNombreEntradaSalida("E");
            } else {
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
        for (Object[] obj : lstObject) {
            OperacionesCaja op = new OperacionesCaja();
            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setIdCorteCajaFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setIdCajaFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setIdCajaDestinoFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            op.setIdConceptoFk(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            op.setFecha(obj[5] == null ? null : (Date) obj[5]);
            op.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setIdUserFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            op.setComentarios(obj[8] == null ? null : obj[8].toString());
            op.setMonto(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            op.setEntradaSalida(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            op.setIdCuentaDestinoFk(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            op.setIdSucursalFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));

            op.setIdFormaPago(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
            op.setIdTipoOperacionFk(obj[14] == null ? null : new BigDecimal(obj[14].toString()));
            op.setFichero((obj[15] == null ? null : (byte[]) (obj[15])));
            op.setNombreCaja(obj[16] == null ? null : obj[16].toString());
            op.setNombreConcepto(obj[17] == null ? "" : obj[17].toString());
            op.setNombreOperacion(obj[18] == null ? null : obj[18].toString());
            op.setNombreUsuario(obj[19] == null ? null : obj[19].toString());
            op.setNumero(i);
            i += 1;
            if (op.getIdStatusFk().intValue() == 1) {
                op.setNombreStatus("APLICADO");
            } else {
                op.setNombreStatus("PENDIENTE");
            }
            if (op.getEntradaSalida().intValue() == 1) {
                op.setNombreEntradaSalida("E");
            } else {
                op.setNombreEntradaSalida("S");
            }
            listaOperaciones.add(op);
        }
        return listaOperaciones;

    }

    @Override
    public int updateStatusConcepto(BigDecimal idOperacionPk, BigDecimal idStatusFk, BigDecimal idConceptoFk) {
        getEjb();
        return ejb.updateStatusConceptoOperacion(idOperacionPk, idStatusFk, idConceptoFk);
    }

    @Override
    public ArrayList<TipoOperacion> getOperacionesCorteBy(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal idES) {
        getEjb();
        int i = 1;
        ArrayList<TipoOperacion> lista = new ArrayList<TipoOperacion>();
        List<Object[]> lstObject = ejb.getOperacionesCorteBy(idCajaFk, idUserFk, idES);
        for (Object[] obj : lstObject) {
            TipoOperacion op = new TipoOperacion();
            op.setIdTipoOperacionPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setNombre(obj[1] == null ? null : obj[1].toString());
            op.setMontoTotal(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setNumero(i);
            i += 1;
            lista.add(op);
        }
        return lista;

    }

    @Override
    public ArrayList<OperacionesCaja> getOperaciones(BigDecimal idCajaFk, BigDecimal idUserFk) {
        getEjb();
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getOperaciones(idCajaFk, idUserFk);
        for (Object[] obj : lstObject) {
            OperacionesCaja op = new OperacionesCaja();
            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setIdCorteCajaFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setIdCajaFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setIdCajaDestinoFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            op.setIdConceptoFk(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            op.setFecha(obj[5] == null ? null : (Date) obj[5]);
            op.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setIdUserFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            op.setComentarios(obj[8] == null ? null : obj[8].toString());
            op.setMonto(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            op.setEntradaSalida(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            op.setIdCuentaDestinoFk(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            op.setIdSucursalFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
            op.setIdFormaPago(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
            op.setIdTipoOperacionFk(obj[14] == null ? null : new BigDecimal(obj[14].toString()));

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
    public ArrayList<OperacionesCaja> getCheques(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal idINOUT) {
        getEjb();
        int i = 1;
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getCheques(idCajaFk, idUserFk, idINOUT);
        for (Object[] obj : lstObject) {
            OperacionesCaja op = new OperacionesCaja();
            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setIdCorteCajaFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setIdCajaFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setIdCajaDestinoFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            op.setIdConceptoFk(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            op.setFecha(obj[5] == null ? null : (Date) obj[5]);
            op.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setIdUserFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            op.setComentarios(obj[8] == null ? null : obj[8].toString());
            op.setMonto(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            op.setEntradaSalida(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            op.setNumero(i);
            i += 1;
            listaOperaciones.add(op);
        }
        return listaOperaciones;

    }

    @Override
    public ArrayList<OperacionesCaja> getDepositosEntrantes(BigDecimal idSucursalfk) {
        getEjb();
        int i = 1;
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getDepositosEntrantes(idSucursalfk);
        for (Object[] obj : lstObject) {
            OperacionesCaja op = new OperacionesCaja();
            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setMonto(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setNombreCaja(obj[2] == null ? null : obj[2].toString());
            op.setNombreConcepto(obj[3] == null ? null : obj[3].toString());
            op.setNombreOperacion(obj[4] == null ? null : obj[4].toString());
            op.setFecha(obj[5] == null ? null : (Date) obj[5]);
            op.setNombreUsuario(obj[6] == null ? null : obj[6].toString());
            op.setNombreBanco(obj[7] == null ? null : obj[7].toString());
            op.setCuentaBanco(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            op.setComentarios(obj[9] == null ? null : obj[9].toString());
            op.setIdConceptoFk(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            op.setIdCajaFk(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            op.setIdCuentaDestinoFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
            op.setIdUserFk(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
            op.setIdSucursalFk(obj[14] == null ? null : new BigDecimal(obj[14].toString()));

            ComprobantesDigitales cd = new ComprobantesDigitales();
            cd = ifaceComprobantes.getComprobanteByIdTipoLlave(new BigDecimal(4), op.getIdOperacionesCajaPk());

            if (cd != null) {
                op.setFichero(cd.getFichero());
            }

            op.setNumero(i);
            i += 1;
            listaOperaciones.add(op);
        }
        return listaOperaciones;

    }

    @Override
    public ArrayList<Usuario> getResponsables(BigDecimal idCajaFk) {
        getEjb();
        ArrayList<Usuario> listaResponsables = new ArrayList<Usuario>();
        List<Object[]> lstObject = ejb.getResponsables(idCajaFk);
        for (Object[] obj : lstObject) {
            Usuario u = new Usuario();
            u.setIdUsuarioPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            u.setNombreUsuario(obj[1] == null ? null : obj[1].toString());
            listaResponsables.add(u);
        }
        return listaResponsables;
    }

    @Override
    public ArrayList<OperacionesCaja> getDetalles(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal entrada_salida, BigDecimal idStatusFk) {
        System.out.println("Entro a Metodo Get Detalles");
        getEjb();
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getDetalles(idCajaFk, idUserFk, entrada_salida, idStatusFk);
        int count = 1;
        for (Object[] obj : lstObject) {
            OperacionesCaja op = new OperacionesCaja();

            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setNombreConcepto(obj[1] == null ? null : obj[1].toString());
            op.setIdSucursalFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setNombreSucursal(obj[3] == null ? null : obj[3].toString());
            op.setMonto(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            op.setIdConceptoFk(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            op.setEntradaSalida(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setNumero(count);
            if (op.getEntradaSalida().intValue() == 1) {
                op.setNombreEntradaSalida("E");
            } else {
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
            System.out.println("Objeto: " + op.toString());
            count += 1;
            listaOperaciones.add(op);
        }
        for (OperacionesCaja c : listaOperaciones) {
            System.out.println("c: " + c.toString());
        }
        System.out.println("Saliendo de Metodo");
        return listaOperaciones;

    }

    @Override
    public ArrayList<OperacionesCaja> getDetallesCorte(BigDecimal idCajaFk, BigDecimal idUserFk, BigDecimal entrada_salida, BigDecimal idStatusFk, BigDecimal idCorteFk) {
        System.out.println("Entro a Metodo Get Detalles");
        getEjb();
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getDetallesCorte(idCajaFk, idUserFk, entrada_salida, idStatusFk, idCorteFk);
        int count = 1;
        for (Object[] obj : lstObject) {
            OperacionesCaja op = new OperacionesCaja();

            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setNombreConcepto(obj[1] == null ? null : obj[1].toString());
            op.setIdSucursalFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setNombreSucursal(obj[3] == null ? null : obj[3].toString());
            op.setMonto(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            op.setIdConceptoFk(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            op.setEntradaSalida(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setNumero(count);
            if (op.getEntradaSalida().intValue() == 1) {
                op.setNombreEntradaSalida("E");
            } else {
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
            System.out.println("Objeto: " + op.toString());
            count += 1;
            listaOperaciones.add(op);
        }
        for (OperacionesCaja c : listaOperaciones) {
            System.out.println("c: " + c.toString());
        }
        System.out.println("Saliendo de Metodo");
        return listaOperaciones;

    }

    @Override
    public ArrayList<TipoOperacion> getOperacionesByIdCorteCajaFk(BigDecimal idCorteCajaFk, BigDecimal entrada_salida) {
        getEjb();
        int i = 1;
        ArrayList<TipoOperacion> lista = new ArrayList<TipoOperacion>();

        List<Object[]> lstObject = ejb.getOperacionesByIdCorteCajaFk(idCorteCajaFk, entrada_salida);

        //List<Object[]> lstObject = ejb.getOperacionesCorteBy(idCajaFk, idUserFk, idES);
        for (Object[] obj : lstObject) {
            TipoOperacion op = new TipoOperacion();
            op.setIdTipoOperacionPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setNombre(obj[1] == null ? null : obj[1].toString());
            op.setMontoTotal(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setNumero(i);
            i += 1;
            lista.add(op);
        }
        return lista;

    }

    @Override
    public ArrayList<OperacionesCaja> getOperaciones(BigDecimal idCajaFk, BigDecimal idEntradaSalida, BigDecimal idUsuarioFk) {
        getEjb();
        int i = 1;
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getOperaciones(idCajaFk, idEntradaSalida, idUsuarioFk);
        for (Object[] obj : lstObject) {
            OperacionesCaja op = new OperacionesCaja();
            op.setIdConceptoFk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setFecha(obj[1] == null ? null : (Date) obj[1]);
            op.setNombreCaja(obj[2] == null ? null : obj[2].toString());
            op.setNombreConcepto(obj[3] == null ? null : obj[3].toString());
            op.setComentarios(obj[4] == null ? null : obj[4].toString());
            op.setMonto(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            op.setIdStatusFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));

            listaOperaciones.add(op);
        }
        return listaOperaciones;
    }

    @Override
    public ArrayList<OperacionesCaja> getOperacionesByCategoria(BigDecimal idCategoriaFk, BigDecimal idSucursalFk, BigDecimal idCajaFk, BigDecimal idStatusFk, BigDecimal idConceptoFk, BigDecimal idTipoOperacionFk, String fechaInicio, String fechaFin) {
        getEjb();
        int i = 1;
        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getOperacionesByCategoria(idCategoriaFk, idSucursalFk, idCajaFk, idStatusFk, idConceptoFk, idTipoOperacionFk, fechaInicio, fechaFin);
        for (Object[] obj : lstObject) {
            OperacionesCaja op = new OperacionesCaja();
            op.setIdOperacionesCajaPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setIdCorteCajaFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setIdCajaFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setIdCajaDestinoFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));

            op.setFecha(obj[4] == null ? null : (Date) obj[4]);
            op.setIdStatusFk(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            op.setIdUserFk(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setComentarios(obj[7] == null ? null : obj[7].toString());
            op.setMonto(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            op.setEntradaSalida(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            op.setIdCuentaDestinoFk(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            op.setIdSucursalFk(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
            op.setIdFormaPago(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
            op.setIdTipoOperacionFk(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
            op.setFichero((obj[14] == null ? null : (byte[]) (obj[14])));
            op.setNombreSucursal(obj[15] == null ? null : obj[15].toString());
            op.setNombreCaja(obj[16] == null ? null : obj[16].toString());
            op.setNombreStatus(obj[17] == null ? null : obj[17].toString());
            op.setNombreOperacion(obj[18] == null ? null : obj[18].toString());
            op.setNombreConcepto(obj[19] == null ? null : obj[19].toString());
            op.setIdConceptoFk(obj[20] == null ? null : new BigDecimal(obj[20].toString()));

            listaOperaciones.add(op);

        }
        return listaOperaciones;

    }

    @Override
    public ArrayList<OperacionesCaja> getGenerales(BigDecimal idCajaFk, BigDecimal idEntradaSalida, BigDecimal idUsuarioFk, BigDecimal idStatusFk, BigDecimal idSucursalFk, BigDecimal TIPO) {
        getEjb();

        ArrayList<OperacionesCaja> listaOperaciones = new ArrayList<OperacionesCaja>();
        List<Object[]> lstObject = ejb.getGenerales(idCajaFk, idEntradaSalida, idUsuarioFk, idStatusFk, idSucursalFk, TIPO);
        for (Object[] obj : lstObject) {
            OperacionesCaja op = new OperacionesCaja();
            if (TIPO.intValue() == 1) {

                op.setNombrePago(obj[0] == null ? null : obj[0].toString());
                op.setMonto(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
                op.setIdFormaPago(obj[2] == null ? null : new BigDecimal(obj[2].toString()));

            } else {

                op.setNombreOperacion(obj[0] == null ? null : obj[0].toString());
                op.setNombreConcepto(obj[1] == null ? null : obj[1].toString());
                op.setNombrePago(obj[2] == null ? null : obj[2].toString());
                op.setMonto(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            }

            listaOperaciones.add(op);
        }
        return listaOperaciones;

    }

    @Override
    public int updateCortebyIdCaja(BigDecimal idCajaFk, BigDecimal idCorteFk) {
        getEjb();
        int variable = ejb.updateCortebyIdCaja(idCajaFk, idCorteFk);
        System.out.println("VARIABLE: " + variable);
        return variable;

    }

    @Override
    public ArrayList<OperacionesCaja> getByIdSucuralAndDate(BigDecimal idSucursal, String fechaInicio, String fechaFin) {
        getEjb();

        ArrayList<OperacionesCaja> lstOperacionesCaja = new ArrayList<OperacionesCaja>();

        List<Object[]> lstObject = ejb.getByIdSucuralAndDate(idSucursal, fechaInicio, fechaFin);

        for (Object[] obj : lstObject) {

            OperacionesCaja operacionesCaja = new OperacionesCaja();
            operacionesCaja.setFecha(obj[0] == null ? null : (Date) obj[0]);
            operacionesCaja.setNombreSucursal(obj[1] == null ? null : obj[1].toString());
            operacionesCaja.setIdTipoOperacionFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            operacionesCaja.setNombreOperacion(obj[3] == null ? null : obj[3].toString());
            operacionesCaja.setIdConceptoFk(obj[4] == null ? null : new BigDecimal(obj[4].toString()));
            operacionesCaja.setNombreConcepto(obj[5] == null ? null : obj[5].toString());
            operacionesCaja.setMonto(obj[6] == null ? new BigDecimal(0) : new BigDecimal(obj[6].toString()));
            operacionesCaja.setComentarios(obj[7] == null ? "" : obj[7].toString());

            lstOperacionesCaja.add(operacionesCaja);
        }

        BigDecimal totalOperaciones = new BigDecimal(0);
        ArrayList<OperacionesCaja> lstOperacion = new ArrayList<OperacionesCaja>();

        //Llena la lista de operaciones o lista principal
        for (OperacionesCaja opc : lstOperacionesCaja) {
            boolean exist = false;
            OperacionesCaja opcTemp = new OperacionesCaja();
            
            opcTemp.setFecha(opc.getFecha());
            opcTemp.setNombreSucursal(opc.getNombreSucursal());
            opcTemp.setIdTipoOperacionFk(opc.getIdTipoOperacionFk());
            opcTemp.setNombreOperacion(opc.getNombreOperacion());
            opcTemp.setIdConceptoFk(opc.getIdConceptoFk());
            opcTemp.setNombreConcepto(opc.getNombreConcepto());
            opcTemp.setMonto(new BigDecimal(0));
            opcTemp.setComentarios(opc.getComentarios());

            for (OperacionesCaja operacion : lstOperacion) {

                if (operacion.getIdTipoOperacionFk().equals(opc.getIdTipoOperacionFk())) {
                    exist = true;
                } else {

                    exist = false;
                }
            }
            if (!exist) {
                lstOperacion.add(opcTemp);
            }

        }

        //Llena la lista de conceptos o lista secundaria
        ArrayList<OperacionesCaja> lstConceptos = new ArrayList<OperacionesCaja>();
        for (OperacionesCaja opc : lstOperacionesCaja) {
            boolean exist = false;
            
             OperacionesCaja opcTemp = new OperacionesCaja();
            
            opcTemp.setFecha(opc.getFecha());
            opcTemp.setNombreSucursal(opc.getNombreSucursal());
            opcTemp.setIdTipoOperacionFk(opc.getIdTipoOperacionFk());
            opcTemp.setNombreOperacion(opc.getNombreOperacion());
            opcTemp.setIdConceptoFk(opc.getIdConceptoFk());
            opcTemp.setNombreConcepto(opc.getNombreConcepto());
            opcTemp.setMonto(new BigDecimal(0));
            opcTemp.setComentarios(opc.getComentarios());

            for (OperacionesCaja operacion : lstConceptos) {

                if (operacion.getIdTipoOperacionFk().equals(opc.getIdTipoOperacionFk()) && operacion.getIdConceptoFk().equals(opc.getIdConceptoFk())) {
                    exist = true;
                } else {
                    exist = false;
                }
            }
            if (!exist) {
                lstConceptos.add(opcTemp);
            }

        }

        //Juntamos las listas de conceptos con la de operaciones de caja
        for (OperacionesCaja opc : lstOperacionesCaja) {
            for (OperacionesCaja operacion : lstConceptos) {

                if (operacion.getIdTipoOperacionFk().equals(opc.getIdTipoOperacionFk()) && operacion.getIdConceptoFk().equals(opc.getIdConceptoFk())) {
                    if (operacion.getLstOperacionesCajas() == null) {
                        operacion.setLstOperacionesCajas(new ArrayList<OperacionesCaja>());
                    }

                    operacion.getLstOperacionesCajas().add(opc);
                    operacion.setMonto(operacion.getMonto().add(opc.getMonto()));
                }
            }
        }

        //Juntamos las listas de conceptos con la de operaciones 
        for (OperacionesCaja opc : lstConceptos) {
            for (OperacionesCaja operacion : lstOperacion) {

                if (operacion.getIdTipoOperacionFk().equals(opc.getIdTipoOperacionFk())) {
                    if (operacion.getLstOperacionesCajas() == null) {
                        operacion.setLstOperacionesCajas(new ArrayList<OperacionesCaja>());
                    }

                    operacion.getLstOperacionesCajas().add(opc);
                    operacion.setMonto(operacion.getMonto().add(opc.getMonto()));
                }
            }
        }


        return lstOperacion;

    }

}
