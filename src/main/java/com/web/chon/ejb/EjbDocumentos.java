/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.Documento;
import com.web.chon.negocio.NegocioDocumentos;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author JesusAlfredo
 */
@Stateless(mappedName = "ejbDocumentos")
public class EjbDocumentos implements NegocioDocumentos{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertarDocumento(Documento documento) 
    {
        
        System.out.println("EJBDOCUMENTOS: "+documento.toString());
         try {
            Query query = em.createNativeQuery("INSERT INTO  "
                    + "DOCUMENTOS_COBRAR (ID_DOCUMENTO_PK,ID_TIPO_DOCUMENTO,ID_ABONO_FK,"
                    + "ID_CLIENTE_FK, ID_STATUS_FK, COMENTARIO,MONTO) "
                    + " VALUES(?,?,?,?,?,?,?)");
            query.setParameter(1, documento.getIdDocumentoPk());
            query.setParameter(2, documento.getIdTipoDocumento());
            query.setParameter(3, documento.getIdAbonoFk());
            query.setParameter(4, documento.getIdClienteFk());
            query.setParameter(5, documento.getIdStatusFk());
            query.setParameter(6, documento.getComentario());
            query.setParameter(7, documento.getMonto());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getDocumentoByIdDocumentoPk(BigDecimal idDocumento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getDocumentoByIdAbonoFk(BigDecimal idAbonoFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getDocumentosByIdClienteFk(BigDecimal idClienteFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object[]> getDocumentosByIdStatusFk(BigDecimal idStatusFk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  @Override
    public int nextVal() {
        try {
            Query query = em.createNativeQuery("select S_DOCUMENTOS_COBRAR.nextval from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            return 0;
        }
    }

    @Override
    public int updateDocumento(Documento documento) {
        System.out.println("EJBDOCUMENTOS: "+documento.toString());
         try {
            Query query = em.createNativeQuery("UPDATE  "
                    + "DOCUMENTOS_COBRAR SET ID_STATUS_FK = ? "
                    + "  WHERE ID_DOCUMENTO_PK = ?");
           
            query.setParameter(1, documento.getIdStatusFk());
            query.setParameter(2, documento.getIdDocumentoPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
   
}
