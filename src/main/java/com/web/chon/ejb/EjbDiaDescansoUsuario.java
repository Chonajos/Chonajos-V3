package com.web.chon.ejb;

import com.web.chon.dominio.DiaDescansoUsuario;
import com.web.chon.negocio.NegocioDiaDescansoUsuario;
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
 * @author Juan
 */
@Stateless(mappedName = "ejbDiaDescansoUsuario")
public class EjbDiaDescansoUsuario implements NegocioDiaDescansoUsuario {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(DiaDescansoUsuario diaDescansoUsuario) {
        try {

            System.out.println("diasdescanso "+diaDescansoUsuario.toString());
            Query query = em.createNativeQuery("INSERT INTO DIA_DESCANSO_USUARIO (ID_DIA_DESCANSO_PK,ID_USUARIO_FK,FECHA_INICIO,FECHA_FIN,DIA) VALUES(S_DIA_DESCANSO_USUARIO.NextVal,?,?,?,?)");

            query.setParameter(1, diaDescansoUsuario.getIdUsuario());
            query.setParameter(2, diaDescansoUsuario.getFechaInicio());
            query.setParameter(3, diaDescansoUsuario.getFechaFin());
            query.setParameter(4, diaDescansoUsuario.getDia());

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbDiaDescansoUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int update(DiaDescansoUsuario diaDescansoUsuario) {
        try {
            Query query = em.createNativeQuery("UPDATE DIA_DESCANSO_USUARIO SET FECHA_INICIO = ?,FECHA_FIN = ?,DIA = ? WHERE ID_DIA_DESCANSO_PK = ?");

            query.setParameter(1, diaDescansoUsuario.getFechaInicio());
            query.setParameter(2, diaDescansoUsuario.getFechaFin());
            query.setParameter(3, diaDescansoUsuario.getDia());
            query.setParameter(4, diaDescansoUsuario.getIdDiaDescansoUsuario());

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbDiaDescansoUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getByIdUsuario(BigDecimal id) {
        try {

            Query query = em.createNativeQuery("SELECT ID_DIA_DESCANSO_PK,ID_USUARIO_FK,FECHA_INICIO,FECHA_FIN,DIA FROM DIA_DESCANSO_USUARIO WHERE ID_USUARIO_FK = ?");

            query.setParameter(1,id);

            return query.getResultList();
            
        }catch (Exception ex) {
            Logger.getLogger(EjbDiaDescansoUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
