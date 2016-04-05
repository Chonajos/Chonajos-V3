package com.web.chon.ejb;

import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.negocio.NegocioBuscaVenta;
import com.web.chon.negocio.NegocioEntradaProductoCentral;
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
@Stateless(mappedName = "ejbEntradaProductoCentral")
public class EjbEntradaProductoCentral implements NegocioEntradaProductoCentral {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int saveEntradaProductoCentral(EntradaMercancia entradaMercancia) {
        try {
            Query query = em.createNativeQuery("INSERT INTO entrada_producto_central (ID_ENTRADA,PRECIO_VENTA,TONELADAS,FECHA) VALUES(S_ENTRADA_PRODUCTO_CENTRAL.NEXTVAL,?,?,SYSDATE)");

            query.setParameter(1, entradaMercancia.getPrecio());
            query.setParameter(2, entradaMercancia.getCantidadToneladas());

            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbEntradaProductoCentral.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}
