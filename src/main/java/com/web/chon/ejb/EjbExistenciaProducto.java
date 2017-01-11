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

        //System.out.println("Entrada_Porducto Existencia Nuevo =============: " + e.toString());
        try {

            Query query = em.createNativeQuery("INSERT INTO EXISTENCIA_PRODUCTO (ID_EXP_PK,ID_SUBPRODUCTO_FK,ID_TIPO_EMPAQUE_FK,KILOS_TOTALES,CANTIDAD_EMPACAQUE,COMENTARIOS,ID_BODEGA_FK,ID_TIPO_CONVENIO_FK,CONVENIO,KILOSPROMPROD,ID_SUCURSAL_FK,ID_EMP_FK,PRECIO_MINIMO,PRECIO_VENTA,PRECIO_MAXIMO,ESTATUS_BLOQUEO)VALUES (S_EXISTENCIA_PRODUCTO.NextVal,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            query.setParameter(1, e.getIdSubProductoFK());
            query.setParameter(2, e.getIdTipoEmpaqueFK());
            query.setParameter(3, e.getKilosTotalesProducto());
            query.setParameter(4, e.getCantidadPaquetes());
            query.setParameter(5, e.getComentarios());
            query.setParameter(6, e.getIdBodegaFK());
            query.setParameter(7, e.getIdTipoConvenio());
            query.setParameter(8, e.getConvenio() == null ? e.getPrecio() : e.getConvenio());
            query.setParameter(9, e.getKilospromprod());
            query.setParameter(10, e.getIdSucursal());
            query.setParameter(11, e.getIdEntradaMercanciaProductoFK());
            query.setParameter(12, e.getPrecioMinimo());
            query.setParameter(13, e.getPrecioVenta());
            query.setParameter(14, e.getPrecioMaximo());
            query.setParameter(15, e.isEstatusBloqueo() == true ? "0" : "1");

            return query.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public List<Object[]> getExistenciaById(BigDecimal idExistencia) {
        try {

            Query query = em.createNativeQuery("select * from existencia_producto where ID_EXP_PK = ? ");
            query.setParameter(1, idExistencia);
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
            //System.out.println(query);
            return query.getResultList();
        } catch (Exception ex) {
            System.out.println("Encontro null ejb");
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int updateCantidadKilo(ExistenciaProducto e) {

        //System.out.println("EJBUPDATEPRODUCTO:--------------------"+e.toString());
        try {

            Query query = em.createNativeQuery("update EXISTENCIA_PRODUCTO SET CANTIDAD_EMPACAQUE=?, KILOS_TOTALES=? WHERE ID_EXP_PK=?");
            query.setParameter(1, e.getCantidadPaquetes());
            query.setParameter(2, e.getKilosTotalesProducto());
            query.setParameter(3, e.getIdExistenciaProductoPk());
            return query.executeUpdate();

        } catch (Exception ex) {
            System.out.println("error 0" + ex.getMessage().toUpperCase());
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);

            return 0;
        }
    }

    @Override
    public int update(ExistenciaProducto e) {

        try {

            Query query = em.createNativeQuery("update EXISTENCIA_PRODUCTO SET ID_SUBPRODUCTO_FK = ?, ID_TIPO_EMPAQUE_FK = ?, KILOS_TOTALES = ?, CANTIDAD_EMPACAQUE = ?, COMENTARIOS = ?,"
                    + "ID_BODEGA_FK = ?, ID_TIPO_CONVENIO_FK = ?, CONVENIO = ?, KILOSPROMPROD = ?, PRECIO_MINIMO = ?, PRECIO_VENTA = ?,"
                    + "PRECIO_MAXIMO = ?, ESTATUS_BLOQUEO = ?, ID_SUCURSAL_FK = ?, ID_EMP_FK=?  WHERE ID_EXP_PK=?");
            query.setParameter(1, e.getIdSubProductoFK());
            query.setParameter(2, e.getIdTipoEmpaqueFK());
            query.setParameter(3, e.getKilosTotalesProducto());
            query.setParameter(4, e.getCantidadPaquetes());
            query.setParameter(5, e.getComentarios());
            query.setParameter(6, e.getIdBodegaFK());
            query.setParameter(7, e.getIdTipoConvenio());
            query.setParameter(8, e.getConvenio());
            query.setParameter(9, e.getKilospromprod());
            query.setParameter(10, e.getPrecioMinimo());
            query.setParameter(11, e.getPrecioVenta());
            query.setParameter(12, e.getPrecioMaximo());
            query.setParameter(13, e.isEstatusBloqueo());
            query.setParameter(14, e.getIdSucursal());
            query.setParameter(15, e.getIdEntradaMercanciaProductoFK());
            query.setParameter(16, e.getIdExistenciaProductoPk());

            return query.executeUpdate();

        } catch (Exception ex) {
            System.out.println("error 0" + ex.getMessage().toUpperCase());
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);

            return 0;
        }
    }

    @Override
    public List<Object[]> getExistencias(BigDecimal idSucursal, BigDecimal idBodega, BigDecimal idProvedor, String idProducto, BigDecimal idEmpaque, BigDecimal idConvenio, BigDecimal idEmpPK, BigDecimal carro,BigDecimal estatusCarro) {

        try {

            Query query;
            int cont = 0;
            StringBuffer cadena = new StringBuffer("select ex.ID_EXP_PK,em.IDENTIFICADOR,subp.NOMBRE_SUBPRODUCTO, te.NOMBRE_EMPAQUE, "
                    + "  ex.CANTIDAD_EMPACAQUE,ex.KILOS_TOTALES,tc.DESCRIPCION_TIPO, "
                    + "  prove.nombre_provedor ||' '|| prove.A_PATERNO_PROVE || ' ' || prove.A_MATERNO_PROVE as nombreProvedor, "
                    + "  sucu.NOMBRE_SUCURSAL,bod.NOMBRE, ex.PRECIO_MINIMO, ex.PRECIO_VENTA, ex.PRECIO_MAXIMO, "
                    + "  ex.ESTATUS_BLOQUEO,ex.ID_SUBPRODUCTO_FK,ex.ID_TIPO_EMPAQUE_FK,bod.ID_BD_PK,ex.CONVENIO,em.CARROSUCURSAL, ex.ID_EMP_FK,ex.COMENTARIOS,ex.ID_TIPO_CONVENIO_FK,ex.KILOSPROMPROD,ex.ID_SUCURSAL_FK "
                    + " from EXISTENCIA_PRODUCTO ex "
                    + " JOIN ENTRADAMERCANCIAPRODUCTO emp ON emp.ID_EMP_PK = ex.ID_EMP_FK "
                    + " JOIN ENTRADAMERCANCIA em ON em.ID_EM_PK = emp.ID_EM_FK "
                    + " JOIN SUBPRODUCTO subp ON subp.ID_SUBPRODUCTO_PK = ex.ID_SUBPRODUCTO_FK "
                    + " JOIN TIPO_EMPAQUE te ON te.ID_TIPO_EMPAQUE_PK = ex.ID_TIPO_EMPAQUE_FK "
                    + " JOIN BODEGA bod ON bod.ID_BD_PK = ex.ID_BODEGA_FK "
                    + " JOIN TIPO_CONVENIO tc ON tc.ID_TC_PK = ex.ID_TIPO_CONVENIO_FK "
                    + " JOIN SUCURSAL sucu ON sucu.ID_SUCURSAL_PK = ex.ID_SUCURSAL_FK "
                    + " JOIN provedores prove ON prove.id_provedor_pk = em.id_provedor_fk");
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

            if (carro != null) {
                if (cont == 0) {
                    cadena.append(" WHERE ");
                } else {
                    cadena.append(" AND ");
                }
                cadena.append("em.CARROSUCURSAL  = '" + carro + "' ");
                cont++;
            }
            
            if (estatusCarro != null) {
                if (cont == 0) {
                    cadena.append(" WHERE ");
                } else {
                    cadena.append(" AND ");
                }
                cadena.append("em.STATUS_CARRO  = '" + estatusCarro + "' ");
                cont++;
            }

            cadena.append("  and (ex.CANTIDAD_EMPACAQUE > 0 or ex.KILOS_TOTALES > 0) ORDER BY  em.ID_EM_PK ");

            query = em.createNativeQuery(cadena.toString());

            return query.getResultList();
        } catch (Exception ex) {
            System.out.println("Encontro null ejb");
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Object[]> getExistenciasRepetidas(BigDecimal idSucursal, String idSubproductoFk, BigDecimal idTipoEmpaqueFk, BigDecimal idBodegaFk, BigDecimal idProvedorFk, BigDecimal idTipoConvenio, BigDecimal idEMProducto) {

        try {

            Query query = em.createNativeQuery("select * from existencia_producto where id_sucursal_fk = ? AND id_subproducto_Fk = ? "
                    + " AND id_Tipo_Empaque_Fk = ? AND id_Bodega_Fk = ? AND ID_TIPO_CONVENIO_FK = ? AND ID_EMP_FK = ?");

            query.setParameter(1, idSucursal);
            query.setParameter(2, idSubproductoFk);
            query.setParameter(3, idTipoEmpaqueFk);
            query.setParameter(4, idBodegaFk);
            query.setParameter(5, idTipoConvenio);
            query.setParameter(6, idEMProducto);

            return query.getResultList();
        } catch (Exception ex) {
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

            return query.getResultList();
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int deleteExistenciaProducto(ExistenciaProducto exp) {
        try {
            Query query = em.createNativeQuery("delete from EXISTENCIA_PRODUCTO exp where exp.ID_EMP_FK = ?");
            query.setParameter(1, exp.getIdEntradaMercanciaProductoFK());
            return query.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public List<Object[]> getExistenciaByIdEmpFk(BigDecimal idEmpFk) {
        try {
            Query query = em.createNativeQuery("select exp.* from EXISTENCIA_PRODUCTO exp where exp.ID_EMP_FK =?");
            query.setParameter(1, idEmpFk);
            //System.out.println("Parametro: "+idEmpFk);
            //System.out.println(query);
            return query.getResultList();
        } catch (Exception ex) {
            //System.out.println("Encontro null ejb");
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public int getNextVal() {
        try {
            Query query = em.createNativeQuery("select S_EXISTENCIA_PRODUCTO.NextVal from dual");
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception ex) {
            System.out.println("error >" + ex.getMessage());
            return 0;
        }

    }

    @Override
    public List<Object[]> getExistenciaByBarCode(String idSubProducto, BigDecimal idTipoEmpaqueFk, BigDecimal idTipoConvenioFk, BigDecimal idCarro, BigDecimal idSucursalFk) {
        try {

            Query query = em.createNativeQuery("select ex.ID_EXP_PK,em.IDENTIFICADOR,subp.NOMBRE_SUBPRODUCTO, te.NOMBRE_EMPAQUE,\n"
                    + "ex.CANTIDAD_EMPACAQUE,ex.KILOS_TOTALES,tc.DESCRIPCION_TIPO,\n"
                    + "prove.nombre_provedor ||' '|| prove.A_PATERNO_PROVE || ' ' || prove.A_MATERNO_PROVE as nombreProvedor,\n"
                    + "sucu.NOMBRE_SUCURSAL,bod.NOMBRE, ex.PRECIO_MINIMO, ex.PRECIO_VENTA, ex.PRECIO_MAXIMO,\n"
                    + "ex.ESTATUS_BLOQUEO,ex.ID_SUBPRODUCTO_FK,ex.ID_TIPO_EMPAQUE_FK,bod.ID_BD_PK,ex.CONVENIO,em.CARROSUCURSAL, \n"
                    + "ex.ID_EMP_FK,ex.COMENTARIOS,ex.ID_TIPO_CONVENIO_FK,ex.KILOSPROMPROD,ex.ID_SUCURSAL_FK\n"
                    + "from EXISTENCIA_PRODUCTO ex\n"
                    + "join ENTRADAMERCANCIAPRODUCTO emp\n"
                    + "on emp.ID_EMP_PK = ex.ID_EMP_FK\n"
                    + "join ENTRADAMERCANCIA em\n"
                    + "on em.ID_EM_PK = emp.ID_EM_FK\n"
                    + "join SUBPRODUCTO subp\n"
                    + "on subp.ID_SUBPRODUCTO_PK = ex.ID_SUBPRODUCTO_FK\n"
                    + "join TIPO_EMPAQUE te\n"
                    + "on te.ID_TIPO_EMPAQUE_PK = ex.ID_TIPO_EMPAQUE_FK\n"
                    + "join BODEGA bod\n"
                    + "on bod.ID_BD_PK = ex.ID_BODEGA_FK\n"
                    + "join TIPO_CONVENIO tc\n"
                    + "on tc.ID_TC_PK = ex.ID_TIPO_CONVENIO_FK\n"
                    + "join SUCURSAL sucu\n"
                    + "on sucu.ID_SUCURSAL_PK = ex.ID_SUCURSAL_FK\n"
                    + "join provedores prove\n"
                    + "on prove.id_provedor_pk = em.id_provedor_fk\n"
                    + "where ex.ID_SUBPRODUCTO_FK = '" + idSubProducto + "' \n"
                    + "and ex.ID_TIPO_EMPAQUE_FK = ? and ex.ID_TIPO_CONVENIO_FK=? \n"
                    + "and em.CARROSUCURSAL=? and em.ID_SUCURSAL_FK=?");

            query.setParameter(1, idTipoEmpaqueFk);
            query.setParameter(2, idTipoConvenioFk);
            query.setParameter(3, idCarro);
            query.setParameter(4, idSucursalFk);

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(EjbExistenciaProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}
