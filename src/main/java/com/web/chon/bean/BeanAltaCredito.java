package com.web.chon.bean;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCredito;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para la venta de productos
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanAltaCredito implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceCredito ifaceCredito;
    @Autowired
    private IfaceCatCliente ifaceCatCliente;
    @Autowired
    private PlataformaSecurityContext context;

    private ArrayList<Usuario> lstUsuario;
    private ArrayList<Cliente> lstCliente;

    private Credito credito;
    private Usuario usuario;
    private Cliente cliente;
    private UsuarioDominio usuarioDominio;
    private String title = "";
    private String viewEstate = "";
    private BigDecimal DIAS_PLAZO = new BigDecimal("7");
    private int idSucu;
    Date date = new Date();

    @PostConstruct
    public void init() {

        date = context.getFechaSistema();
        usuario = new Usuario();

        usuarioDominio = context.getUsuarioAutenticado();

        idSucu = usuarioDominio.getSucId();
        cliente = new Cliente();
        credito = new Credito();
        autoCompleteCliente("");
        setTitle("Alta de Creditos.");
        setViewEstate("init");

    }

    public ArrayList<Cliente> autoCompleteCliente(String nombreCliente) {
        lstCliente = ifaceCatCliente.getClienteByNombreCompleto(nombreCliente.toUpperCase());
        return lstCliente;

    }

    @Override
    public String insert() {

        return null;

    }

    public void insertaCredito() {

        credito.setIdClienteFk(cliente.getId_cliente());
        //folio de la venta de menudeo (es el folio general no el de sucursal)
        credito.setIdVentaMenudeo(null);
        //Uusuario que realiza la venta
        credito.setIdUsuarioCredito(usuarioDominio.getIdUsuario());
        //tipo de pago semanal, mensual etc
        credito.setIdTipoCreditoFk(new BigDecimal(28));
        //estatus de credito en activo =1
        credito.setEstatusCredito(new BigDecimal("1"));
        //Numero de veces que el cliente a prometido pagar, al realizar la venta seria la primera promesa
        credito.setNumeroPromesaPago(new BigDecimal("1"));
        //el dia de la compra a credito
//        credito.setFechaInicioCredito(fechaActual);
        // Fecha en la que el cliente promete liquidar los pagos del credito
        credito.setFechaPromesaPago(TiempoUtil.sumarRestarDias(credito.getFechaInicioCredito(), 28));
        //el procentaje de interes que se le cobrara al cliente por la compra
        credito.setTazaInteres(new BigDecimal("0.6"));
        //Lo que el cliente deja a cuenta
        credito.setDejaCuenta(new BigDecimal(0));
        //Estatus si se a cobrado lo que el usuario a dejado a cuenta al momento de la venta es 0 (que no se a cobrado).
        credito.setStatusACuenta(new BigDecimal("0"));
//        credito.setMontoCredito(totalVentaDescuento.subtract(dejaACuenta).setScale(2, RoundingMode.UP));
        //Numero de pagos que el cliente debera realizar
        credito.setPlasos(new BigDecimal(28));
        //El numero de dias del plaso
        credito.setNumeroPagos(new BigDecimal(1));
        if (ifaceCredito.insert(credito) == 0) {
            JsfUtil.addErrorMessage("Error al Agregar");
        } else {
            credito.reset();
            JsfUtil.addSuccessMessage("Agregado Exitosamente");

        }
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

    public ArrayList<Usuario> getLstUsuario() {
        return lstUsuario;
    }

    public void setLstUsuario(ArrayList<Usuario> lstUsuario) {
        this.lstUsuario = lstUsuario;
    }

    public ArrayList<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(ArrayList<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getIdSucu() {
        return idSucu;
    }

    public void setIdSucu(int idSucu) {
        this.idSucu = idSucu;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    @Override
    public String delete() {
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

}
