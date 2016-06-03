/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.RegistroEntradaSalida;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceRegistroEntradaSalida;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author freddy
 */
@Component
@Scope("view")
public class BeanRegistroEntrada implements Serializable, BeanSimple
{
    private static final long serialVersionUID = 1L;
    private boolean botonGuardarEnabled;
    private boolean renderMap;
    private String jsonUbicaciones;
    private Double latitud;
    private Double longitud;
    private UsuarioDominio usuarioDominio;
    private Usuario usuario;
    private RegistroEntradaSalida data;
    private Date fechaTemporal;
    private boolean permissionToEntrada;
    private boolean permisionToSalida;
    private boolean bandera;
    
    @Autowired
    IfaceRegistroEntradaSalida ifaceRegEntSal;
    
    @Autowired
    IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private PlataformaSecurityContext context;
    @PostConstruct
    public void init()
    {
        usuario = new Usuario();
        usuarioDominio = context.getUsuarioAutenticado();
        usuario.setIdUsuarioPk(usuarioDominio.getIdUsuario());
        usuario.setIdSucursal(usuarioDominio.getSucId());
        data = new RegistroEntradaSalida();
        data.setIdUsuarioFk(usuario.getIdUsuarioPk());
        data.setIdSucursalFk(new BigDecimal(usuario.getIdSucursal()));
        fechaTemporal = context.getFechaSistema();
        ArrayList<RegistroEntradaSalida> lista = new ArrayList<RegistroEntradaSalida>();
        
        lista = ifaceRegEntSal.getUsuarioByIdUsuario(data.getIdUsuarioFk(), fechaTemporal);
        
        if(lista==null || lista.isEmpty())
        {
        
            //data.setFechaEntrada(user.getFechaEntrada());
            //Ya tiene la entrada Registrada
            bandera =true;
            permissionToEntrada = false;
            permisionToSalida = true;
            
            
        }
        else
        {
            data.setIdRegEntSalPk(lista.get(0).getIdRegEntSalPk());
            
            bandera = false;
            permissionToEntrada = true;
            permisionToSalida = false;
            
            //nueva entrada
            
        }
        

        
//        latitud=19.367365;
//        longitud=-99.096502;
    }
    

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String insert() {
        if(bandera)
        {
            
            data.setLatitudEntrada(latitud);
            data.setLongitudEntrada(longitud);
            data.setLatitudSalida(0);
            data.setLongitudSalida(0);
            int idRegEntSal = ifaceRegEntSal.getNextVal();
            data.setIdRegEntSalPk(new BigDecimal(idRegEntSal));
            if(ifaceRegEntSal.insertEntradabyIdReg(data)!=0)
            {
                JsfUtil.addSuccessMessageClean("Registro de Entrada Realizado Correctamente");
            }
            else
            {
                JsfUtil.addErrorMessageClean("Ocurrio un problema al registrar la Entrada");
            }
        }
        else
        {
            
            data.setLatitudSalida(latitud);
            data.setLongitudSalida(longitud);
            if(ifaceRegEntSal.updateSalidabyIdReg(data)!=0)
            {
                JsfUtil.addSuccessMessageClean("Registro de Salida Realizado Correctamente");
        }
            else
            {
                JsfUtil.addErrorMessageClean("Ocurrio un problema al registrar la Salida");
            }
            
        }
        
        System.out.println("=================");
        System.out.println(data.toString());
        
        return "";
       }
    

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchById() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isBotonGuardarEnabled() {
        return botonGuardarEnabled;
    }

    public void setBotonGuardarEnabled(boolean botonGuardarEnabled) {
        this.botonGuardarEnabled = botonGuardarEnabled;
    }

    public boolean isRenderMap() {
        return renderMap;
    }

    public void setRenderMap(boolean renderMap) {
        this.renderMap = renderMap;
    }

    public String getJsonUbicaciones() {
        return jsonUbicaciones;
    }

    public void setJsonUbicaciones(String jsonUbicaciones) {
        this.jsonUbicaciones = jsonUbicaciones;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Date getFechaTemporal() {
        return fechaTemporal;
    }

    public void setFechaTemporal(Date fechaTemporal) {
        this.fechaTemporal = fechaTemporal;
    }

    public boolean isPermissionToEntrada() {
        return permissionToEntrada;
    }

    public void setPermissionToEntrada(boolean permissionToEntrada) {
        this.permissionToEntrada = permissionToEntrada;
    }

    public boolean isPermisionToSalida() {
        return permisionToSalida;
    }

    public void setPermisionToSalida(boolean permisionToSalida) {
        this.permisionToSalida = permisionToSalida;
    }
    
    
}
