/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.CodigoPostal;

/**
 *
 * @author freddy
 */
public interface IfaceCatCodigosPostales 
{
    public CodigoPostal getMunicipioById(String codigo_postal);
}
