/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.negocio;

import com.web.chon.dominio.Municipios;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author freddy
 */
@Remote
public interface NegocioCatMunicipio 
{
    public List<Object[]> getMunicipios(int idEntidad);

    public Object[] getMunicipioById(int idMunicipio);

    public int deleteMunicipio(int idMunicipio);

    public int updateMunicipio(Municipios muni);

    public int insertMunicipio(Municipios muni);
    
}
