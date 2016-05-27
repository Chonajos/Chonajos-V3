/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.negocio.NegocioEntradaMercanciaProducto;
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
 * @author freddy
 */
@Stateless(mappedName = "ejbEntradaMercanciaProducto")
public class EjbEntradaMercanciaProducto implements NegocioEntradaMercanciaProducto {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertEntradaMercanciaProducto(EntradaMercanciaProducto producto) {
        System.out.println("EJB_INSERTA_ENTRADAMERCANCIA Producto");
        try {
            System.out.println("Entrada_Porducto: " + producto);
            Query query = em.createNativeQuery("INSERT INTO ENTRADAMERCANCIAPRODUCTO (ID_EM_FK,ID_SUBPRODUCTO_FK,ID_TIPO_EMPAQUE_FK,KILOS_TOTALES,CANTIDAD_EMPACAQUE,COMENTARIOS,ID_BODEGA_FK,ID_TIPO_CONVENIO_FK,CONVENIO,KILOSPROMPROD,ID_EMP_PK)VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            query.setParameter(1, producto.getIdEmFK());
            query.setParameter(2, producto.getIdSubProductoFK());
            query.setParameter(3, producto.getIdTipoEmpaqueFK());
            query.setParameter(4, producto.getKilosTotalesProducto());
            query.setParameter(5, producto.getCantidadPaquetes());
            query.setParameter(6, producto.getComentarios());
            query.setParameter(7, producto.getIdBodegaFK());
            query.setParameter(8, producto.getIdTipoConvenio());
            query.setParameter(9, producto.getPrecio());
            query.setParameter(10, producto.getKilospromprod());
            query.setParameter(11, producto.getIdEmpPK());
            

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbEntradaMercanciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public List<Object[]> getEntradaProductoByIdEM(BigDecimal idEntradaProducto) {

        Query query = em.createNativeQuery("SELECT EMP.*, SUB.NOMBRE_SUBPRODUCTO, TE.NOMBRE_EMPAQUE, BD.NOMBRE,TC.TIPO FROM ENTRADAMERCANCIAPRODUCTO EMP "
                + "LEFT JOIN SUBPRODUCTO SUB ON SUB.ID_SUBPRODUCTO_PK = EMP.ID_SUBPRODUCTO_FK "
                + "LEFT JOIN TIPO_EMPAQUE TE ON TE.ID_TIPO_EMPAQUE_PK = EMP.ID_TIPO_EMPAQUE_FK "
                + "LEFT JOIN BODEGA BD ON BD.ID_BD_PK = EMP.ID_BODEGA_FK "
                + "LEFT JOIN TIPO_CONVENIO TC ON TC.ID_TC_PK = EMP.ID_TIPO_CONVENIO_FK "
                + "WHERE ID_EM_FK = ?");

        query.setParameter(1, idEntradaProducto);

        return query.getResultList();

    }

    @Override
    public int getNextVal() {

        Query query = em.createNativeQuery("SELECT S_ENTRADAMERCANCIAPRODUCTO.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}
