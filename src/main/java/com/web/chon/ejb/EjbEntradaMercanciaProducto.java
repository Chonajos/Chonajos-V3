/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.negocio.NegocioEntradaMercanciaProducto;
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
@Stateless(mappedName = "ejbEntradaMercanciaProducto")
public class EjbEntradaMercanciaProducto implements NegocioEntradaMercanciaProducto {
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertEntradaMercanciaProducto(EntradaMercanciaProducto producto) 
    {
        System.out.println("EJB_INSERTA_ENTRADAMERCANCIA Producto");
        try {
            System.out.println("Entrada_Porducto: " + producto);
            Query query = em.createNativeQuery("INSERT INTO ENTRADAMERCANCIAPRODUCTO (ID_EMP_PK,ID_EM_FK,ID_SUBPRODUCTO_FK,ID_TIPO_EMPAQUE_FK,KILOS_TOTALES,CANTIDAD_EMPACAQUE,COMENTARIOS,ID_TIPO_COMPRA_FK,ID_BODEGA_FK)VALUES (S_EntradaMercanciaProducto.NextVal,?,?,?,?,?,?,?,?)");
            query.setParameter(1, producto.getIdEmFK());
            query.setParameter(2, producto.getIdSubProductoFK());
            query.setParameter(3, producto.getIdTipoEmpaqueFK());
            query.setParameter(4, producto.getKilosTotalesProducto());
            query.setParameter(5, producto.getCantidadPaquetes());
            query.setParameter(6, producto.getComentarios());
            query.setParameter(7, producto.getIdTipo());
            query.setParameter(8, producto.getIdBodegaFK());
            
            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbCatSucursales.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }
    
}
