
package com.web.chon.service;

import com.web.chon.dominio.Usuario;
import com.web.chon.negocio.NegocioCatUsuario;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Juan de la Cruz
 */
@Service
public class ServiceCatUsuario implements IfaceCatUsuario {

    NegocioCatUsuario ejb;

    @Override
    public ArrayList<Usuario> getUsuarios() {
        try {
            ArrayList<Usuario> lstUsuario = new ArrayList<Usuario>();
            ejb = (NegocioCatUsuario) Utilidades.getEJBRemote("ejbCatUsuario", NegocioCatUsuario.class.getName());

            List<Object[]> lstObject = ejb.getUsuarios();

            for (Object[] obj : lstObject) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuarioPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                usuario.setNombreUsuario(obj[1] == null ? "" : obj[1].toString());
                usuario.setApaternoUsuario(obj[2] == null ? "" : obj[2].toString());
                usuario.setAmaternoUsuario(obj[3] == null ? "" : obj[3].toString());
                
                usuario.setContrasenaUsuario(obj[4] == null ? "" : obj[4].toString());
                usuario.setRfcUsuario(obj[5] == null ? "" : obj[5].toString());
                usuario.setClaveUsuario(obj[6] == null ? "" : obj[6].toString());
                usuario.setIdRolFk(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                usuario.setIdSucursal(obj[8] == null ? -1 : Integer.parseInt(obj[8].toString()));
                usuario.setStatus(obj[9] == null ? new BigDecimal(1) : new BigDecimal(obj[9].toString()));
                usuario.setNombreCompletoUsuario(usuario.getNombreUsuario().trim()+" "+usuario.getApaternoUsuario().trim()+" "+usuario.getAmaternoUsuario().trim());
                lstUsuario.add(usuario);

            }

            return lstUsuario;

        } catch (Exception ex) {
            Logger.getLogger(ServiceCatUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public Usuario getUsuariosById(int idProducto) {
        try {
            Usuario usuario = new Usuario();
            ejb = (NegocioCatUsuario) Utilidades.getEJBRemote("ejbCatUsuario", NegocioCatUsuario.class.getName());

            List<Object[]> lstObject = ejb.getUsuarioById(idProducto);

            for (Object[] obj : lstObject) {

                usuario.setIdUsuarioPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                usuario.setNombreUsuario(obj[1] == null ? "" : obj[1].toString());
                usuario.setApaternoUsuario(obj[2] == null ? "" : obj[2].toString());
                usuario.setAmaternoUsuario(obj[3] == null ? "" : obj[3].toString());
                usuario.setClaveUsuario(obj[18] == null ? "" : obj[18].toString());
                usuario.setContrasenaUsuario(obj[4] == null ? "" : obj[4].toString());
                usuario.setRfcUsuario(obj[16] == null ? "" : obj[16].toString());
                usuario.setIdRolFk(obj[21] == null ? null : new BigDecimal(obj[21].toString()));
                usuario.setIdSucursal(obj[26] == null ? -1 : Integer.parseInt(obj[26].toString()));
                usuario.setNombreCompletoUsuario(usuario.getNombreUsuario().trim()+" "+usuario.getApaternoUsuario().trim()+" "+usuario.getAmaternoUsuario().trim());
                System.out.println("service converter "+usuario);

            }

            return usuario;

        } catch (Exception ex) {
            Logger.getLogger(ServiceCatUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public int deleteUsuarios(int idUsuario) {
        return ejb.deleteUsuario(idUsuario);
    }

    @Override
    public int updateUsuario(Usuario usuario) {
        return ejb.updateUsuario(usuario);
    }

    @Override
    public int insertarUsuarios(Usuario usuario) {
        return ejb.insertUsuario(usuario);

    }

    @Override
    public ArrayList<Usuario> getUsuarioByNombreCompleto(String nombre, int idSucursal) {
        try {
            ArrayList<Usuario> lstUsuario = new ArrayList<Usuario>();
            ejb = (NegocioCatUsuario) Utilidades.getEJBRemote("ejbCatUsuario", NegocioCatUsuario.class.getName());
            List<Object[]> object = ejb.getUsuarioByNombreCompleto(nombre, idSucursal);

            for (Object[] obj : object) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuarioPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                usuario.setNombreUsuario(obj[1] == null ? "" : obj[1].toString());
                usuario.setApaternoUsuario(obj[2] == null ? "" : obj[2].toString());
                usuario.setAmaternoUsuario(obj[3] == null ? "" : obj[3].toString());
                usuario.setIdSucursal(obj[26] == null ? null : Integer.parseInt(obj[0].toString()));
                usuario.setNombreCompletoUsuario(usuario.getNombreUsuario().trim()+" "+usuario.getApaternoUsuario().trim()+" "+usuario.getAmaternoUsuario().trim());
                lstUsuario.add(usuario);
            }

            return lstUsuario;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Usuario getUsuarioByClave(String clave,int idSucursal) {
        try {

            Usuario usuario = new Usuario();
            ejb = (NegocioCatUsuario) Utilidades.getEJBRemote("ejbCatUsuario", NegocioCatUsuario.class.getName());

            List<Object[]> lstObject = ejb.getUsuarioByClave(clave,idSucursal);

            for (Object[] obj : lstObject) {

                usuario.setIdUsuarioPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                usuario.setNombreUsuario(obj[1] == null ? "" : obj[1].toString());
                usuario.setApaternoUsuario(obj[2] == null ? "" : obj[2].toString());
                usuario.setAmaternoUsuario(obj[3] == null ? "" : obj[3].toString());
                usuario.setClaveUsuario(obj[18] == null ? "" : obj[18].toString());
                usuario.setContrasenaUsuario(obj[4] == null ? "" : obj[4].toString());
                usuario.setRfcUsuario(obj[16] == null ? "" : obj[16].toString());
                usuario.setIdRolFk(obj[21] == null ? null : new BigDecimal(obj[21].toString()));
                usuario.setIdSucursal(obj[26] == null ? -1 : Integer.parseInt(obj[26].toString()));
                usuario.setNombreCompletoUsuario(usuario.getNombreUsuario().trim()+" "+usuario.getApaternoUsuario().trim()+" "+usuario.getAmaternoUsuario().trim());

            }
            
            return usuario;

        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ArrayList<Usuario> getUsuariosbyIdSucursal(int idSucursal) {
       
        try {
            ArrayList<Usuario> lstUsuario = new ArrayList<Usuario>();
            ejb = (NegocioCatUsuario) Utilidades.getEJBRemote("ejbCatUsuario", NegocioCatUsuario.class.getName());

            List<Object[]> lstObject = ejb.getUsuariosbyIdSucursal(idSucursal);

            for (Object[] obj : lstObject) {

                Usuario usuario = new Usuario();
                usuario.setIdUsuarioPk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                usuario.setNombreUsuario(obj[1] == null ? "" : obj[1].toString());
                usuario.setApaternoUsuario(obj[2] == null ? "" : obj[2].toString());
                usuario.setAmaternoUsuario(obj[3] == null ? "" : obj[3].toString());
                usuario.setClaveUsuario(obj[18] == null ? "" : obj[18].toString());
                usuario.setContrasenaUsuario(obj[4] == null ? "" : obj[4].toString());
                usuario.setRfcUsuario(obj[16] == null ? "" : obj[16].toString());
                usuario.setIdRolFk(obj[21] == null ? null : new BigDecimal(obj[21].toString()));
                usuario.setIdSucursal(obj[26] == null ? -1 : Integer.parseInt(obj[26].toString()));
                usuario.setNombreCompletoUsuario(usuario.getNombreUsuario().trim()+" "+usuario.getApaternoUsuario().trim()+" "+usuario.getAmaternoUsuario().trim());
                lstUsuario.add(usuario);
            }

            return lstUsuario;

        } catch (Exception ex) {
            Logger.getLogger(ServiceCatUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
