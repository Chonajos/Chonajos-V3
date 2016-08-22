/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.Documento;
import com.web.chon.util.TiempoUtil;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author JesusAlfredo
 */
@Remote
public interface NegocioDocumentos {
    /**
     * Inserta una Documento por Cobrar
     * @param documento
     * @return 
     */
    public int insertarDocumento(Documento documento);
     /**
     * Busca un Documento por Cobrar por IdDocumentoPk
     * @param idDocumento
     * @return 
     */
    public List<Object[]> getDocumentoByIdDocumentoPk(BigDecimal idDocumento);

    /**
     *
     * @param idAbonoFk
     * @return
     */
    public List<Object[]> getDocumentoByIdAbonoFk(BigDecimal idAbonoFk);
    
    /**
     *
     * @param idClienteFk
     * @return
     */
    public List<Object[]> getDocumentosByIdClienteFk(BigDecimal idClienteFk);
    
    /**
     *
     * @param idStatusFk
     * @return
     */
    public List<Object[]> getDocumentosByIdStatusFk(BigDecimal idStatusFk);

    /**
     *
     * @return
     */
    public int nextVal();
    
    
    public int updateDocumento(Documento dc);
    
    public List<Object[]> getDocumentos(String fechaInicio, String fechaFin,BigDecimal idSucursal,BigDecimal idClienteFk,BigDecimal filtro,BigDecimal filtroStatus,BigDecimal filtroFecha);

    public int cambiarFormaPago(Documento d);
    
}
