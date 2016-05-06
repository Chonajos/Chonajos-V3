/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.EntradaMercancia2;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.TipoVenta;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.VentaMayoreo;
import com.web.chon.dominio.VentaProductoMayoreo;
import com.web.chon.security.service.PlataformaSecurityContext;

import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceTipoVenta;
import com.web.chon.service.IfaceVentaMayoreo;
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
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
public class BeanVentaMayoreo implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceVentaMayoreo ifaceVentaMayoreo;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    IfaceCatCliente ifaceCatCliente;
    @Autowired
    IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private IfaceNegocioExistencia ifaceNegocioExistencia;
    @Autowired
    private IfaceTipoVenta ifaceTipoVenta;
    @Autowired
    private PlataformaSecurityContext context;

    private ArrayList<TipoVenta> lstTipoVenta;
    private ArrayList<Cliente> lstCliente;
    private ArrayList<Usuario> lstUsuario;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<ExistenciaProducto> lstExistencias;
    private ArrayList<VentaProductoMayoreo> lstVenta;
    private ExistenciaProducto selectedExistencia;

    private UsuarioDominio usuarioDominio;

    private Cliente cliente;
    private Usuario usuario;
    private Subproducto subProducto;
    private VentaProductoMayoreo data;
    private EntradaMercancia2 entradaMercancia;
    private ExistenciaProducto ep;
    private VentaMayoreo ventaGeneral;
    private VentaProductoMayoreo dataRemove;
    private VentaProductoMayoreo dataEdit;

    private BigDecimal totalVenta;
    private int idSucu;
    private BigDecimal idExistencia;
    private BigDecimal idTipoVenta;

    private String title = "";
    private String viewEstate = "";

    private BigDecimal TotalVentaGeneral;

    @PostConstruct
    public void init() {

        ventaGeneral = new VentaMayoreo();
        usuario = new Usuario();
        usuarioDominio = context.getUsuarioAutenticado();
        idSucu = usuarioDominio.getSucId();
        usuario.setIdUsuarioPk(usuarioDominio.getIdUsuario());
        usuario.setNombreUsuario(usuarioDominio.getUsuNombre());
        usuario.setApaternoUsuario(usuarioDominio.getUsuPaterno());
        usuario.setAmaternoUsuario(usuarioDominio.getUsuMaterno());
        TotalVentaGeneral = new BigDecimal(0);
        data = new VentaProductoMayoreo();
        setTitle("Venta Mayoreo");
        setViewEstate("viewAddProducto");
        lstTipoVenta = ifaceTipoVenta.getAll();
        lstVenta = new ArrayList<VentaProductoMayoreo>();
    }

    public void inserts() {
        ventaGeneral.setIdVentaMayoreoPk(new BigDecimal(ifaceVentaMayoreo.getNextVal()));
        ventaGeneral.setIdtipoVentaFk(idTipoVenta);
        ventaGeneral.setIdClienteFk(new BigDecimal(cliente.getId_cliente()));
        ventaGeneral.setIdSucursalFk(new BigDecimal(idSucu));
        ventaGeneral.setIdVendedorFK(usuario.getIdUsuarioPk());

        System.out.println("Venta General: " + ventaGeneral.toString());

        if (ifaceVentaMayoreo.insertarVenta(ventaGeneral) != 0) {
            //si la venta se ingreso el siguiente paso es ingresar los productos.

        }
    }

    public void changeViewToAddProducto() {
        setViewEstate("viewAddProducto");
    }

    public void changeView() {
        setViewEstate("viewCarrito");
    }

    public void buscaExistencias() {
        BigDecimal idEntrada;
        if (entradaMercancia == null) {
            idEntrada = null;
        } else {
            idEntrada = entradaMercancia.getIdEmPK();
        }

        String idproductito = subProducto == null ? null : subProducto.getIdSubproductoPk();
        lstExistencias = ifaceNegocioExistencia.getExistencias(null, null, null, idproductito, null, null, null);

    }

    public void addProducto() {
        if (selectedExistencia == null || data.getCantidadEmpaque().intValue() == 0 || data.getKilosVendidos().intValue() == 0) {
            JsfUtil.addErrorMessage("Seleccione un Producto de la tabla, o agrego una cantidad o peso en 0 Kg.");

        } else //            System.out.println("idSubProducto:" + selectedExistencia.getIdSubProductoFK());
        if (selectedExistencia.getPrecioVenta() == null) {
            JsfUtil.addErrorMessage("No se tiene precio de venta para este producto. Contactar al administrador.");
        } else if (data.getPrecioProducto().intValue() < selectedExistencia.getPrecioMinimo().intValue() || data.getPrecioProducto().intValue() > selectedExistencia.getPrecioMaximo().intValue()) {
            JsfUtil.addErrorMessage("Precio de Venta fuera de Rango \n Precio Maximo =" + selectedExistencia.getPrecioMaximo() + " Precio minimo =" + selectedExistencia.getPrecioMinimo());
        } else if (data.getCantidadEmpaque().intValue() > selectedExistencia.getCantidadPaquetes().intValue()) {
            JsfUtil.addErrorMessage("Cantidad de Empaque insuficiente");
        } else if (data.getKilosVendidos().intValue() > selectedExistencia.getKilosTotalesProducto().intValue()) {
            JsfUtil.addErrorMessage("Cantidad de Kilos insuficiente");
        } else if (selectedExistencia.isEstatusBloqueo()) {
            JsfUtil.addErrorMessage("Producto Bloqueado, contactar al administrador");
        } else if (lstVenta.isEmpty()) {
//                System.out.println("Lista vacia agregando producto");
            add();
            limpia();
        } else {
            for (int i = 0; i < lstVenta.size(); i++) {
                VentaProductoMayoreo productoRepetido = lstVenta.get(i);
//                    System.out.println("Recorriendo lista Venta");
//                    System.out.println("repetido id: " + productoRepetido.getIdExistenciaFk() + " nuevo: " + selectedExistencia.getIdExistenciaProductoPk());
                if (productoRepetido.getIdExistenciaFk().equals(selectedExistencia.getIdExistenciaProductoPk())) {
//                        System.out.println("Encontro producto repetido verificando existencias");
                    BigDecimal enlista = productoRepetido.getCantidadEmpaque();
                    BigDecimal totalexistencia = selectedExistencia.getCantidadPaquetes();
                    BigDecimal suma = enlista.add(data.getCantidadEmpaque(), MathContext.UNLIMITED);

//                        System.out.println("Total Existencia: " + totalexistencia);
//                        System.out.println("Total en Lista: " + enlista);
//                        System.out.println("Total Suma: " + suma);
//                        System.out.println("Total nuevo: " + data.getCantidadEmpaque());
                    BigDecimal kilosnelista = productoRepetido.getKilosVendidos();
                    BigDecimal kilostotalexistencia = selectedExistencia.getKilosTotalesProducto();
                    BigDecimal kilossuma = kilosnelista.add(data.getKilosVendidos(), MathContext.UNLIMITED);

                    if (suma.intValue() > totalexistencia.intValue() || kilossuma.intValue() > kilostotalexistencia.intValue()) {
                        //System.out.println("No alcanzan existencias");
                        JsfUtil.addErrorMessage("Producto repetido, no alcanzan las existencias.\n Cantidad: " + selectedExistencia.getCantidadPaquetes() + "\nKilos: " + selectedExistencia.getKilosTotalesProducto());
                    } else {
                        lstVenta.remove(i);
                        TotalVentaGeneral = TotalVentaGeneral.subtract(productoRepetido.getTotalVenta(), MathContext.UNLIMITED);

                        productoRepetido.setCantidadEmpaque(suma);
                        productoRepetido.setKilosVendidos(kilossuma);
                        productoRepetido.setTotalVenta(productoRepetido.getPrecioProducto().multiply(productoRepetido.getKilosVendidos(), MathContext.UNLIMITED));
                        TotalVentaGeneral = TotalVentaGeneral.add(productoRepetido.getTotalVenta(), MathContext.UNLIMITED);
                        lstVenta.add(productoRepetido);
                        limpia();
                        //System.out.println("Existencias suficientes, producto actualizado");
                        JsfUtil.addSuccessMessage("Producto Actualizado");

                    }
                } else {
                    System.out.println("No encontro repetidos");
                    if (selectedExistencia.getIdExistenciaProductoPk() != null) {
                        //System.out.println("producto Agregado");
                        add();
                        limpia();
                    }

///
                }

            }
        }

    }

    public void add() {
        data.setIdExistenciaFk(selectedExistencia.getIdExistenciaProductoPk());
        data.setIdEntradaMercanciaFk(selectedExistencia.getIdEmFK());
        data.setIdTipoEmpaqueFk(selectedExistencia.getIdTipoEmpaqueFK());
        data.setIdSubProductofk(selectedExistencia.getIdSubProductoFK());
        data.setNombreEmpaque(selectedExistencia.getNombreEmpaque());
        data.setNombreProducto(selectedExistencia.getNombreProducto());
        data.setClave(selectedExistencia.getIdentificador());
        data.setTotalVenta(data.getPrecioProducto().multiply(data.getKilosVendidos(), MathContext.UNLIMITED));
        VentaProductoMayoreo productoTemporal = new VentaProductoMayoreo();
        productoTemporal.setIdEntradaMercanciaFk(data.getIdEntradaMercanciaFk());
        productoTemporal.setIdTipoEmpaqueFk(data.getIdTipoEmpaqueFk());
        productoTemporal.setIdSubProductofk(data.getIdSubProductofk());
        productoTemporal.setNombreEmpaque(data.getNombreEmpaque());
        productoTemporal.setNombreProducto(data.getNombreProducto());
        productoTemporal.setCantidadEmpaque(data.getCantidadEmpaque());
        productoTemporal.setPrecioProducto(data.getPrecioProducto());
        productoTemporal.setClave(data.getClave());
        productoTemporal.setTotalVenta(data.getTotalVenta());
        productoTemporal.setKilosVendidos(data.getKilosVendidos());
        productoTemporal.setIdExistenciaFk(data.getIdExistenciaFk());
        TotalVentaGeneral = TotalVentaGeneral.add(productoTemporal.getTotalVenta(), MathContext.UNLIMITED);
        lstVenta.add(productoTemporal);

    }

    public void limpia() {
        selectedExistencia = new ExistenciaProducto();
        lstExistencias = new ArrayList<ExistenciaProducto>();
        data.reset();
        JsfUtil.addSuccessMessage("Producto Agregado al Pedido Correctamente");
        subProducto = new Subproducto();
        setViewEstate("viewAddProducto");

    }

    public ArrayList<TipoVenta> getLstTipoVenta() {
        return lstTipoVenta;
    }

    public void setLstTipoVenta(ArrayList<TipoVenta> lstTipoVenta) {
        this.lstTipoVenta = lstTipoVenta;
    }

    public void editProducto() {

    }

    public void remove() {

    }

    public VentaProductoMayoreo getDataRemove() {
        return dataRemove;
    }

    public void setDataRemove(VentaProductoMayoreo dataRemove) {
        this.dataRemove = dataRemove;
    }

    public VentaProductoMayoreo getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(VentaProductoMayoreo dataEdit) {
        this.dataEdit = dataEdit;
    }

    public ExistenciaProducto getEp() {
        return ep;
    }

    public void setEp(ExistenciaProducto ep) {
        this.ep = ep;
    }

    public ArrayList<Cliente> autoCompleteCliente(String nombreCliente) {
        lstCliente = ifaceCatCliente.getClienteByNombreCompleto(nombreCliente.toUpperCase());
        return lstCliente;

    }

    public ArrayList<Usuario> autoCompleteVendedor(String nombreUsuario) {
        lstUsuario = ifaceCatUsuario.getUsuarioByNombreCompleto(nombreUsuario.toUpperCase(), idSucu);
        return lstUsuario;

    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public BigDecimal getTotalVentaGeneral() {
        return TotalVentaGeneral;
    }

    public void setTotalVentaGeneral(BigDecimal TotalVentaGeneral) {
        this.TotalVentaGeneral = TotalVentaGeneral;
    }

    public ArrayList<ExistenciaProducto> getLstExistencias() {
        return lstExistencias;
    }

    public void setLstExistencias(ArrayList<ExistenciaProducto> lstExistencias) {
        this.lstExistencias = lstExistencias;
    }

    public ArrayList<VentaProductoMayoreo> getLstVenta() {
        return lstVenta;
    }

    public void setLstVenta(ArrayList<VentaProductoMayoreo> lstVenta) {
        this.lstVenta = lstVenta;
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String insert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchById() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewEstate() {
        return viewEstate;
    }

    public void setViewEstate(String viewEstate) {
        this.viewEstate = viewEstate;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(ArrayList<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Usuario> getLstUsuario() {
        return lstUsuario;
    }

    public void setLstUsuario(ArrayList<Usuario> lstUsuario) {
        this.lstUsuario = lstUsuario;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public BigDecimal getIdTipoVenta() {
        return idTipoVenta;
    }

    public void setIdTipoVenta(BigDecimal idTipoVenta) {
        this.idTipoVenta = idTipoVenta;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

    public int getIdSucu() {
        return idSucu;
    }

    public void setIdSucu(int idSucu) {
        this.idSucu = idSucu;
    }

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public VentaProductoMayoreo getData() {
        return data;
    }

    public void setData(VentaProductoMayoreo data) {
        this.data = data;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public EntradaMercancia2 getEntradaMercancia() {
        return entradaMercancia;
    }

    public void setEntradaMercancia(EntradaMercancia2 entradaMercancia) {
        this.entradaMercancia = entradaMercancia;
    }

    public BigDecimal getIdExistencia() {
        return idExistencia;
    }

    public void setIdExistencia(BigDecimal idExistencia) {
        this.idExistencia = idExistencia;
    }

    public ExistenciaProducto getSelectedExistencia() {
        return selectedExistencia;
    }

    public void setSelectedExistencia(ExistenciaProducto selectedExistencia) {
        this.selectedExistencia = selectedExistencia;
    }

    public VentaMayoreo getVentaGeneral() {
        return ventaGeneral;
    }

    public void setVentaGeneral(VentaMayoreo ventaGeneral) {
        this.ventaGeneral = ventaGeneral;
    }

}
