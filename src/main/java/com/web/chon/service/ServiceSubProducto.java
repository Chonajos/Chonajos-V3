package com.web.chon.service;

import com.web.chon.dominio.Subproducto;
import com.web.chon.negocio.NegocioSubProducto;
import com.web.chon.util.Utilidades;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * Servicio para el catalogo de Productos
 *
 * @author Juan de la Cruz
 */
@Service
public class ServiceSubProducto implements IfaceSubProducto {

    NegocioSubProducto ejb;

    @Override
    public ArrayList<Subproducto> getSubProductos() {
        try {
            ArrayList<Subproducto> lstSubProducto = new ArrayList<Subproducto>();
            ejb = (NegocioSubProducto) Utilidades.getEJBRemote("ejbSubProducto", NegocioSubProducto.class.getName());

            List<Object[]> lstObject = ejb.getSubProductos();

            for (Object[] obj : lstObject) {

                Subproducto subProducto = new Subproducto();
                subProducto.setIdSubproductoPk(obj[0] == null ? null : obj[0].toString());
                subProducto.setNombreSubproducto(obj[1] == null ? "" : obj[1].toString());
                subProducto.setDescripcionSubproducto(obj[2] == null ? "" : obj[2].toString());
                subProducto.setUrlImagenSubproducto(obj[3] == null ? "" : obj[3].toString());
                subProducto.setIdProductoFk(obj[4] == null ? null : obj[4].toString());
                subProducto.setFichero((obj[5] == null ? null : (byte[]) (obj[5])));
                subProducto.setNombreCategoria(obj[7] == null ? "" : obj[7].toString());
                

                lstSubProducto.add(subProducto);
            }

            return lstSubProducto;
        } catch (Exception ex) {
            Logger.getLogger(ServiceSubProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public Subproducto getSubProductoById(String idSubProducto) {
        try {
            Subproducto subProducto = new Subproducto();
            ejb = (NegocioSubProducto) Utilidades.getEJBRemote("ejbSubProducto", NegocioSubProducto.class.getName());
            List<Object[]> object = ejb.getProductoById(idSubProducto.trim());
            for (Object[] obj : object) {

                subProducto.setIdSubproductoPk(obj[0] == null ? null : obj[0].toString());
                subProducto.setNombreSubproducto(obj[1] == null ? "" : obj[1].toString());
                subProducto.setDescripcionSubproducto(obj[2] == null ? "" : obj[2].toString());
                subProducto.setUrlImagenSubproducto(obj[3] == null ? "" : obj[3].toString());
                subProducto.setIdProductoFk(obj[4] == null ? null : obj[4].toString());
            }

            return subProducto;
        } catch (Exception ex) {
            Logger.getLogger(ServiceSubProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public int deleteSubProducto(String idSubProducto) {
        return ejb.deleteSubProducto(idSubProducto);
    }

    @Override
    public int updateSubProducto(Subproducto subProducto) {

        int i = ejb.updateSubProducto(subProducto);
        if (i == 1) {
            try {
                byte[] fichero = subProducto.getFichero();
                ejb.insertarDocumento(subProducto.getIdSubproductoPk(), fichero);
                
            } catch (SQLException ex) {
                Logger.getLogger(ServiceSubProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return i;
    }

    @Override
    public int insertarSubProducto(Subproducto subProducto) {

        int i = ejb.insertarSubProducto(subProducto);

        if (i == 1) {
            try {
                byte[] fichero = subProducto.getFichero();
                ejb.insertarDocumento(subProducto.getIdSubproductoPk(), fichero);
            } catch (SQLException ex) {
                Logger.getLogger(ServiceSubProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return i;

    }

    @Override
    public int getLastIdProducto(String idCategoria) {
        try {
            int idProducto = 0;
            ejb = (NegocioSubProducto) Utilidades.getEJBRemote("ejbSubProducto", NegocioSubProducto.class.getName());
            idProducto = ejb.getLastIdProducto(idCategoria);
            return idProducto;
        } catch (Exception ex) {
            Logger.getLogger(ServiceSubProducto.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }

    @Override
    public ArrayList<Subproducto> getSubProductoByNombre(String nombre) {
        try {
            ArrayList<Subproducto> lstSubProducto = new ArrayList<Subproducto>();
            ejb = (NegocioSubProducto) Utilidades.getEJBRemote("ejbSubProducto", NegocioSubProducto.class.getName());
            List<Object[]> object = ejb.getSubProductoByNombre(nombre);

            for (Object[] obj : object) {

                Subproducto subProducto = new Subproducto();
                subProducto.setIdSubproductoPk(obj[0] == null ? null : obj[0].toString());
                subProducto.setNombreSubproducto(obj[1] == null ? "" : obj[1].toString());

                lstSubProducto.add(subProducto);
            }

            return lstSubProducto;
        } catch (Exception ex) {
            Logger.getLogger(ServiceSubProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public ArrayList<Subproducto> getSubProductosIdSucursal(BigDecimal idSucursal) {
        try {
            ArrayList<Subproducto> lstSubProducto = new ArrayList<Subproducto>();
            ejb = (NegocioSubProducto) Utilidades.getEJBRemote("ejbSubProducto", NegocioSubProducto.class.getName());

            List<Object[]> lstObject = ejb.getSubProductosIdSucursal(idSucursal);

            for (Object[] obj : lstObject) {

                Subproducto subProducto = new Subproducto();
                subProducto.setIdSubproductoPk(obj[0] == null ? null : obj[0].toString());
                subProducto.setNombreSubproducto(obj[1] == null ? "" : obj[1].toString());
                subProducto.setDescripcionSubproducto(obj[2] == null ? "" : obj[2].toString());
                subProducto.setUrlImagenSubproducto(obj[3] == null ? null : obj[3].toString());
                subProducto.setIdProductoFk(obj[4] == null ? null : obj[4].toString());
                subProducto.setNombreCategoria(obj[5] == null ? "" : obj[5].toString());
                subProducto.setPrecioProducto(obj[6] == null ? null : new BigDecimal(obj[6].toString()));
                subProducto.setFichero((obj[7] == null ? null : (byte[]) (obj[7])));

                lstSubProducto.add(subProducto);
            }

            return lstSubProducto;
        } catch (Exception ex) {
            Logger.getLogger(ServiceSubProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private byte[] convertToBytes(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(bos);

            out.writeObject(obj);

        } catch (IOException ex) {
            Logger.getLogger(ServiceSubProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bos.toByteArray();
    }

}
