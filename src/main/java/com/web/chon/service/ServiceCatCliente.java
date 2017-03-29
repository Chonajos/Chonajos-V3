package com.web.chon.service;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.ReporteClienteVentas;
import com.web.chon.negocio.NegocioCatCliente;
import com.web.chon.util.Utilidades;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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

    private void getEjb() {
        if (ejb == null) {
            try {
                ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            } catch (Exception ex) {
                Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Cliente> getClientes() {
        getEjb();
        ArrayList<Cliente> lista_clientes = new ArrayList<Cliente>();
        List<Object[]> lstObject = ejb.getClientes();
        for (Object[] obj : lstObject) {
            Cliente cliente = new Cliente();
            cliente.setIdClientePk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            cliente.setNombre(obj[1] == null ? "" : obj[1].toString());
            cliente.setPaterno(obj[2] == null ? "" : obj[2].toString());
            cliente.setMaterno(obj[3] == null ? "" : obj[3].toString());
            cliente.setEmpresa(obj[4] == null ? "" : obj[4].toString());
            cliente.setCalle(obj[5] == null ? "" : obj[5].toString());
            String auxiliar_sexo = obj[6] == null ? "M" : obj[6].toString();
            cliente.setSexo(auxiliar_sexo.charAt(0));
            cliente.setTelefonoCelular(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            cliente.setTelefonoOficina(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            cliente.setExt(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            cliente.setNumInterior(obj[10] == null ? null : obj[10].toString());
            cliente.setNumExterior(obj[11] == null ? null : obj[11].toString());
            cliente.setIdCodigoPostalFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
            cliente.setNextel(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
            cliente.setRazonSocial(obj[14] == null ? null : obj[14].toString());
            cliente.setRfc(obj[15] == null ? null : obj[15].toString());//
            cliente.setIdStatusFk(obj[16] == null ? null : new BigDecimal(obj[16].toString()));
            cliente.setFechaAlta(obj[17] == null ? null : (Date) obj[17]);
            cliente.setDiasCredito(obj[18] == null ? null : new BigDecimal(obj[18].toString()));
            cliente.setLimiteCredito(obj[19] == null ? new BigDecimal("0") : new BigDecimal(obj[19].toString()));
            cliente.setTipoPersona(obj[20] == null ? "1" : obj[20].toString());
            cliente.setLocalidad(obj[21] == null ? "" : obj[21].toString());
            cliente.setPais(obj[22] == null ? "" : obj[22].toString());
            cliente.setCorreo(obj[23] == null ? "" : obj[23].toString());
            cliente.setCodigoPostal(obj[24] == null ? "" : obj[24].toString());
            cliente.setNombreColonia(obj[25] == null ? "" : obj[25].toString());
            cliente.setNombreMunicipio(obj[26] == null ? "" : obj[26].toString());
            cliente.setNombreEstado(obj[27] == null ? "" : obj[27].toString());
            cliente.setIdColoniaFk(obj[28] == null ? null : new BigDecimal(obj[28].toString()));
            cliente.setIdMunicipioFk(obj[29] == null ? null : new BigDecimal(obj[29].toString()));
            cliente.setIdEntidadFk(obj[30] == null ? null : new BigDecimal(obj[30].toString()));
            lista_clientes.add(cliente);

        }
        return lista_clientes;
    }

    @Override
    public Cliente getClienteById(BigDecimal idClientePk) {
        getEjb();
        Cliente cliente = new Cliente();
        List<Object[]> lstObject = ejb.getClienteById(idClientePk);
        for (Object[] obj : lstObject) {
            cliente.setIdClientePk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
            cliente.setNombre(obj[1] == null ? "" : obj[1].toString());
            cliente.setPaterno(obj[2] == null ? "" : obj[2].toString());
            cliente.setMaterno(obj[3] == null ? "" : obj[3].toString());
            cliente.setEmpresa(obj[4] == null ? "" : obj[4].toString());
            cliente.setCalle(obj[5] == null ? "" : obj[5].toString());
            String auxiliar_sexo = obj[6] == null ? "M" : obj[6].toString();
            cliente.setSexo(auxiliar_sexo.charAt(0));
            cliente.setTelefonoCelular(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
            cliente.setTelefonoOficina(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
            cliente.setExt(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
            cliente.setNumInterior(obj[10] == null ? null : obj[10].toString());
            cliente.setNumExterior(obj[11] == null ? null : obj[11].toString());
            cliente.setIdCodigoPostalFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
            cliente.setNextel(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
            cliente.setRazonSocial(obj[14] == null ? null : obj[14].toString());
            cliente.setRfc(obj[15] == null ? null : obj[15].toString());//
            cliente.setIdStatusFk(obj[16] == null ? null : new BigDecimal(obj[16].toString()));
            cliente.setFechaAlta(obj[17] == null ? null : (Date) obj[17]);
            cliente.setDiasCredito(obj[18] == null ? null : new BigDecimal(obj[18].toString()));
            cliente.setLimiteCredito(obj[19] == null ? new BigDecimal("0") : new BigDecimal(obj[19].toString()));
            cliente.setTipoPersona(obj[20] == null ? "1" : obj[20].toString());
            cliente.setLocalidad(obj[21] == null ? "" : obj[21].toString());
            cliente.setPais(obj[22] == null ? "" : obj[22].toString());
            cliente.setCorreo(obj[23] == null ? "" : obj[23].toString());
            cliente.setCodigoPostal(obj[24] == null ? "" : obj[24].toString());
            cliente.setNombreColonia(obj[25] == null ? "" : obj[25].toString());
            cliente.setNombreMunicipio(obj[26] == null ? "" : obj[26].toString());
            cliente.setNombreEstado(obj[27] == null ? "" : obj[27].toString());
        }
        return cliente;

    }

    @Override
    public int deleteCliente(BigDecimal idCliente) {
        getEjb();
        return ejb.deleteCliente(idCliente);
    }

    @Override
    public int updateCliente(Cliente cliente) {
        getEjb();
        return ejb.updateCliente(cliente);
    }

    @Override
    public int insertCliente(Cliente cliente) {
        getEjb();
        System.out.println("Cliente" + cliente.toString());
        return ejb.insertCliente(cliente);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Cliente> getClienteByNombreCompleto(String nombre) {
        getEjb();
        try {
            ArrayList<Cliente> lstCliente = new ArrayList<Cliente>();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> object = ejb.getClienteByNombreCompleto(nombre.trim());

            for (Object[] obj : object) {

                Cliente cliente = new Cliente();
                cliente.setIdClientePk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
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
        getEjb();
        try {
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            return ejb.getNextVal();
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public Cliente getClienteCreditoById(BigDecimal idCliente) {
        getEjb();
        try {
            Cliente cliente = new Cliente();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> lstObject = ejb.getClienteCreditoById(idCliente);

            for (Object[] obj : lstObject) {
                cliente.setIdClientePk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
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
        getEjb();
        try {
            Cliente cliente = new Cliente();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> lstObject = ejb.getCreditoClienteByIdCliente(idCliente);

            for (Object[] obj : lstObject) {
                cliente.setIdClientePk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                cliente.setNombreCompleto(obj[1] == null ? "" : obj[1].toString());
                cliente.setLimiteCredito(obj[2] == null ? new BigDecimal("0") : new BigDecimal(obj[2].toString()));
                cliente.setUtilizadoTotal(obj[3] == null ? new BigDecimal("0") : new BigDecimal(obj[3].toString()));
                cliente.setUtilizadoDocumentos(obj[4] == null ? new BigDecimal("0") : new BigDecimal(obj[4].toString()));
                cliente.setPromedioRecuperacionTres(obj[5] == null ? new BigDecimal("0") : new BigDecimal(obj[5].toString()).setScale(2,RoundingMode.CEILING));
                cliente.setPromedioRecuperacion(obj[6] == null ? new BigDecimal("0") : new BigDecimal(obj[6].toString()).setScale(2,RoundingMode.CEILING));
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
        getEjb();
        try {
            ArrayList<Cliente> lista_clientes = new ArrayList<Cliente>();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> lstObject = ejb.getClientes();

            for (Object[] obj : lstObject) {
                Cliente cliente = new Cliente();
                cliente.setIdClientePk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                cliente.setNombre(obj[1] == null ? "" : obj[1].toString());
                cliente.setPaterno(obj[2] == null ? "" : obj[2].toString());
                cliente.setMaterno(obj[3] == null ? "" : obj[3].toString());
                cliente.setEmpresa(obj[4] == null ? "" : obj[4].toString());
                cliente.setCalle(obj[5] == null ? "" : obj[5].toString());
                String auxiliar_sexo = obj[6] == null ? "M" : obj[6].toString();
                cliente.setSexo(auxiliar_sexo.charAt(0));
                cliente.setTelefonoCelular(obj[7] == null ? null : new BigDecimal(obj[7].toString()));
                cliente.setTelefonoOficina(obj[8] == null ? null : new BigDecimal(obj[8].toString()));
                cliente.setExt(obj[9] == null ? null : new BigDecimal(obj[9].toString()));
                cliente.setNumInterior(obj[10] == null ? null : obj[10].toString());
                cliente.setNumExterior(obj[11] == null ? null : obj[11].toString());
                cliente.setIdCodigoPostalFk(obj[12] == null ? null : new BigDecimal(obj[12].toString()));
                cliente.setNextel(obj[13] == null ? null : new BigDecimal(obj[13].toString()));
                cliente.setRazonSocial(obj[14] == null ? null : obj[14].toString());
                cliente.setRfc(obj[15] == null ? null : obj[15].toString());//
                cliente.setIdStatusFk(obj[16] == null ? null : new BigDecimal(obj[16].toString()));
                cliente.setFechaAlta(obj[17] == null ? null : (Date) obj[17]);
                cliente.setDiasCredito(obj[18] == null ? null : new BigDecimal(obj[18].toString()));
                cliente.setLimiteCredito(obj[19] == null ? new BigDecimal("0") : new BigDecimal(obj[19].toString()));
                cliente.setTipoPersona(obj[20] == null ? "1" : obj[20].toString());
                cliente.setLocalidad(obj[21] == null ? "" : obj[21].toString());
                cliente.setPais(obj[22] == null ? "" : obj[22].toString());
                cliente.setCorreo(obj[23] == null ? "" : obj[23].toString());
                cliente.setCodigoPostal(obj[24] == null ? "" : obj[24].toString());
                cliente.setNombreColonia(obj[25] == null ? "" : obj[25].toString());
                cliente.setNombreMunicipio(obj[26] == null ? "" : obj[26].toString());
                cliente.setNombreEstado(obj[27] == null ? "" : obj[27].toString());
                lista_clientes.add(cliente);
            }
            return lista_clientes;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public ArrayList<Cliente> getClienteByIdSubProducto(String idSubProducto, BigDecimal idSucursal) {
        getEjb();
        try {
            ArrayList<Cliente> lstCliente = new ArrayList<Cliente>();
            List<Object[]> lstObject = ejb.getClienteByIdSubProducto(idSubProducto, idSucursal);

            for (Object[] obj : lstObject) {
                Cliente cliente = new Cliente();

                cliente.setNombreCompleto(obj[0] == null ? "" : obj[0].toString());
                cliente.setCorreo(obj[1] == null ? null : obj[1].toString());

                lstCliente.add(cliente);

            }
            return lstCliente;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    @Override
    public ArrayList<ReporteClienteVentas> getReporteClienteVentasUtilidad(BigDecimal idCliente, String fechaInicio, String fechaFin) {
        getEjb();
        try {
            ArrayList<ReporteClienteVentas> lstReporteClienteVentas = new ArrayList<ReporteClienteVentas>();
            ejb = (NegocioCatCliente) Utilidades.getEJBRemote("ejbCatCliente", NegocioCatCliente.class.getName());
            List<Object[]> lstObject = ejb.getReporteClienteVentasUtilidad(idCliente, fechaInicio, fechaFin);

            for (Object[] obj : lstObject) {

                ReporteClienteVentas reporteClienteVentas = new ReporteClienteVentas();

                reporteClienteVentas.setIdClientePk(obj[0] == null ? null : new BigDecimal(obj[0].toString()));
                reporteClienteVentas.setTotalMenudeoContado(obj[1] == null ? new BigDecimal("0") : new BigDecimal(obj[1].toString()));
                reporteClienteVentas.setTotalMenudeoCredito(obj[2] == null ? new BigDecimal("0") : new BigDecimal(obj[2].toString()));
                reporteClienteVentas.setUtilidadMenudeo(obj[3] == null ? new BigDecimal("0") : new BigDecimal(obj[3].toString()));
                reporteClienteVentas.setTotalMayoreoContado(obj[4] == null ? new BigDecimal("0") : new BigDecimal(obj[4].toString()));
                reporteClienteVentas.setTotalMayoreoCredito(obj[5] == null ? new BigDecimal("0") : new BigDecimal(obj[5].toString()));
                reporteClienteVentas.setUtilidadMayoreoCosto(obj[6] == null ? new BigDecimal("0") : new BigDecimal(obj[6].toString()));
                reporteClienteVentas.setUtilidadMayoreoComision(obj[7] == null ? new BigDecimal("0") : new BigDecimal(obj[7].toString()));
                reporteClienteVentas.setUtilidadMayoreoPacto(obj[8] == null ? new BigDecimal("0") : new BigDecimal(obj[8].toString()));
                reporteClienteVentas.setDiasRecuperacion(obj[9] == null ? new BigDecimal("0") : new BigDecimal(obj[9].toString()));
                reporteClienteVentas.setRecuperacion(obj[10] == null ? new BigDecimal("0") : new BigDecimal(obj[10].toString()));

                lstReporteClienteVentas.add(reporteClienteVentas);
            }

            return lstReporteClienteVentas;
        } catch (Exception ex) {
            Logger.getLogger(ServiceCatCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }
}
