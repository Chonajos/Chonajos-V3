/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author marcogante
 */
public class Sucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idSucursalPk;
    private String nombreSucursal;
    /*private String calleSucursal;
    private String coloniaSucursal;
    private Long telefonoSucursal;
    private List<ExistenciaProducto> existenciaProductoList;
    private Municipios idMunicipioFk;
    private Usuario idUsuarioSucursalFk;*/

    public int getIdSucursalPk() {
        return idSucursalPk;
    }

    public void setIdSucursalPk(int idSucursalPk) {
        this.idSucursalPk = idSucursalPk;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    @Override
    public String toString() {
        return "Sucursal{" + "idSucursalPk=" + idSucursalPk + ", nombreSucursal=" + nombreSucursal + '}';
    }
    

   
}
