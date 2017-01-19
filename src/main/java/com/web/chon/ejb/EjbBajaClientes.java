
package com.web.chon.ejb;

import com.web.chon.dominio.BajaClientes;
import com.web.chon.negocio.NegocioBajaClientes;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author freddy
 */
@Stateless(mappedName = "ejbBajaClientes")
public class EjbBajaClientes implements NegocioBajaClientes
{
     @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertCliente(BajaClientes clie)
    {
       System.out.println("EJB_INSERTA_BAJA_CLIENTE");
        try {
                System.out.println("baja_insert : " + clie);
                Query query = em.createNativeQuery("INSERT INTO BAJA_CLIENTES (ID_BC_PK,MOTIVO_FK,FECHA_BAJA,COMENTARIOS) VALUES(?,?,sysdate,?)");
                query.setParameter(1, clie.getId_baja_cliente());
                query.setParameter(2, clie.getId_motivo_fk());
                query.setParameter(3, clie.getComentarios());//activo
                return query.executeUpdate();
        } catch (Exception ex) 
        {
            Logger.getLogger(EjbBajaClientes.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            return 0;
        }
        
    }

    @Override
    public int deleteClienteBajas(BigDecimal idCliente) 
    {
       try {
            Query query = em.createNativeQuery("delete BAJA_CLIENTES where ID_BC_PK = ?");
            query.setParameter(1, idCliente);

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbBajaClientes.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    
    
    }
    
}
