/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
                usuario.setClaveUsuario(obj[18] == null ? "" : obj[18].toString());
                usuario.setContrasenaUsuario(obj[4] == null ? "" : obj[4].toString());
                usuario.setRfcUsuario(obj[16] == null ? "" : obj[16].toString());
                
                lstUsuario.add(usuario);
                
            }
            
            return lstUsuario;
            
        } catch (Exception ex) {
            Logger.getLogger(ServiceProducto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public Usuario getUsuariosById(int idProducto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteUsuarios(int idUsuario) {
        System.out.println("service");
       return ejb.deleteUsuario(idUsuario);
    }

    @Override
    public int updateUsuario(Usuario usuario) {
        System.out.println("service"+usuario.toString());
        return ejb.updateUsuario(usuario);
    }
    
     @Override
    public int insertarUsuarios(Usuario usuario) {
         System.out.println("usuario"+usuario.toString());
      return ejb.insertUsuario(usuario);
 
    }
    
    



}
