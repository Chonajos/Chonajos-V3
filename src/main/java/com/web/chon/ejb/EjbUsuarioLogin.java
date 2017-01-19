package com.web.chon.ejb;

import com.web.chon.dominio.AnalisisMercado;
import com.web.chon.negocio.NegocioBuscaVenta;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.web.chon.negocio.NegocioUsuarioLogin;

/**
 *
 * @author Juan
 */
@Stateless(mappedName = "ejbUsuarioLogin")
public class EjbUsuarioLogin implements NegocioUsuarioLogin {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getMenuByUser(String perfilId) {

        try {
            Query query = em.createNativeQuery(" SELECT men.id_menu, men.descripcion, men.tipo, men.nivel, men.url_sistema FROM menu men, acces_menu acces  "
                    + "WHERE acces.ID_ROL_FK = ? AND acces.id_menu_fk = men.id_menu "
                    + "ORDER BY men.NIVEL");

            query.setParameter(1, perfilId);
            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbUsuarioLogin.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getUser(String idUser) {
        try {
            Query query = em.createNativeQuery(" SELECT USU.CLAVE_USUARIO, "
                    + " USU.NOMBRE_USUARIO, "
                    + " USU.APATERNO_USUARIO, "
                    + " USU.AMATERNO_USUARIO, "
                    + " USU.CONTRASENA_USUARIO, "
                    + " USU.FECHA_ALTA_USUARIO, "
                    + " USU.ID_ROL_FK, "
                    + " USU.ID_SUCURSAL_FK, "
                    + " ROL.NOMBRE_ROL, "
                    + " USU.ID_USUARIO_PK, "
                    + " SUC.NOMBRE_SUCURSAL, "
                    + " SUC.TELEFONO_SUCURSAL "  
                    + " FROM USUARIO USU "
                    + " INNER JOIN ROL ROL ON USU.ID_ROL_FK =ROL.ID_ROL_PK "
                    + " INNER JOIN SUCURSAL SUC ON USU.ID_SUCURSAL_FK = SUC.ID_SUCURSAL_PK"
                    + " WHERE CLAVE_USUARIO = ? AND STATUS = 1");

            query.setParameter(1, idUser);
            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbUsuarioLogin.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
