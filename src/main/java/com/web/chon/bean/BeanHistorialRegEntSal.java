/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.RegistroEntradaSalida;
import com.web.chon.dominio.Sucursal;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceRegistroEntradaSalida;
import java.io.Serializable;
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
public class BeanHistorialRegEntSal implements Serializable{
    private static final long serialVersionUID = 1L;
    @Autowired
    IfaceRegistroEntradaSalida ifaceRegEntSal;
     @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    private RegistroEntradaSalida data;
    private RegistroEntradaSalida model;
    private Date fechaFin;
    private Date fechaInicio;
    private ArrayList<Sucursal> listaSucursales;
    @PostConstruct
    public void init()
    {
        
        listaSucursales = new ArrayList<Sucursal>();
        listaSucursales = ifaceCatSucursales.getSucursales();
    }
    
}
