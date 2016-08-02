/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.dominio.AbonoCredito;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.SaldosDeudas;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAbonoCredito;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceTipoAbono;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.TiempoUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author JesusAlfredo
 */
@Component
@Scope("view")
public class BeanCheques implements Serializable{

    private static final long serialVersionUID = 1L;
    @Autowired private IfaceTipoAbono ifaceTipoAbono;
    @Autowired private PlataformaSecurityContext context;
    @Autowired private IfaceAbonoCredito ifaceAbonoCredito;
    @Autowired private IfaceCredito ifaceCredito;

    private UsuarioDominio usuarioDominio;
    private AbonoCredito abonoCreditoPagar;

    private ArrayList<AbonoCredito> selectedchequesPendientes;
    private ArrayList<AbonoCredito> listaAbonosAtrasdos;
    private ArrayList<AbonoCredito> listaAbonosHoy;
    private ArrayList<AbonoCredito> listaAbonosSemana;
     private ArrayList<AbonoCredito> listaAbonos;
     
     Date fechaInicio;
     Date fechaFin;

    //---Variables de la vista---///
    private String title;
    private String viewEstate;
    //---Variables de la vista---///
    
    //---Constantes---//
    private static BigDecimal CREDITOFINALIZADO = new BigDecimal(2);
    private static BigDecimal CREDITOACTIVO = new BigDecimal(1);
    private static BigDecimal ABONOREALIZADO = new BigDecimal(1);
    private static BigDecimal ABONOPENDIENTE = new BigDecimal(2);
    //----Constantes--//
  
    @PostConstruct
    public void init() {
        fechaInicio = new Date();
        fechaFin = new Date();

//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
//        cal.clear(Calendar.MINUTE);
//        cal.clear(Calendar.SECOND);
//        cal.clear(Calendar.MILLISECOND);
//        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
//        Date dia = cal.getTime();
//        System.out.println("La semana Empieza en: " + dia);
//         fechaInicio= new Date();
//        Date fechaFin=dia;

        usuarioDominio = context.getUsuarioAutenticado();
        listaAbonosAtrasdos = new ArrayList<AbonoCredito>();
        listaAbonosHoy = new ArrayList<AbonoCredito>();
        listaAbonosSemana = new ArrayList<AbonoCredito>();
        usuarioDominio = context.getUsuarioAutenticado();
        selectedchequesPendientes = new ArrayList<AbonoCredito>();
        listaAbonos= new ArrayList<AbonoCredito>();
        listaAbonosAtrasdos = ifaceAbonoCredito.getChequesPendientes(fechaInicio,fechaFin);
        
                
        setTitle("Relación de Cheques No Cobrados ");
        setViewEstate("init");
    }
    
    public void pagarCheques() {
        if (!selectedchequesPendientes.isEmpty()) {
            for (AbonoCredito abonoCheque : selectedchequesPendientes) {
                BigDecimal totalAbonado = new BigDecimal(0);
                System.out.println("===============================================");
                System.out.println("Cheque: " + abonoCheque);
                abonoCheque.setEstatusAbono(new BigDecimal(1));
                BigDecimal totalAbonadoTemporal = new BigDecimal(0);
                if (ifaceAbonoCredito.update(abonoCheque) == 1) 
                {
                    //enseguida buscar si ya se libero el credito de ese abono.
                    Credito cTemporal = new Credito();
                    cTemporal = ifaceCredito.getTotalAbonado(abonoCheque.getIdCreditoFk());
                    totalAbonadoTemporal = cTemporal.getTotalAbonado().add(abonoCheque.getMontoAbono(), MathContext.UNLIMITED);
                    if (totalAbonadoTemporal.compareTo(cTemporal.getMontoCredito()) >= 0) 
                    {
                        ifaceCredito.updateStatus(abonoCheque.getIdCreditoFk(), CREDITOFINALIZADO);
                        JsfUtil.addSuccessMessageClean("Se ha liquidado el crédito exitosamente");
                        // Se liquido con un cheque todo ese credito 
                    }
                    JsfUtil.addSuccessMessageClean("Cheque Cobrado Existosamente");
                } else {
                    JsfUtil.addErrorMessageClean("Ocurrio un problema al cobrar los cheques");
                }
            }
            selectedchequesPendientes = new ArrayList<AbonoCredito>();
            listaAbonosAtrasdos = ifaceAbonoCredito.getChequesPendientes(fechaInicio,fechaFin);
        } else {
            JsfUtil.addErrorMessageClean("No existen cheques activos o no se ha seleccionado ninguno");
        }

    }
    public void buscaCheques() {

       listaAbonos = ifaceAbonoCredito.getChequesPendientes(fechaInicio,fechaFin);
    }

    public ArrayList<AbonoCredito> getListaAbonosAtrasdos() {
        return listaAbonosAtrasdos;
    }

    public void setListaAbonosAtrasdos(ArrayList<AbonoCredito> listaAbonosAtrasdos) {
        this.listaAbonosAtrasdos = listaAbonosAtrasdos;
    }

    public ArrayList<AbonoCredito> getListaAbonosHoy() {
        return listaAbonosHoy;
    }

    public void setListaAbonosHoy(ArrayList<AbonoCredito> listaAbonosHoy) {
        this.listaAbonosHoy = listaAbonosHoy;
    }

    public ArrayList<AbonoCredito> getListaAbonosSemana() {
        return listaAbonosSemana;
    }

    public void setListaAbonosSemana(ArrayList<AbonoCredito> listaAbonosSemana) {
        this.listaAbonosSemana = listaAbonosSemana;
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

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public AbonoCredito getAbonoCreditoPagar() {
        return abonoCreditoPagar;
    }

    public void setAbonoCreditoPagar(AbonoCredito abonoCreditoPagar) {
        this.abonoCreditoPagar = abonoCreditoPagar;
    }

    public ArrayList<AbonoCredito> getSelectedchequesPendientes() {
        return selectedchequesPendientes;
    }

    public void setSelectedchequesPendientes(ArrayList<AbonoCredito> selectedchequesPendientes) {
        this.selectedchequesPendientes = selectedchequesPendientes;
    }

    public ArrayList<AbonoCredito> getListaAbonos() {
        return listaAbonos;
    }

    public void setListaAbonos(ArrayList<AbonoCredito> listaAbonos) {
        this.listaAbonos = listaAbonos;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    

}
