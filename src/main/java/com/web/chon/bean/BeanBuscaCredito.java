/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.SaldosDeudas;
import com.web.chon.dominio.TipoAbono;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAbonoCredito;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceTipoAbono;
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author JesusAlfredo
 */
@Component
@Scope("view")
public class BeanBuscaCredito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    IfaceCatCliente ifaceCatCliente;
    @Autowired
    IfaceCredito ifaceCredito;
    @Autowired
    IfaceTipoAbono ifaceTipoAbono;
    @Autowired
    IfaceAbonoCredito ifaceAbonoCredito;
    @Autowired
    private PlataformaSecurityContext context;

    private BigDecimal idCliente;
    private String nombreCompletoCliente;
    private String rfcCliente;
    private BigDecimal creditoAutorizado;
    private BigDecimal creditoUtilizado;
    private BigDecimal creditoDisponible;
    private String tipoCliente;
    private String title;
    private String viewEstate;
    private BigDecimal totalCreditos;
    private BigDecimal totalAbonado;

    private ArrayList<SaldosDeudas> modelo;
    private ArrayList<TipoAbono> lstTipoAbonos;
    private ArrayList<Cliente> lstCliente;
    private SaldosDeudas dataAbonar;
    private AbonoCredito abono;
    private Cliente cliente;
    private String viewCheque;

    private UsuarioDominio usuarioDominio;
    private boolean bandera;

    @PostConstruct
    public void init() {
        bandera = false;
        abono = new AbonoCredito();
        usuarioDominio = context.getUsuarioAutenticado();
        dataAbonar = new SaldosDeudas();
        cliente = ifaceCatCliente.getClienteById(1);
        modelo = new ArrayList<SaldosDeudas>();
        lstTipoAbonos = ifaceTipoAbono.getAll();
        setTitle("Historial de Compras");
        setViewEstate("init");
        setViewCheque("init");
    }

    public void activaBandera() {
        bandera = true;
    }

    public void addView() {

        if (abono.getIdtipoAbonoFk().intValue() == 3) {
            setViewCheque("true");
        } else if (abono.getIdtipoAbonoFk().intValue() == 2) {
            setViewCheque("trans");

        } else {
            setViewCheque("init");
        }

    }

    public void abonar() {

        if (bandera != true) {
            AbonoCredito ac = new AbonoCredito();
            ac.setIdAbonoCreditoPk(new BigDecimal(ifaceAbonoCredito.getNextVal()));
            ac.setIdCreditoFk(dataAbonar.getFolioCredito());
            ac.setMontoAbono(abono.getMontoAbono());
            ac.setIdUsuarioFk(usuarioDominio.getIdUsuario()); //aqui poner el usuario looggeado
            ac.setIdtipoAbonoFk(abono.getIdtipoAbonoFk());
            if (abono.getIdtipoAbonoFk().intValue() == 3) {
                ac.setEstatusAbono(new BigDecimal(2));
                //Entra a estado 2 Que significa que esta pendiente.
            } else {
                //Quiere decir que se ejecute el abono.
                ac.setEstatusAbono(new BigDecimal(1));
            }

            ac.setNumeroCheque(abono.getNumeroCheque());
            ac.setLibrador(abono.getLibrador());
            ac.setFechaCobro(abono.getFechaCobro());
            ac.setBanco(abono.getBanco());
            ac.setFactura(abono.getFactura());
            ac.setReferencia(abono.getReferencia());
            ac.setConcepto(abono.getConcepto());
            ac.setFechaTransferencia(abono.getFechaTransferencia());
            //Despues sigue sumar 
            BigDecimal temporal = dataAbonar.getTotalAbonado().add(abono.getMontoAbono(), MathContext.UNLIMITED);

            if ((temporal).compareTo(dataAbonar.getSaldoTotal()) == 1 || (temporal).compareTo(dataAbonar.getSaldoTotal()) == 0) {

                System.out.println("Se liquido Todo cambiar a estatus 2 el credito");
                ifaceCredito.updateStatus(ac.getIdCreditoFk(), new BigDecimal(2));
                JsfUtil.addSuccessMessage("Se ha liquidado el crédito exitosamente");
            }
            if (ifaceAbonoCredito.insert(ac) == 1) {
                JsfUtil.addSuccessMessage("Se ha realizado un abono existosamente");
                searchByIdCliente();
                abono.reset();
                dataAbonar.reset();
            } else {
                JsfUtil.addErrorMessageClean("Ocurrio un error");
            }
        } else {

            for (SaldosDeudas item : modelo) 
            {
                BigDecimal AbonoGrande = abono.getMontoAbono();
                if (AbonoGrande.compareTo(new BigDecimal(0)) == 1) 
                {
                    AbonoCredito ac = new AbonoCredito();
                    ac.setIdAbonoCreditoPk(new BigDecimal(ifaceAbonoCredito.getNextVal()));
                    ac.setIdCreditoFk(item.getFolioCredito());
                    AbonoGrande = AbonoGrande.subtract(item.getSaldoLiquidar(), MathContext.UNLIMITED);
                    ac.setMontoAbono(item.getSaldoLiquidar());

                    ac.setIdUsuarioFk(usuarioDominio.getIdUsuario()); //aqui poner el usuario looggeado
                    ac.setIdtipoAbonoFk(abono.getIdtipoAbonoFk());
                    if (abono.getIdtipoAbonoFk().intValue() == 3) {
                        ac.setEstatusAbono(new BigDecimal(2));
                        //Entra a estado 2 Que significa que esta pendiente.
                    } else {
                        //Quiere decir que se ejecute el abono.
                        ac.setEstatusAbono(new BigDecimal(1));
                    }
                    ac.setNumeroCheque(abono.getNumeroCheque());
                    ac.setLibrador(abono.getLibrador());
                    ac.setFechaCobro(abono.getFechaCobro());
                    ac.setBanco(abono.getBanco());
                    ac.setFactura(abono.getFactura());
                    ac.setReferencia(abono.getReferencia());
                    ac.setConcepto(abono.getConcepto());
                    ac.setFechaTransferencia(abono.getFechaTransferencia());
                    BigDecimal temporal = item.getTotalAbonado().add(abono.getMontoAbono(), MathContext.UNLIMITED);
                    if ((temporal).compareTo(item.getSaldoTotal()) == 1 || (temporal).compareTo(item.getSaldoTotal()) == 0) {
                        System.out.println("Se liquido Todo cambiar a estatus 2 el credito");
                        ifaceCredito.updateStatus(ac.getIdCreditoFk(), new BigDecimal(2));
                        JsfUtil.addSuccessMessageClean("Se ha liquidado el crédito exitosamente");
                    }
                    if (ifaceAbonoCredito.insert(ac) == 1)
                    {
                        JsfUtil.addSuccessMessageClean("Se ha realizado un abono existosamente");
                        searchByIdCliente();
                        //abono.reset();
                        //dataAbonar.reset();
                    } else {
                        JsfUtil.addErrorMessageClean("Ocurrio un error");
                    }
                }//fin if
            }//fin for

            System.out.println("Va a ser un abono moustro");
        }

//RequestContext.getCurrentInstance().execute("PF('dlg').show();"); 
    }

    public void searchByIdCliente() {
        cliente = ifaceCatCliente.getClienteCreditoById(cliente.getId_cliente().intValue());
        if (cliente != null && cliente.getId_cliente() != null) {
            modelo = ifaceCredito.getCreditosActivos(cliente.getId_cliente());
        } else {
            JsfUtil.addWarnMessage("El cliente no cuenta con credito");
            modelo = new ArrayList<SaldosDeudas>();
            cliente = new Cliente();
        }

    }
    public ArrayList<Cliente> autoCompleteCliente(String nombreCliente) {
        lstCliente = ifaceCatCliente.getClienteByNombreCompleto(nombreCliente.toUpperCase());
        return lstCliente;

    }

    public BigDecimal getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(BigDecimal idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCompletoCliente() {
        return nombreCompletoCliente;
    }

    public void setNombreCompletoCliente(String nombreCompletoCliente) {
        this.nombreCompletoCliente = nombreCompletoCliente;
    }

    public String getRfcCliente() {
        return rfcCliente;
    }

    public void setRfcCliente(String rfcCliente) {
        this.rfcCliente = rfcCliente;
    }

    public BigDecimal getCreditoAutorizado() {
        return creditoAutorizado;
    }

    public void setCreditoAutorizado(BigDecimal creditoAutorizado) {
        this.creditoAutorizado = creditoAutorizado;
    }

    public BigDecimal getCreditoUtilizado() {
        return creditoUtilizado;
    }

    public void setCreditoUtilizado(BigDecimal creditoUtilizado) {
        this.creditoUtilizado = creditoUtilizado;
    }

    public BigDecimal getCreditoDisponible() {
        return creditoDisponible;
    }

    public void setCreditoDisponible(BigDecimal creditoDisponible) {
        this.creditoDisponible = creditoDisponible;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
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

    public ArrayList<SaldosDeudas> getModelo() {
        return modelo;
    }

    public void setModelo(ArrayList<SaldosDeudas> modelo) {
        this.modelo = modelo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public SaldosDeudas getDataAbonar() {
        return dataAbonar;
    }

    public void setDataAbonar(SaldosDeudas dataAbonar) {
        this.dataAbonar = dataAbonar;
    }

    public BigDecimal getTotalCreditos() {
        return totalCreditos;
    }

    public void setTotalCreditos(BigDecimal totalCreditos) {
        this.totalCreditos = totalCreditos;
    }

    public BigDecimal getTotalAbonado() {
        return totalAbonado;
    }

    public void setTotalAbonado(BigDecimal totalAbonado) {
        this.totalAbonado = totalAbonado;
    }

    public ArrayList<TipoAbono> getLstTipoAbonos() {
        return lstTipoAbonos;
    }

    public void setLstTipoAbonos(ArrayList<TipoAbono> lstTipoAbonos) {
        this.lstTipoAbonos = lstTipoAbonos;
    }

    public AbonoCredito getAbono() {
        return abono;
    }

    public void setAbono(AbonoCredito abono) {
        this.abono = abono;
    }

    public String getViewCheque() {
        return viewCheque;
    }

    public void setViewCheque(String viewCheque) {
        this.viewCheque = viewCheque;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public ArrayList<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(ArrayList<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

}
