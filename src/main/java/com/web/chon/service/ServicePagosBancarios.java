/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.PagosBancarios;
import com.web.chon.negocio.NegocioPagosBancarios;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author freddy
 */
@Service
public class ServicePagosBancarios implements IfacePagosBancarios {

    NegocioPagosBancarios ejb;

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioPagosBancarios) Utilidades.getEJBRemote("ejbPagoBancario", NegocioPagosBancarios.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServicePagosBancarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int insertaPagoBancario(PagosBancarios pb) {
        getEjb();
        return ejb.insertaPagoBancario(pb);
    }

    @Override
    public int updatePagoBancario(PagosBancarios pb) {
        getEjb();
        return ejb.updatePagoBancario(pb);

    }

    @Override
    public ArrayList<PagosBancarios> getPagosPendientes(BigDecimal idSucursalFk) {
        getEjb();
        BigDecimal i = new BigDecimal(1);
        ArrayList<PagosBancarios> listaOperaciones = new ArrayList<PagosBancarios>();
        List<Object[]> lstObject = ejb.getPagosPendientes(idSucursalFk);
        for (Object[] obj : lstObject) {
            PagosBancarios op = new PagosBancarios();
            op.setIdTransBancariasPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            op.setIdCajaFk(obj[1] == null ? null : new BigDecimal(obj[1].toString()));
            op.setIdConceptoFk(obj[2] == null ? null : new BigDecimal(obj[2].toString()));
            op.setIdTipoFk(obj[3] == null ? null : new BigDecimal(obj[3].toString()));
            op.setComentarios(obj[4] == null ? null : obj[4].toString());
            op.setIdUserFk(obj[5] == null ? null : new BigDecimal(obj[5].toString()));
            op.setMonto(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
            op.setFecha(obj[7] == null ? null : (Date) obj[7]);
            op.setIdStatusFk(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            op.setFechaTranferencia((obj[9] == null ? null : (Date) obj[9]));
            op.setFolioElectronico(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            op.setFechaDeposito((obj[11] == null ? null : (Date) obj[11]));
            op.setIdCuentaFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
            op.setConcepto(obj[13] == null ? null : obj[13].toString());
            op.setReferencia(obj[14] == null ? null : obj[14].toString());
            op.setIdOperacionCajaFk(obj[15] == null ? null : new BigDecimal(obj[15].toString()));
            op.setIdTipoTD(obj[16] == null ? null : new BigDecimal(obj[16].toString()));
            op.setIdLlaveFk(obj[17] == null ? null : new BigDecimal(obj[17].toString()));
            
            op.setNombreCaja(obj[18] == null ? null : obj[18].toString());
            op.setNombreConcepto(obj[19] == null ? "" : obj[19].toString());
            op.setNombreTipoAbono(obj[20] == null ? "" : obj[20].toString());
            op.setNombreUsuario(obj[21] == null ? null : obj[21].toString());
            op.setNombreBanco(obj[22] == null ? null : obj[22].toString());
            op.setNumero(i);
            i = i.add(new BigDecimal(1), MathContext.UNLIMITED);

            if (op.getIdStatusFk().intValue() == 1) {
                op.setNombreStatus("APLICADO");
            } else {
                op.setNombreStatus("PENDIENTE");
            }

            listaOperaciones.add(op);
        }
        return listaOperaciones;

    }

    @Override
    public int getNextVal() {
        getEjb();
        return ejb.getNextVal();
    }

    @Override
    public PagosBancarios getByIdTipoLlave(BigDecimal idTipo, BigDecimal idLLave) {
        getEjb();
        List<Object[]> Object = ejb.getByIdTipoLlave(idTipo, idLLave);
        PagosBancarios p = new PagosBancarios();
        for (Object[] obj : Object) {
            p.setIdTransBancariasPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            p.setIdCajaFk(obj[1] == null ? null : new BigDecimal(obj[0].toString()));
            p.setIdConceptoFk(obj[2] == null ? null : new BigDecimal(obj[0].toString()));
            p.setIdTipoFk(obj[3] == null ? null : new BigDecimal(obj[0].toString()));
            p.setComentarios(obj[4] == null ? null : obj[20].toString());
            p.setIdUserFk(obj[5] == null ? null : new BigDecimal(obj[0].toString()));
            p.setMonto(obj[6] == null ? null : new BigDecimal(obj[0].toString()));
            p.setFecha((obj[7] == null ? null : (Date) obj[9]));
            p.setIdStatusFk(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            p.setFechaTranferencia((obj[9] == null ? null : (Date) obj[9]));
            p.setFolioElectronico(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
            p.setFechaDeposito((obj[11] == null ? null : (Date) obj[11]));
            p.setIdCuentaFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
            p.setConcepto(obj[13] == null ? null : obj[13].toString());
            p.setReferencia(obj[14] == null ? null : obj[14].toString());
            p.setIdOperacionCajaFk(obj[15] == null ? null : new BigDecimal(obj[15].toString()));
            p.setIdTipoTD(obj[16] == null ? null : new BigDecimal(obj[16].toString()));
            p.setIdLlaveFk(obj[17] == null ? null : new BigDecimal(obj[17].toString()));
        }
        return p;
    }

}
