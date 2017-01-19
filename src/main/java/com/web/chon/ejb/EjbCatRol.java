package com.web.chon.ejb;

import com.web.chon.dominio.Rol;
import com.web.chon.negocio.NegocioCatRol;
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
@Stateless(mappedName = "ejbCatRol")
public class EjbCatRol implements NegocioCatRol {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public List<Object[]> getAll() {

        try {

            Query query = em.createNativeQuery("SELECT * FROM ROL ORDER BY ID_ROL_PK ");
            List<Object[]> resultList = null;
            resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            Logger.getLogger(EjbCatRol.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getById(int idRol) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ROL WHERE  ID_ROL_PK = ?");
            query.setParameter(1, idRol);

            return query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public int delete(int idRol) {
        try {
            Query query = em.createNativeQuery("DELETE ROL WHERE ID_ROL_PK = ?");
            query.setParameter(1, idRol);

            return query.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int update(Rol rol) {
        try {

            Query query = em.createNativeQuery("UPDATE ROL SET NOMBRE_ROL = ? , DESCRIPCION_ROL = ? WHERE ID_ROL_PK = ?");

            query.setParameter(1, rol.getNombreRol());
            query.setParameter(2, rol.getDescripcionRol());
            query.setParameter(3, rol.getIdRolPk());

            return query.executeUpdate();

        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public int insert(Rol rol) {
        try {
            Query query = em.createNativeQuery("INSERT INTO ROL (ID_ROL_PK,NOMBRE_ROL,DESCRIPCION_ROL) VALUES(S_ROL.NextVal,?,?)");

            query.setParameter(1, rol.getNombreRol());
            query.setParameter(2, rol.getDescripcionRol());

            return query.executeUpdate();

        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public int getNextVal() {
        try {

            Query query = em.createNativeQuery("SELECT S_ROL.NextVal FROM DUAL");

            return Integer.parseInt(query.getSingleResult().toString());

        } catch (Exception e) {
            return 1;
        }
    }

}
