package com.web.chon.ejb;

import com.web.chon.dominio.RegistroEntradaSalida;
import com.web.chon.negocio.NegocioRegEntSal;
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
 * @author Juan de la Cruz
 */
@Stateless(mappedName = "ejbRegEntSal")
public class EjbRegEntSal implements NegocioRegEntSal {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getUsuarioByIdUsuario(BigDecimal idUsuarioFK, String fechaHoy) {
        try {

            Query query = em.createNativeQuery("select reg.*,usu.NOMBRE_USUARIO,usu.APATERNO_USUARIO,usu.AMATERNO_USUARIO from REGISTROENTRADA reg inner join USUARIO usu\n"
                    + "on usu.ID_USUARIO_PK = reg.ID_USUARIO_FK where ID_USUARIO_FK = ? and TO_DATE(TO_CHAR(reg.FECHAENTRADA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaHoy + "' AND '" + fechaHoy + "'");
            query.setParameter(1, idUsuarioFK);

            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbRegEntSal.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int updateSalidabyIdReg(RegistroEntradaSalida data) {
        Query query = em.createNativeQuery("UPDATE REGISTROENTRADA set FECHASALIDA =sysdate ,LATITUDSALIDA=?,LONGITUDSALIDA=? WHERE ID_REGENT_PK=?");
        query.setParameter(1, data.getLatitudSalida());
        query.setParameter(2, data.getLongitudSalida());
        query.setParameter(3, data.getIdRegEntSalPk());
        return query.executeUpdate();
    }

    @Override
    public int insertEntradabyIdReg(RegistroEntradaSalida data) {
        Query query = em.createNativeQuery("INSERT INTO REGISTROENTRADA(ID_REGENT_PK,FECHAENTRADA,LATITUDENTRADA,LONGITUDENTRADA,ID_USUARIO_FK,ID_SUCURSAL_FK,LATITUDSALIDA,LONGITUDSALIDA) VALUES(?,sysdate,?,?,?,?,?,?)");
        query.setParameter(1, data.getIdRegEntSalPk());
        query.setParameter(2, data.getLatitudEntrada());
        query.setParameter(3, data.getLongitudEntrada());
        query.setParameter(4, data.getIdUsuarioFk());
        query.setParameter(5, data.getIdSucursalFk());
        query.setParameter(6, data.getLatitudSalida());
        query.setParameter(7, data.getLongitudSalida());

        return query.executeUpdate();

    }

    @Override
    public int getNextVal() {
        Query query = em.createNativeQuery("SELECT S_REGISTROENTRADA.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());

    }

    @Override
    public List<Object[]> getRegistros(BigDecimal idUsuarioFK, String fechaInicio, String fechaFin) {
        try {
            
            Query query = em.createNativeQuery("select days.fecha,RE.FECHAENTRADA,to_char(nvl(RE.FECHAENTRADA, to_date(null)),'hh24:mi') HORA_ENTRADA,RE.FECHASALIDA,to_char(nvl(RE.FECHASALIDA, to_date(null)),'hh24:mi') AS HORA_SALIDA, "
                    + "RE.LATITUDENTRADA,RE.LATITUDSALIDA,RE.LONGITUDENTRADA,RE.LONGITUDSALIDA,to_char(nvl(HU.HORA_ENTRADA, to_date(null)),'hh24:mi') AS HORARIO_ENTRADA,to_char(nvl(HU.HORA_SALIDA, to_date(null)),'hh24:mi') AS HORARIO_SALIDA "
                    + ",DDU.DIA FROM(SELECT TRUNC (to_date(?, 'DD/MM/YYYY'), 'DD') + r fecha FROM (SELECT ROWNUM - 1 r  "
                    + "FROM all_objects WHERE ROWNUM <= to_char(last_day(trunc(to_date(?,'DD/MM/YYYY'))), 'DD')) WHERE "
                    + "TRUNC (TRUNC (to_date(?,'DD/MM/YYYY'), 'yyyy') + r, 'yyyy') = TRUNC (to_date(?,'DD/MM/YYYY'), 'yyyy')) days "
                    + "LEFT JOIN REGISTROENTRADA RE ON  to_date(RE.FECHAENTRADA,'DD/MM/YYYY')=to_date(DAYS.fecha,'DD/MM/YYYY')  AND RE.ID_USUARIO_FK =? "
                    + "LEFT JOIN HORARIO_USUARIO HU ON HU.ID_USUARIO_FK = ? AND ( TO_DATE(TO_CHAR(days.fecha,'dd/mm/yyyy'),'dd/mm/yyyy')  BETWEEN TO_DATE(TO_CHAR(HU.FECHA_INICIO,'dd/mm/yyyy'),'dd/mm/yyyy') AND TO_DATE(TO_CHAR(HU.FECHA_FIN,'dd/mm/yyyy'),'dd/mm/yyyy') ) "
                    + "LEFT JOIN DIA_DESCANSO_USUARIO DDU ON DDU.ID_USUARIO_FK = ? AND ( TO_DATE(TO_CHAR(days.fecha,'dd/mm/yyyy'),'dd/mm/yyyy')  BETWEEN TO_DATE(TO_CHAR(DDU.FECHA_INICIO,'dd/mm/yyyy'),'dd/mm/yyyy') AND TO_DATE(TO_CHAR(DDU.FECHA_FIN,'dd/mm/yyyy'),'dd/mm/yyyy') ) "
                    + "ORDER BY DAYS.fecha");

            query.setParameter(1, fechaInicio);
            query.setParameter(2, fechaInicio);
            query.setParameter(3, fechaInicio);
            query.setParameter(4, fechaInicio);
            query.setParameter(5, idUsuarioFK);
            query.setParameter(6, idUsuarioFK);
            query.setParameter(7, idUsuarioFK);

            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbRegEntSal.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getALL(String fechaInicio, String fechaFin) {
        try {

            Query query = em.createNativeQuery("select reg.*,usu.NOMBRE_USUARIO,usu.APATERNO_USUARIO,usu.AMATERNO_USUARIO from REGISTROENTRADA reg inner join USUARIO usu\n"
                    + "on usu.ID_USUARIO_PK = reg.ID_USUARIO_FK where TO_DATE(TO_CHAR(reg.FECHAENTRADA,'dd/mm/yyyy'),'dd/mm/yyyy') BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "'");
            return query.getResultList();

        } catch (Exception ex) {
            Logger.getLogger(EjbRegEntSal.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
