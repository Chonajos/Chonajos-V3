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
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceTipoAbono;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class BeanBuscaCredito implements Serializable{
    private static final long serialVersionUID = 1L;
    @Autowired IfaceCatCliente ifaceCatCliente;
    @Autowired IfaceCredito ifaceCredito;
    @Autowired IfaceTipoAbono ifaceTipoAbono;
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
    private SaldosDeudas dataAbonar;
    private AbonoCredito abono;
    Cliente cliente;
    private String viewCheque;

    
    @PostConstruct
    public void init() 
    {
        abono = new AbonoCredito();
        dataAbonar = new SaldosDeudas();
                dataAbonar.setFolioCredito(new BigDecimal("01"));

        cliente = new Cliente();
        modelo = new ArrayList<SaldosDeudas>();
        lstTipoAbonos = ifaceTipoAbono.getAll();
        setTitle("Abono de Créditos");
        setViewEstate("init");
        setViewCheque("init");
    }
    public void addView()
    {
  
        if(abono.getIdtipoAbonoFk().intValue() == 3)
           {
               setViewCheque("true");
           } 
        else if(abono.getIdtipoAbonoFk().intValue() == 2)
            {
            setViewCheque("trans");

            } 
        else
            {
                setViewCheque("init");
            }
            

    }       
          
    public void abonar()
    {
//        dataAbonar.setFolioCredito(new BigDecimal("11"));
        System.out.println("abonar show: "+dataAbonar.toString());
        
         RequestContext.getCurrentInstance().execute("PF('dlg').show();");
//         dataAbonar.setFolioCredito(new BigDecimal("10"));
        
    }
    public void searchByIdCliente()
    {
        cliente = ifaceCatCliente.getClienteCreditoById(cliente.getId_cliente().intValue());
        modelo = ifaceCredito.getCreditosActivos(cliente.getId_cliente());
        
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

    

    
    
    
}
