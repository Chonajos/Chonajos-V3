package com.web.chon.ejb;

import com.web.chon.dominio.HorarioUsuario;
import com.web.chon.negocio.NegocioHorarioUsuario;
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

@Stateless(mappedName = "ejbHorarioUsuario")
public class EjbHorarioUsuario implements NegocioHorarioUsuario {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insert(HorarioUsuario horarioUsuario) {

        try {
            
            System.out.println("horarioUsuario "+horarioUsuario.toString());
            Query query = em.createNativeQuery("INSERT INTO HORARIO_USUARIO (ID_HORARIO_PK,ID_USUARIO_FK,HORA_ENTRADA,HORA_SALIDA,FECHA_INICIO,FECHA_FIN) VALUES(S_HORARIO_USUARIO.NextVal,?,?,?,?,?)");

            query.setParameter(1, horarioUsuario.getIdUsuario());
            query.setParameter(2, horarioUsuario.getHoraEntrada());
            query.setParameter(3, horarioUsuario.getHoraSalida());
            query.setParameter(4, horarioUsuario.getFechaInicio());
            query.setParameter(5, horarioUsuario.getFechaFin());

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbHorarioUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int update(HorarioUsuario horarioUsuario) {
        try {
            Query query = em.createNativeQuery("UPDATE HORARIO_USUARIO SET HORA_ENTRADA = ?,HORA_SALIDA = ?,FECHA_INICIO = ?,FECHA_FIN = ? WHERE ID_HORARIO_PK = ?");

            query.setParameter(1, horarioUsuario.getHoraEntrada());
            query.setParameter(2, horarioUsuario.getHoraSalida());
            query.setParameter(3, horarioUsuario.getFechaInicio());
            query.setParameter(4, horarioUsuario.getFechaFin());
            query.setParameter(5, horarioUsuario.getIdHorarioUsuario());

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbHorarioUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

       @Override
    public List<Object[]> getByIdUsuario(BigDecimal id) {
        try {

            Query query = em.createNativeQuery("SELECT ID_HORARIO_PK,ID_USUARIO_FK,HORA_ENTRADA,HORA_SALIDA,FECHA_INICIO,FECHA_FIN FROM HORARIO_USUARIO WHERE ID_USUARIO_FK = ?");

            query.setParameter(1,id);

            return query.getResultList();
            
        }catch (Exception ex) {
            Logger.getLogger(EjbDiaDescansoUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
