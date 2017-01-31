package com.web.chon.ejb;

import com.web.chon.dominio.ConceptosES;
import com.web.chon.negocio.NegocioConceptos;
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
@Stateless(mappedName = "ejbConceptos")
public class EjbConceptos implements NegocioConceptos {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getConceptosByTipoOperacion(BigDecimal idTipoOperacionFk) {
        Query query = em.createNativeQuery("select * from CONCEPTOS where ID_TIPO_OPERACION_FK = ?");
        query.setParameter(1, idTipoOperacionFk);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getConceptos() {
        Query query = em.createNativeQuery("select cn.ID_CONCEPTOS_PK,cn.ID_TIPO_OPERACION_FK,tiop.NOMBRE as Categoria,cn.NOMBRE as concepto,cn.DESCRIPCION from CONCEPTOS cn\n" +
"inner join TIPOS_OPERACION tiop on tiop.ID_TIPO_OPERACION_PK= cn.ID_TIPO_OPERACION_FK\n" +
"order by tiop.ID_TIPO_OPERACION_PK");
        return query.getResultList();
    }

    @Override
    public int insertConcepto(ConceptosES c) {
       try {

            Query query = em.createNativeQuery("INSERT INTO  CONCEPTOS (ID_CONCEPTOS_PK ,"
                    + "ID_TIPO_OPERACION_FK "
                    + ",NOMBRE ,DESCRIPCION) "
                    + " VALUES(?,?,?,?)");
            query.setParameter(1, c.getIdConceptoPk());
            query.setParameter(2, c.getIdTipoOperacionFk());
            query.setParameter(3, c.getNombre());
            query.setParameter(4, c.getDescripcion());

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbConceptos.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_CONCEPTOS.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public int updateConcepto(ConceptosES c) {
        try {
            
            Query query = em.createNativeQuery("UPDATE CONCEPTOS SET ID_TIPO_OPERACION_FK =? ,NOMBRE=? ,DESCRIPCION=? WHERE ID_CONCEPTOS_PK = ?");
            
            query.setParameter(1, c.getIdTipoOperacionFk());
            query.setParameter(2, c.getNombre());
            query.setParameter(3, c.getDescripcion());
            query.setParameter(4, c.getIdTipoOperacionFk());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbConceptos.class
                    .getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    }

    @Override
    public List<Object[]> getConceptosByIdCategoria(BigDecimal id) {
        
        Query query = em.createNativeQuery("SELECT CN.ID_CONCEPTOS_PK,CN.ID_TIPO_OPERACION_FK,CN.NOMBRE,CN.DESCRIPCION \n" +
"FROM CONCEPTOS CN INNER JOIN TIPOS_OPERACION TIP\n" +
"ON TIP.ID_TIPO_OPERACION_PK=CN.ID_TIPO_OPERACION_FK\n" +
"WHERE TIP.ID_GRUPO_FK=?");
        query.setParameter(1, id);
        return query.getResultList();
    }
}
