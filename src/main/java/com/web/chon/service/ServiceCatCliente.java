/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.service;

import com.web.chon.dominio.Cliente;
import com.web.chon.negocio.NegocioCatCliente;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.math.MathContext;
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
    public ArrayList<Cliente> getClientes() {
        //throw new UnsupportedOperationException("Not supported yet.");
        try {
            ArrayList<Cliente> lista_clientes = new ArrayList<Cliente>();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> lstObject = ejb.getClientes();

            for (Object[] obj : lstObject) {
                Cliente cliente = new Cliente();
                cliente.setId_cliente(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                cliente.setNombre(obj[1] == null ? "" : obj[1].toString());
                cliente.setPaterno(obj[2] == null ? "" : obj[2].toString());
                cliente.setMaterno(obj[3] == null ? "" : obj[3].toString());
                cliente.setEmpresa(obj[4] == null ? "" : obj[4].toString());
                cliente.setCalle(obj[5] == null ? "" : obj[5].toString());
                String auxiliar_sexo = obj[6] == null ? "M" : obj[6].toString();
                cliente.setSexo(auxiliar_sexo.charAt(0));
                cliente.setFecha_nacimiento((Date) obj[7]);
                cliente.setTel_movil(Integer.parseInt(obj[8].toString()));
                cliente.setTel_fijo(Integer.parseInt(obj[9].toString()));
                cliente.setExt(Integer.parseInt(obj[10].toString()));
                cliente.setNum_int(Integer.parseInt(obj[11].toString()));
                cliente.setNum_ext(Integer.parseInt(obj[12].toString()));
                cliente.setClavecelular(Integer.parseInt(obj[13].toString()));
                cliente.setLadacelular(Integer.parseInt(obj[14] == null ? "0" : obj[14].toString()));
                cliente.setID_CP(Integer.parseInt(obj[15] == null ? "100000" : obj[15].toString()));//
                cliente.setCalleFiscal(obj[16] == null ? "" : obj[16].toString());
                cliente.setNum_int_fiscal(Integer.parseInt(obj[17] == null ? "0" : obj[17].toString()));
                cliente.setNum_ext_fiscal(Integer.parseInt(obj[18] == null ? "0" : obj[18].toString()));
                cliente.setID_CP_FISCAL(Integer.parseInt(obj[19] == null ? "100000" : obj[19].toString()));
                cliente.setNextel(obj[20] == null ? "" : obj[20].toString());
                cliente.setRazon_social(obj[21] == null ? "" : obj[21].toString());
                cliente.setRfcFiscal(obj[22] == null ? "" : obj[22].toString());
                cliente.setLadaoficina(obj[23] == null ? 0 : Integer.parseInt(obj[23].toString()));
                cliente.setClaveoficina(obj[24] == null ? 0 : Integer.parseInt(obj[24].toString()));
                cliente.setNextelclave(obj[25] == null ? "" : obj[25].toString());
                cliente.setStatus_cliente(Integer.parseInt(obj[26] == null ? "0" : obj[26].toString()));
                cliente.setStatusClienteBoolean(obj[26].toString().equals("1"));
                cliente.setTipoPersona(obj[30] == null ? null : obj[30].toString());
//Hasta aqui terminan los datos de la tabla clientes.
                //Los siguientes datos son para obtener las direcciones y los correos.
                cliente.setFecha_alta((Date) obj[27]);
                cliente.setDiasCredito(obj[28] == null ? null : new BigDecimal(obj[28].toString()));
                cliente.setLimiteCredito(obj[29] == null ? null : new BigDecimal(obj[29].toString()));
                cliente.setEstado(obj[31] == null ? "" : (obj[31].toString()));
                //agregamos fecha de alta.
                cliente.setNombreEstado(obj[32] == null ? "" : (obj[32].toString()));
                cliente.setEstadoFiscal(obj[33] == null ? "" : (obj[33].toString()));
                cliente.setNombreEstadoFiscal(obj[34] == null ? "" : (obj[34].toString()));
                cliente.setNombreDelegacionMunicipio(obj[35] == null ? "" : (obj[35].toString()));
                cliente.setNombreDeleMunFiscal(obj[36] == null ? "" : (obj[36].toString()));
                cliente.setColonia(obj[37] == null ? "" : (obj[37].toString()));
                cliente.setCodigoPostal(obj[38] == null ? "" : (obj[38].toString()));
                cliente.setColoniaFiscal(obj[39] == null ? "" : (obj[39].toString()));
                cliente.setCodigoPostalFiscal(obj[40] == null ? "" : (obj[40].toString()));
                cliente.setMunicipio(obj[41] == null ? "" : (obj[41].toString()));
                cliente.setMunicipioFiscal(obj[42] == null ? "" : (obj[42].toString()));
                
                
                //cliente.setID_CP(Integer.parseInt(obj[39] == null ? "":(obj[39].toString())));
                //cliente.setID_CP_FISCAL(Integer.parseInt(obj[40] == null ? "":(obj[40].toString())));

                lista_clientes.add(cliente);

            }
            return lista_clientes;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    @Override
    public Cliente getClienteById(int idCliente) {
        try {
            Cliente cliente = new Cliente();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> lstObject = ejb.getClienteById(idCliente);

            for (Object[] obj : lstObject) {
                cliente.setId_cliente(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                cliente.setNombre(obj[1] == null ? "" : obj[1].toString());
                cliente.setPaterno(obj[2] == null ? "" : obj[2].toString());
                cliente.setMaterno(obj[3] == null ? "" : obj[3].toString());
                /*cliente.setEmpresa(obj[4] == null ? "":obj[4].toString());
                cliente.setCalle(obj[5] == null ? "":obj[5].toString());           
                String auxiliar_sexo=obj[6] == null ? "M":obj[6].toString();
              
                //cliente.setEstado(obj[7] == null ? "":obj[7].toString());
                cliente.setSexo(auxiliar_sexo.charAt(0));
                cliente.setFecha_nacimiento((Date) obj[7]);
                cliente.setTel_movil(obj[8] == null ? 0:Integer.parseInt(obj[8].toString()));
                cliente.setTel_fijo(obj[9] == null ? 0: Integer.parseInt(obj[9].toString()));
                cliente.setExt(obj[10] == null ? 0:Integer.parseInt(obj[10].toString()));
                cliente.setEmail(obj[11] == null ? "":obj[11].toString());
                cliente.setNum_int(obj[12] == null ? 0:Integer.parseInt(obj[12].toString()));
                cliente.setNum_ext(obj[13] == null ? 0:Integer.parseInt(obj[13].toString()));
                cliente.setColonia(obj[14] == null ? "":obj[14].toString());
                cliente.setClavecelular(obj[15] == null ? 0:Integer.parseInt(obj[15].toString()));
                cliente.setLadaoficina(obj[16] == null ? 0:Integer.parseInt(obj[16].toString()));
                cliente.setID_CP(obj[17] == null ? 0:Integer.parseInt(obj[17].toString()));
                cliente.setCalleFiscal(obj[18] == null ? "":obj[18].toString());
                cliente.setNum_int_fiscal(obj[19] == null ? 0:Integer.parseInt(obj[19].toString()));
                cliente.setNum_ext_fiscal(obj[20] == null ? 0:Integer.parseInt(obj[20].toString()));
                cliente.setColoniaFiscal(obj[21] == null ? null:obj[21].toString());
                //cliente.setEstadoFiscal(obj[24] == null ? "1":obj[24].toString());
                cliente.setID_CP_FISCAL(obj[22] == null ? 0:Integer.parseInt(obj[22].toString()));
                cliente.setNextel(obj[23] == null ? 0:Integer.parseInt(obj[23].toString()));
                cliente.setRazon_social(obj[24] == null ? "":obj[24].toString());
                cliente.setRfcFiscal(obj[25] == null ? "":obj[25].toString());
                //cliente.setcpFiscal(Integer.parseInt(obj[27] == null ? "":obj[27].toString()));
                 */
                cliente.setDiasCredito(obj[28] == null ? null : new BigDecimal(obj[28].toString()));
                cliente.setLimiteCredito(obj[29] == null ? null : new BigDecimal(obj[29].toString()));

            }
            return cliente;
        } catch (Exception ex) {
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

        System.out.println("service update cliente: " + cliente.toString());
        return ejb.updateCliente(cliente);
    }

    @Override
    public int insertCliente(Cliente cliente) {
        System.out.println("Cliente" + cliente.toString());
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
                cliente.setId_cliente(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                cliente.setNombre(obj[1] == null ? "" : obj[1].toString());
                cliente.setPaterno(obj[2] == null ? "" : obj[2].toString());
                cliente.setMaterno(obj[3] == null ? "" : obj[3].toString());

                lstCliente.add(cliente);
            }

            return lstCliente;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int getNextVal() {
        try {
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            return ejb.getNextVal();
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public Cliente getClienteCreditoById(int idCliente) {
        try {
            Cliente cliente = new Cliente();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> lstObject = ejb.getClienteCreditoById(idCliente);

            for (Object[] obj : lstObject) {
                cliente.setId_cliente(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                cliente.setNombre(obj[1] == null ? "" : obj[1].toString());
                cliente.setPaterno(obj[2] == null ? "" : obj[2].toString());
                cliente.setMaterno(obj[3] == null ? "" : obj[3].toString());
                cliente.setLimiteCredito(obj[4] == null ? new BigDecimal("0") : new BigDecimal(obj[4].toString()));
                cliente.setUtilizadoMenudeo(obj[5] == null ? new BigDecimal("0") : new BigDecimal(obj[5].toString()));
                cliente.setUtilizadoMayoreo(obj[6] == null ? new BigDecimal("0") : new BigDecimal(obj[6].toString()));
                cliente.setUtilizadoTotal(cliente.getUtilizadoMenudeo().add(cliente.getUtilizadoMayoreo(), MathContext.UNLIMITED));
                cliente.setCreditoDisponible(cliente.getLimiteCredito().subtract(cliente.getUtilizadoTotal(), MathContext.UNLIMITED));
            }
            System.out.println("Cliente: " + cliente.toString());
            return cliente;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    @Override
    public Cliente getCreditoClienteByIdCliente(BigDecimal idCliente) {
        try {
            Cliente cliente = new Cliente();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> lstObject = ejb.getCreditoClienteByIdCliente(idCliente);

            for (Object[] obj : lstObject) {
                cliente.setId_cliente(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                cliente.setNombreCombleto(obj[1] == null ? "" : obj[1].toString());
                cliente.setLimiteCredito(obj[2] == null ? new BigDecimal("0") : new BigDecimal(obj[2].toString()));
                cliente.setUtilizadoTotal(obj[3] == null ? new BigDecimal("0") : new BigDecimal(obj[3].toString()));
                cliente.setUtilizadoDocumentos(obj[4] == null ? new BigDecimal("0") : new BigDecimal(obj[4].toString()));
                cliente.setCreditoDisponible((cliente.getLimiteCredito().subtract(cliente.getUtilizadoTotal(), MathContext.UNLIMITED)).subtract(cliente.getUtilizadoDocumentos(), MathContext.UNLIMITED));
                
            }
         

            return cliente;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }
    
     @Override
    public ArrayList<Cliente> getClientesActivos() {
        try {
            ArrayList<Cliente> lista_clientes = new ArrayList<Cliente>();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> lstObject = ejb.getClientes();

            for (Object[] obj : lstObject) {
                Cliente cliente = new Cliente();
                cliente.setId_cliente(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                cliente.setNombre(obj[1] == null ? "" : obj[1].toString());
                cliente.setPaterno(obj[2] == null ? "" : obj[2].toString());
                cliente.setMaterno(obj[3] == null ? "" : obj[3].toString());
                cliente.setEmpresa(obj[4] == null ? "" : obj[4].toString());
                cliente.setCalle(obj[5] == null ? "" : obj[5].toString());
                String auxiliar_sexo = obj[6] == null ? "M" : obj[6].toString();
                cliente.setSexo(auxiliar_sexo.charAt(0));
                cliente.setFecha_nacimiento((Date) obj[7]);
                cliente.setTel_movil(Integer.parseInt(obj[8].toString()));
                cliente.setTel_fijo(Integer.parseInt(obj[9].toString()));
                cliente.setExt(Integer.parseInt(obj[10].toString()));
                cliente.setNum_int(Integer.parseInt(obj[11].toString()));
                cliente.setNum_ext(Integer.parseInt(obj[12].toString()));
                cliente.setClavecelular(Integer.parseInt(obj[13].toString()));
                cliente.setLadacelular(Integer.parseInt(obj[14] == null ? "0" : obj[14].toString()));
                cliente.setID_CP(Integer.parseInt(obj[15] == null ? "100000" : obj[15].toString()));//
                cliente.setCalleFiscal(obj[16] == null ? "" : obj[16].toString());
                cliente.setNum_int_fiscal(Integer.parseInt(obj[17] == null ? "0" : obj[17].toString()));
                cliente.setNum_ext_fiscal(Integer.parseInt(obj[18] == null ? "0" : obj[18].toString()));
                cliente.setID_CP_FISCAL(Integer.parseInt(obj[19] == null ? "100000" : obj[19].toString()));
                cliente.setNextel(obj[20] == null ? "" : obj[20].toString());
                cliente.setRazon_social(obj[21] == null ? "" : obj[21].toString());
                cliente.setRfcFiscal(obj[22] == null ? "" : obj[22].toString());
                cliente.setLadaoficina(obj[23] == null ? 0 : Integer.parseInt(obj[23].toString()));
                cliente.setClaveoficina(obj[24] == null ? 0 : Integer.parseInt(obj[24].toString()));
                cliente.setNextelclave(obj[25] == null ? "" : obj[25].toString());
                cliente.setStatus_cliente(Integer.parseInt(obj[26] == null ? "0" : obj[26].toString()));
                cliente.setStatusClienteBoolean(obj[26].toString().equals("1"));
//Hasta aqui terminan los datos de la tabla clientes.
                //Los siguientes datos son para obtener las direcciones y los correos.
                cliente.setFecha_alta((Date) obj[27]);
                cliente.setDiasCredito(obj[28] == null ? null : new BigDecimal(obj[28].toString()));
                cliente.setLimiteCredito(obj[29] == null ? null : new BigDecimal(obj[29].toString()));
                cliente.setEstado(obj[30] == null ? "" : (obj[30].toString()));
                //agregamos fecha de alta.
                cliente.setNombreEstado(obj[31] == null ? "" : (obj[31].toString()));
                cliente.setEstadoFiscal(obj[32] == null ? "" : (obj[32].toString()));
                cliente.setNombreEstadoFiscal(obj[33] == null ? "" : (obj[33].toString()));
                cliente.setNombreDelegacionMunicipio(obj[34] == null ? "" : (obj[34].toString()));
                cliente.setNombreDeleMunFiscal(obj[35] == null ? "" : (obj[35].toString()));
                cliente.setColonia(obj[36] == null ? "" : (obj[36].toString()));
                cliente.setCodigoPostal(obj[37] == null ? "" : (obj[37].toString()));
                cliente.setColoniaFiscal(obj[38] == null ? "" : (obj[38].toString()));
                cliente.setCodigoPostalFiscal(obj[39] == null ? "" : (obj[39].toString()));
                cliente.setMunicipio(obj[40] == null ? "" : (obj[40].toString()));
                cliente.setMunicipioFiscal(obj[41] == null ? "" : (obj[41].toString()));

                lista_clientes.add(cliente);

            }
            return lista_clientes;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }
}
