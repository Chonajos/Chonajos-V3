/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;
import com.web.chon.dominio.Cliente;
import com.web.chon.negocio.NegocioCatCliente;
import com.web.chon.util.Utilidades;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Freddy
 */
@Service
public class ServiceCatCliente implements IfaceCatCliente {
    NegocioCatCliente ejb;
    
    @Override
    public ArrayList<Cliente> getClientes()       
    {
        //throw new UnsupportedOperationException("Not supported yet.");
       try
       {
            ArrayList <Cliente> lista_clientes = new ArrayList<Cliente>();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> lstObject = ejb.getClientes();
             
            for(Object[] obj: lstObject )
            {
                 
                Cliente cliente = new Cliente();
                cliente.setId_cliente(Integer.parseInt(obj[0].toString()));
                cliente.setNombre(obj[1].toString());
                cliente.setPaterno(obj[2].toString());
                cliente.setMaterno(obj[3].toString());
                cliente.setEmpresa(obj[4].toString());
                cliente.setCalle(obj[5].toString());
                cliente.setCp(Integer.parseInt(obj[6].toString()));
                cliente.setEstado(obj[7].toString());
                String auxiliar_sexo=obj[8].toString();
                cliente.setSexo(auxiliar_sexo.charAt(0));
                cliente.setFecha_nacimiento((Date) obj[9]);
                cliente.setTel_movil(Integer.parseInt(obj[10].toString()));
                cliente.setTel_fijo(Integer.parseInt(obj[11].toString()));
                cliente.setExt(Integer.parseInt(obj[12].toString()));
                cliente.setEmail(obj[13].toString());
                cliente.setNum_int(Integer.parseInt(obj[14].toString()));
                cliente.setNum_ext(Integer.parseInt(obj[15].toString()));
                cliente.setColonia(obj[16].toString());
                cliente.setClave_tel(Integer.parseInt(obj[17].toString()));
                cliente.setLada(Integer.parseInt(obj[18].toString()));
                cliente.setDel_Mun(obj[19].toString());
                cliente.setCalleFiscal(obj[20].toString());
                cliente.setNum_int_fiscal(Integer.parseInt(obj[21].toString()));
                cliente.setNum_ext_fiscal(Integer.parseInt(obj[22].toString()));
                cliente.setColoniaFiscal(obj[23].toString());
                cliente.setEstadoFiscal(obj[24].toString());
                cliente.setDel_mun_fiscal(obj[25].toString());
                cliente.setNextel(Integer.parseInt(obj[26].toString()));
                cliente.setRazon_social(obj[27].toString());
                cliente.setRfcFiscal(obj[28].toString());
                cliente.setcpFiscal(Integer.parseInt(obj[29].toString()));
                lista_clientes.add(cliente);
                 
            }
            return lista_clientes;
        }catch(Exception ex)
            {
                Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
                return null;

            }
    }

    @Override
    public Cliente getClienteById(int idCliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteCliente(int idCliente) {
        System.out.println("service delete clientes");
       return ejb.deleteCliente(idCliente);
    }

    @Override
    public int updateCliente(Cliente cliente) {
        System.out.println("service update cliente: "+cliente.toString());
        return ejb.updateCliente(cliente);
    }

    @Override
    public int insertCliente(Cliente cliente) {
        System.out.println("Cliente"+cliente.toString());
        return ejb.insertCliente(cliente);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
