/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.ejb;

import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.negocio.NegocioExistenciaProducto;
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
@Stateless(mappedName = "ejbExistenciaProducto")
public class EjbExistenciaProducto implements NegocioExistenciaProducto {

    @PersistenceContext(unitName = "persistenceJR")
    EntityManager em;

    @Override
    public int insertaExistencia(ExistenciaProducto e) {

        System.out.println("Entrada_Porducto Existencia Nuevo=============: " + e.toString());

        try {

            Query query = em.createNativeQuery("INSERT INTO EXISTENCIA_PRODUCTO (ID_EXP_PK,ID_EM_FK,ID_SUBPRODUCTO_FK,ID_TIPO_EMPAQUE_FK,KILOS_TOTALES,CANTIDAD_EMPACAQUE,COMENTARIOS,ID_BODEGA_FK,ID_TIPO_CONVENIO_FK,CONVENIO,KILOSPROMPROD,ID_SUCURSAL_FK,ID_EMP_FK)VALUES (S_EXISTENCIA_PRODUCTO.NextVal,?,?,?,?,?,?,?,?,?,?,?,?)");
            query.setParameter(1, e.getIdEmFK());
            query.setParameter(2, e.getIdSubProductoFK());
            query.setParameter(3, e.getIdTipoEmpaqueFK());
            query.setParameter(4, e.getKilosTotalesProducto());
            query.setParameter(5, e.getCantidadPaquetes());
            query.setParameter(6, e.getComentarios());
            query.setParameter(7, e.getIdBodegaFK());
            query.setParameter(8, e.getIdTipoConvenio());
            query.setParameter(9, e.getPrecio());
            query.setParameter(10, e.getKilospromprod());
            query.setParameter(11, e.getIdSucursal());
            query.setParameter(12, e.getIdEntradaMercanciaProductoFK());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getExistenciaById(BigDecimal idExistencia) {
        try {

            Query query = em.createNativeQuery("select * from existencia_producto where ID_EXP_PK = '" + idExistencia + "'");
            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public List<Object[]> getExistenciaProductoId(BigDecimal idSucursal, String idSubproductoFk, BigDecimal idTipoEmpaqueFk, BigDecimal idBodegaFk, BigDecimal idProvedorFk) {
        try {

            Query query = em.createNativeQuery("select * from existencia_producto where ID_SUCURSAL_FK = '" + idSucursal + "' and ID_SUBPRODUCTO_FK='" + idSubproductoFk + "' and  ID_TIPO_EMPAQUE='" + idTipoEmpaqueFk + "' and ID_BODEGA_FK='" + idBodegaFk + "'and ID_PROVEDOR_FK='" + idProvedorFk + "'");
            System.out.println(query);
            return query.getResultList();
        } catch (Exception ex) {
            System.out.println("Encontro null ejb");
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int updateExistenciaProducto(ExistenciaProducto e) {

        System.out.println("EJBUPDATEPRODUCTO:--------------------"+e.toString());
        try {

            Query query = em.createNativeQuery("update EXISTENCIA_PRODUCTO SET CANTIDAD_EMPACAQUE=?, KILOS_TOTALES=? WHERE ID_EXP_PK=?");
            query.setParameter(1, e.getCantidadPaquetes());
            query.setParameter(2, e.getKilosTotalesProducto());
            query.setParameter(3, e.getIdExistenciaProductoPk());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);

            return 0;
        }
    }

    @Override
    public List<Object[]> getExistencias(BigDecimal idSucursal, BigDecimal idBodega, BigDecimal idProvedor, String idProducto, BigDecimal idEmpaque, BigDecimal idConvenio, BigDecimal idEmpPK) {

        try {

            Query query;
            int cont = 0;
            StringBuffer cadena = new StringBuffer("select ex.ID_EXP_PK,em.ID_EM_PK,em.IDENTIFICADOR,subp.NOMBRE_SUBPRODUCTO, te.NOMBRE_EMPAQUE,\n" +
"  ex.CANTIDAD_EMPACAQUE,ex.KILOS_TOTALES,tc.DESCRIPCION_TIPO,\n" +
"  prove.nombre_provedor ||' '|| prove.A_PATERNO_PROVE || ' ' || prove.A_MATERNO_PROVE as nombreProvedor,\n" +
"  sucu.NOMBRE_SUCURSAL,bod.NOMBRE, ex.PRECIO_MINIMO, ex.PRECIO_VENTA, ex.PRECIO_MAXIMO,\n" +
"  ex.ESTATUS_BLOQUEO,ex.ID_SUBPRODUCTO_FK,ex.ID_TIPO_EMPAQUE_FK,bod.ID_BD_PK,ex.CONVENIO,em.CARROSUCURSAL\n" +
" from EXISTENCIA_PRODUCTO ex\n" +
" join ENTRADAMERCANCIAPRODUCTO emp\n" +
" on emp.ID_EMP_PK = ex.ID_EMP_FK\n" +
"join ENTRADAMERCANCIA em\n" +
"on em.ID_EM_PK = emp.ID_EM_FK\n" +
"join SUBPRODUCTO subp\n" +
"  on subp.ID_SUBPRODUCTO_PK = ex.ID_SUBPRODUCTO_FK\n" +
"  join TIPO_EMPAQUE te\n" +
"  on te.ID_TIPO_EMPAQUE_PK = ex.ID_TIPO_EMPAQUE_FK\n" +
"  join BODEGA bod\n" +
"  on bod.ID_BD_PK = ex.ID_BODEGA_FK\n" +
"join TIPO_CONVENIO tc\n" +
" on tc.ID_TC_PK = ex.ID_TIPO_CONVENIO_FK\n" +
"join SUCURSAL sucu\n" +
"on sucu.ID_SUCURSAL_PK = ex.ID_SUCURSAL_FK\n" +
"join provedores prove\n" +
"on prove.id_provedor_pk = em.id_provedor_fk");
            if (idEmpPK == null) {
                BigDecimal cero = new BigDecimal(0);

                if (idSucursal != null && idSucursal != cero) {
                    if (cont == 0) {
                        cadena.append(" WHERE ");
                    } else {
                        cadena.append(" AND ");
                    }
                    cadena.append("ex.ID_SUCURSAL_FK = '" + idSucursal + "' ");
                    cont++;

                }
                if (idBodega != null && idBodega != cero) {
                    if (cont == 0) {
                        cadena.append(" WHERE ");
                    } else {
                        cadena.append(" AND ");
                    }
                    cadena.append("ex.ID_BODEGA_FK = '" + idBodega + "' ");
                    cont++;
                }
                if (idProvedor != null && idProvedor != cero) {
                    if (cont == 0) {
                        cadena.append(" WHERE ");
                    } else {
                        cadena.append(" AND ");
                    }
                    cadena.append("em.ID_PROVEDOR_FK  = '" + idProvedor + "' ");
                    cont++;
                }
                if (idProducto != null && idProducto != "") {
                    if (cont == 0) {
                        cadena.append(" WHERE ");
                    } else {
                        cadena.append(" AND ");
                    }
                    cadena.append("ex.ID_SUBPRODUCTO_FK  = '" + idProducto + "' ");
                    cont++;
                }
                if (idEmpaque != null && idEmpaque != cero) {
                    if (cont == 0) {
                        cadena.append(" WHERE ");
                    } else {
                        cadena.append(" AND ");
                    }
                    cadena.append("ex.ID_TIPO_EMPAQUE_FK  = '" + idEmpaque + "' ");
                    cont++;
                }
                if (idConvenio != null && idConvenio != cero) {
                    if (cont == 0) {
                        cadena.append(" WHERE ");
                    } else {
                        cadena.append(" AND ");
                    }
                    cadena.append("ex.ID_TIPO_CONVENIO_FK  = '" + idConvenio + "' ");
                    cont++;
                }
            } else {

                cadena.append(" WHERE  emp.ID_EMP_PK= '" + idEmpPK + "' ");
            }

            cadena.append(" and ex.CANTIDAD_EMPACAQUE > 0 ORDER BY  em.ID_EM_PK");

            query = em.createNativeQuery(cadena.toString());
            System.out.println(query);

            return query.getResultList();
        } catch (Exception ex) {
            System.out.println("Encontro null ejb");
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getExistenciasRepetidas(BigDecimal idSucursal, String idSubproductoFk, BigDecimal idTipoEmpaqueFk, BigDecimal idBodegaFk, BigDecimal idProvedorFk, BigDecimal idEMFK, BigDecimal idTipoConvenio) {

        try {
            Query query = em.createNativeQuery("select * from existencia_producto where id_sucursal_fk='" + idSucursal + "'and id_subproducto_Fk = '" + idSubproductoFk + "' and id_Tipo_Empaque_Fk='" + idTipoEmpaqueFk + "'and  id_Bodega_Fk='" + idBodegaFk + "'and  id_EM_FK = '" + idEMFK + "' and  ID_TIPO_CONVENIO_FK = '" + idTipoConvenio + "'");

            return query.getResultList();
        } catch (Exception ex) {
            System.out.println("Encontro null ejb");
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public int updatePrecio(ExistenciaProducto ep) {
        try {

            String bloqueo = ep.isEstatusBloqueo() == true ? "1" : "0";
            Query query = em.createNativeQuery("update EXISTENCIA_PRODUCTO SET PRECIO_MINIMO=?, PRECIO_VENTA=?, PRECIO_MAXIMO=?, ESTATUS_BLOQUEO=? WHERE ID_EXP_PK=?");
            query.setParameter(1, ep.getPrecioMinimo());
            query.setParameter(2, ep.getPrecioVenta());
            query.setParameter(3, ep.getPrecioMaximo());
            query.setParameter(4, bloqueo);
            query.setParameter(5, ep.getIdExistenciaProductoPk());

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    
    @Override
    public List<Object[]> getExistenciasCancelar(BigDecimal idExistencia) {
        System.out.println("Ejecuto Get Existencias para Cancelar");
        try {
           
            Query query = em.createNativeQuery("select exp.ID_EXP_PK,exp.CANTIDAD_EMPACAQUE,exp.KILOS_TOTALES from EXISTENCIA_PRODUCTO exp where exp.ID_EXP_PK=?");
            query.setParameter(1, idExistencia);
            System.out.println(query);
            return query.getResultList();
        } catch (Exception ex) {
            System.out.println("Encontro null ejb");
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
