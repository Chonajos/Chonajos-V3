/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.EntradaMenudeoProducto;
import com.web.chon.negocio.NegocioMenudeoProducto;
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
@Stateless(mappedName = "ejbEntradaMenudeoProducto")
public class EjbEntradaMenudeoProducto implements NegocioMenudeoProducto{
    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertEntradaMenudeoProducto(EntradaMenudeoProducto entrada) {
        System.out.println("::::::::::::::::::::::::::::::::");
        System.out.println(entrada.toString());
        try {
     
            Query query = em.createNativeQuery("INSERT INTO ENTRADAMENUDEOPRODUCTO (ID_EMMP_PK,ID_EMM_FK,ID_SUBPRODUCTO_FK,ID_TIPO_EMPAQUE_FK,KILOS_TOTALES,CANTIDAD_EMPACAQUE,COMENTARIOS)VALUES (?,?,?,?,?,?,?)");
            query.setParameter(1, entrada.getIdEmmpPk());
            query.setParameter(2, entrada.getIdEmmFk());
            query.setParameter(3, entrada.getIdSubproductoFk());
            query.setParameter(4, entrada.getIdtipoEmpaqueFk());
            query.setParameter(5, entrada.getKilosTotales());
            query.setParameter(6, entrada.getCantidadEmpaque());
            query.setParameter(7, entrada.getComentarios());
           
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbEntradaMenudeo.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int getNextVal() 
    {
        Query query = em.createNativeQuery("SELECT S_EntradaMenudeoProducto.nextVal FROM DUAL");
        return Integer.parseInt(query.getSingleResult().toString());
    
    }

    @Override
    public List<Object[]> getEntradaMenudeoProductoByIdEM(BigDecimal idEntradaMenudeo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
