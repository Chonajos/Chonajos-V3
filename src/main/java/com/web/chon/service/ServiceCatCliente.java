package com.web.chon.service;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Correos;
import com.web.chon.negocio.NegocioCatCliente;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Freddy
 */
@Service
public class ServiceCatCliente implements IfaceCatCliente {

    NegocioCatCliente ejb;
    @Autowired
    private IfaceCatCorreos ifaceCatCorreos;

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
                cliente.setFecha_nacimiento(obj[7] == null ? null : (Date) obj[7]);
                cliente.setTel_movil(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
                cliente.setTel_fijo(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
                cliente.setExt(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
                cliente.setNum_int(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
                cliente.setNum_ext(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
                cliente.setClavecelular(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
                cliente.setLadacelular(obj[14] == null ? null : new BigDecimal(obj[14].toString()));
                cliente.setID_CP(obj[15] == null ? null : new BigDecimal(obj[15].toString()));//
                cliente.setCalleFiscal(obj[16] == null ? "" : obj[16].toString());
                cliente.setNum_int_fiscal(obj[17] == null ? new BigDecimal(0) : new BigDecimal(obj[17].toString()));
                cliente.setNum_ext_fiscal(obj[18] == null ? new BigDecimal(0) : new BigDecimal(obj[18].toString()));
                cliente.setID_CP_FISCAL(obj[19] == null ? new BigDecimal(100000) : new BigDecimal(obj[19].toString()));
                cliente.setNextel(obj[20] == null ? "" : obj[20].toString());
                cliente.setRazon_social(obj[21] == null ? "" : obj[21].toString());
                cliente.setRfcFiscal(obj[22] == null ? "" : obj[22].toString());
                cliente.setLadaoficina(obj[23] == null ? null : new BigDecimal(obj[23].toString()));
                cliente.setClaveoficina(obj[24] == null ? new BigDecimal(0) : new BigDecimal(obj[24].toString()));
                cliente.setNextelclave(obj[25] == null ? "" : obj[25].toString());
                cliente.setStatus_cliente(obj[26] == null ? new BigDecimal(0) : new BigDecimal(obj[26].toString()));
                cliente.setStatusClienteBoolean(obj[26] == null ? false : obj[26].toString().equals("1"));
                cliente.setTipoPersona(obj[30] == null ? "1" : obj[30].toString());
//Hasta aqui terminan los datos de la tabla clientes.
                //Los siguientes datos son para obtener las direcciones y los correos.
                cliente.setFecha_alta(obj[27] == null ? null : (Date) obj[27]);
                cliente.setDiasCredito(obj[28] == null ? null : new BigDecimal(obj[28].toString()));
                cliente.setLimiteCredito(obj[29] == null ? null : new BigDecimal(obj[29].toString()));
                cliente.setEstado_id(obj[31] == null ? "" : (obj[31].toString()));
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

                ArrayList<Correos> lista = new ArrayList<Correos>();
                lista = ifaceCatCorreos.SearchCorreosbyidClientPk(cliente.getId_cliente());
                if (!lista.isEmpty()) {
                    cliente.setEmail(lista.get(0).getCorreo());
                    cliente.setEmails(lista);
                }

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
                cliente.setNombre(obj[1] == null ? "" : obj[1].toString().trim());
                cliente.setPaterno(obj[2] == null ? "" : obj[2].toString().trim());
                cliente.setMaterno(obj[3] == null ? "" : obj[3].toString().trim());
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
        return ejb.deleteCliente(idCliente);
    }

    @Override
    public int updateCliente(Cliente cliente) {

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
            List<Object[]> object = ejb.getClienteByNombreCompleto(nombre.trim());

            for (Object[] obj : object) {

                Cliente cliente = new Cliente();
                cliente.setId_cliente(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                cliente.setNombre(obj[1] == null ? "" : obj[1].toString().trim());
                cliente.setPaterno(obj[2] == null ? "" : obj[2].toString().trim());
                cliente.setMaterno(obj[3] == null ? "" : obj[3].toString().trim());

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
                cliente.setNombreCompleto(obj[1] == null ? "" : obj[1].toString());
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
                cliente.setTel_movil(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
                cliente.setTel_fijo(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
                cliente.setExt(obj[10] == null ? null : new BigDecimal(obj[10].toString()));
                cliente.setNum_int(obj[11] == null ? null : new BigDecimal(obj[11].toString()));
                cliente.setNum_ext(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
                cliente.setClavecelular(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
                cliente.setLadacelular(obj[14] == null ? null : new BigDecimal(obj[14].toString()));
                cliente.setID_CP(obj[15] == null ? new BigDecimal(100000) : new BigDecimal(obj[15].toString()));//
                cliente.setCalleFiscal(obj[16] == null ? "" : obj[16].toString());
                cliente.setNum_int_fiscal(obj[17] == null ? null : new BigDecimal(obj[17].toString()));
                cliente.setNum_ext_fiscal(obj[18] == null ? null : new BigDecimal(obj[18].toString()));
                cliente.setID_CP_FISCAL(obj[19] == null ? new BigDecimal(100000) : new BigDecimal(obj[19].toString()));
                cliente.setNextel(obj[20] == null ? "" : obj[20].toString());
                cliente.setRazon_social(obj[21] == null ? "" : obj[21].toString());
                cliente.setRfcFiscal(obj[22] == null ? "" : obj[22].toString());
                cliente.setLadaoficina(obj[23] == null ? null : new BigDecimal(obj[23].toString()));
                cliente.setClaveoficina(obj[24] == null ? null : new BigDecimal(obj[24].toString()));
                cliente.setNextelclave(obj[25] == null ? "" : obj[25].toString());
                //cliente.setStatus_cliente(Integer.parseInt(obj[26] == null ? "0" : obj[26].toString()));
                cliente.setStatusClienteBoolean(obj[26].toString().equals("1"));
//Hasta aqui terminan los datos de la tabla clientes.
                //Los siguientes datos son para obtener las direcciones y los correos.
                cliente.setFecha_alta((Date) obj[27]);
                cliente.setDiasCredito(obj[28] == null ? null : new BigDecimal(obj[28].toString()));
                cliente.setLimiteCredito(obj[29] == null ? null : new BigDecimal(obj[29].toString()));
                cliente.setEstado_id(obj[30] == null ? "" : (obj[30].toString()));
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

    @Override
    public ArrayList<Cliente> getClienteByIdSubProducto(String idSubProducto,BigDecimal idSucursal) {
        try{
        ArrayList<Cliente> lstCliente = new ArrayList<Cliente>();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> lstObject = ejb.getClienteByIdSubProducto(idSubProducto,idSucursal);

            for (Object[] obj : lstObject) {
                Cliente cliente = new Cliente();
                
                cliente.setNombreCompleto(obj[0] == null ? "" : obj[0].toString());
                cliente.setEmail(obj[1] == null ? null : obj[1].toString());

                lstCliente.add(cliente);

            }
            return lstCliente;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }
}
