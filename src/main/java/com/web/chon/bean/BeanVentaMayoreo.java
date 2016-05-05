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
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.dominio.VentaProductoMayoreo;

import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceSubProducto;
import java.io.Serializable;
import java.math.BigDecimal;
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
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    IfaceCatCliente ifaceCatCliente;
    @Autowired
    IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private IfaceNegocioExistencia ifaceNegocioExistencia;
    
    
    private ArrayList<Cliente> lstCliente;
    private ArrayList<Usuario> lstUsuario;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<ExistenciaProducto> lstExistencias;
    private ArrayList<VentaProductoMayoreo> lstVenta;
    private ArrayList<ExistenciaProducto> selectedExistencia;
    
    
    private UsuarioDominio usuarioDominio;
    
    private Cliente cliente;
    private Usuario usuario;
    private Subproducto subProducto;
    private VentaProductoMayoreo data;
    private EntradaMercancia2 entradaMercancia;
    private ExistenciaProducto ep;
    
    private VentaProductoMayoreo dataRemove;
    private VentaProductoMayoreo dataEdit;
    
    
    private BigDecimal totalVenta;
    private int idSucu;
    private BigDecimal idExistencia;
    
    private String title = "";
    private String viewEstate = "";
    
    @PostConstruct
    public void init() {
        //idSucu = usuarioDominio.getSucId();
        data = new VentaProductoMayoreo();
        setTitle("Venta Mayoreo");
        setViewEstate("viewAddProducto");
    }
    public void inserts()
    {
        
    }
    public void changeView()
    {
        setViewEstate("viewCarrito");
    }
    public void buscaExistencias() 
    {
        BigDecimal idEntrada;
        if (entradaMercancia == null) {
            idEntrada = null;
        } else {
            idEntrada = entradaMercancia.getIdEmPK();
        }

        System.out.println("Bandera");
        String idproductito = subProducto == null ? null : subProducto.getIdSubproductoPk();
        lstExistencias = ifaceNegocioExistencia.getExistencias(null, null,null, idproductito,null, null, null);

    }
    public void addProducto()
    {
        setViewEstate("viewAddProducto");
        
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

    public ArrayList<ExistenciaProducto> getSelectedExistencia() {
        return selectedExistencia;
    }

    public void setSelectedExistencia(ArrayList<ExistenciaProducto> selectedExistencia) {
        this.selectedExistencia = selectedExistencia;
    }
    
    
    
    
}
