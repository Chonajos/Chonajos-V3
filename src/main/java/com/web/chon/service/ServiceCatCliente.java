/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;
import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Usuario;
import com.web.chon.negocio.NegocioCatCliente;
import com.web.chon.negocio.NegocioCatUsuario;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
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
                cliente.setMaterno(obj[3] == null ? "" : obj[3].toString());
                cliente.setEmpresa(obj[4] == null ? "" : obj[4].toString());
                cliente.setCalle(obj[5] == null ? "" : obj[5].toString());
                cliente.setCp(Integer.parseInt(obj[6].toString()));
                cliente.setEstado(obj[7] == null ? "" : obj[7].toString());
                String auxiliar_sexo=obj[8] == null ? "" : obj[8].toString();
                cliente.setSexo(auxiliar_sexo.charAt(0));
                cliente.setFecha_nacimiento((Date) obj[9]);
                cliente.setTel_movil(Integer.parseInt(obj[10].toString()));
                cliente.setTel_fijo(Integer.parseInt(obj[11].toString()));
                cliente.setExt(Integer.parseInt(obj[12].toString()));
                cliente.setEmail(obj[13] == null ? "" : obj[13].toString());
                cliente.setNum_int(Integer.parseInt(obj[14].toString()));
                cliente.setNum_ext(Integer.parseInt(obj[15].toString()));
                cliente.setColonia(obj[16] == null ? "" : obj[16].toString());
                cliente.setClavecelular(Integer.parseInt(obj[17].toString()));
                cliente.setLadacelular(Integer.parseInt(obj[18].toString()));
                cliente.setDel_Mun(obj[19] == null ? "" : obj[19].toString());
                cliente.setCalleFiscal(obj[20] == null ? "" : obj[20].toString());
                cliente.setNum_int_fiscal(Integer.parseInt(obj[21].toString()));
                cliente.setNum_ext_fiscal(Integer.parseInt(obj[22].toString()));
                cliente.setColoniaFiscal(obj[23] == null ? "" : obj[23].toString());
                cliente.setEstadoFiscal(obj[24] == null ? "" : obj[24].toString());
                cliente.setDel_mun_fiscal(obj[25] == null ? "" : obj[25].toString());
                cliente.setNextel(Integer.parseInt(obj[26].toString()));
                cliente.setRazon_social(obj[27] == null ? "" : obj[27].toString());
                cliente.setRfcFiscal(obj[28] == null ? "" : obj[28].toString());
                cliente.setcpFiscal(Integer.parseInt(obj[29].toString()));
                cliente.setLadaoficina(Integer.parseInt(obj[30].toString()));
                cliente.setNextelclave(Integer.parseInt(obj[31].toString()));
                cliente.setcpFiscal(Integer.parseInt(obj[32].toString()));
                
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
       try
       {
            Cliente cliente = new Cliente();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> lstObject = ejb.getClienteById(idCliente);
             
            for(Object[] obj: lstObject )
            {    
                cliente.setId_cliente(obj[0] == null ? 0:Integer.parseInt(obj[0].toString()));
                cliente.setNombre(obj[1] == null ? "":obj[1].toString());
                cliente.setPaterno(obj[2] == null ? "":obj[2].toString());
                cliente.setMaterno(obj[3] == null ? "":obj[3].toString());
                cliente.setEmpresa(obj[4] == null ? "":obj[4].toString());
                cliente.setCalle(obj[5] == null ? "":obj[5].toString());
                cliente.setCp(obj[6] == null ? 0:Integer.parseInt(obj[6].toString()));
                cliente.setEstado(obj[7] == null ? "":obj[7].toString());
                String auxiliar_sexo=obj[8] == null ? "":obj[8].toString();
                cliente.setSexo(auxiliar_sexo.charAt(0));
                cliente.setFecha_nacimiento(obj[9] == null ? null:(Date) obj[9]);
                cliente.setTel_movil(Integer.parseInt(obj[10] == null ? "":obj[10].toString()));
                cliente.setTel_fijo(Integer.parseInt(obj[11] == null ? "":obj[11].toString()));
                cliente.setExt(Integer.parseInt(obj[12] == null ? "":obj[12].toString()));
                cliente.setEmail(obj[13] == null ? "":obj[13].toString());
                cliente.setNum_int(Integer.parseInt(obj[14] == null ? "":obj[14].toString()));
                cliente.setNum_ext(Integer.parseInt(obj[15] == null ? "":obj[15].toString()));
                cliente.setColonia(obj[16] == null ? "":obj[16].toString());
                cliente.setClavecelular(Integer.parseInt(obj[17] == null ? "":obj[17].toString()));
                cliente.setLadaoficina(Integer.parseInt(obj[18] == null ? "":obj[18].toString()));
                cliente.setDel_Mun(obj[19] == null ? "":obj[19].toString());
                cliente.setCalleFiscal(obj[20] == null ? "":obj[20].toString());
                cliente.setNum_int_fiscal(Integer.parseInt(obj[21] == null ? "":obj[21].toString()));
                cliente.setNum_ext_fiscal(Integer.parseInt(obj[22] == null ? "":obj[22].toString()));
                cliente.setColoniaFiscal(obj[23] == null ? "":obj[23].toString());
                cliente.setEstadoFiscal(obj[24] == null ? "":obj[24].toString());
                cliente.setDel_mun_fiscal(obj[25] == null ? "":obj[25].toString());
                cliente.setNextel(Integer.parseInt(obj[26] == null ? "":obj[26].toString()));
                cliente.setRazon_social(obj[27] == null ? "":obj[27].toString());
                cliente.setRfcFiscal(obj[28] == null ? "":obj[28].toString());
                cliente.setcpFiscal(Integer.parseInt(obj[29] == null ? "":obj[29].toString()));
      
                 
            }
            return cliente;
        }catch(Exception ex)
            {
                Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
                return null;

            }
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
    
     @Override
    public ArrayList<Cliente> getClienteByNombreCompleto(String nombre) {
        try {
            ArrayList<Cliente> lstCliente = new ArrayList<Cliente>();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> object = ejb.getClienteByNombreCompleto(nombre);

            for (Object[] obj : object) {

                Cliente cliente = new Cliente();
                cliente.setId_cliente(obj[0] == null ? null : Integer.parseInt(obj[0].toString()));
                cliente.setNombre(obj[1] == null ? "" : obj[1].toString());
                cliente.setPaterno(obj[2] == null ? "" : obj[2].toString());
                cliente.setMaterno(obj[3] == null ? "" : obj[3].toString());
                cliente.setNombre(cliente.getNombre()+" "+cliente.getPaterno()+" "+cliente.getMaterno());

                lstCliente.add(cliente);
            }

            return lstCliente;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
