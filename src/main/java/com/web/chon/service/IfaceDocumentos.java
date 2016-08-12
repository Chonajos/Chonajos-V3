/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Documento;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Interfaz para el Servicio de Documentos por Cobrar
 * @author JesusAlfredo
 */
public interface IfaceDocumentos {
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
    public Documento getDocumentoByIdDocumentoPk(BigDecimal idDocumento);

    /**
     *
     * @param idAbonoFk
     * @return
     */
    public Documento getDocumentoByIdAbonoFk(BigDecimal idAbonoFk);
    
    /**
     *
     * @param idClienteFk
     * @return
     */
    public ArrayList<Documento> getDocumentosByIdClienteFk(BigDecimal idClienteFk);
    
    /**
     *
     * @param idStatusFk
     * @return
     */
    public ArrayList<Documento> getDocumentosByIdStatusFk(BigDecimal idStatusFk);
    public int getNextVal();
    
    public int updateDocumentoById(Documento dc);
}
