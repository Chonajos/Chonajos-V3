package com.web.chon.bean;

import com.web.chon.dominio.AcionGestion;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.GestionCredito;
import com.web.chon.dominio.ResultadoGestion;
import com.web.chon.dominio.SaldosDeudas;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAcionGestion;
import com.web.chon.service.IfaceCredito;
import com.web.chon.service.IfaceGestionCredito;
import com.web.chon.service.IfaceResultadoGestion;
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
 *
 * @author Juan De la Cruz
 */
@Component
@Scope("view")
public class BeanConsultaCredito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    IfaceCredito ifaceCredito;
    @Autowired
    PlataformaSecurityContext context;
    @Autowired
    IfaceAcionGestion ifaceAcionGestion;
    @Autowired
    IfaceGestionCredito ifaceGestionCredito;
    @Autowired
    IfaceResultadoGestion ifaceResultadoGestion;

    private String title;
    private String viewEstate;

    private Date fechaSystema;

    private ArrayList<SaldosDeudas> modelo;
    private ArrayList<AcionGestion> lstAcionGestion;
    private ArrayList<ResultadoGestion> lstResultadoGestion;

    private SaldosDeudas data;
    private UsuarioDominio usuarioDominio;
    private GestionCredito gestionCredito;

    private Date reprogramarFecha;

    private BigDecimal idResultadoGestio;

    private int numDias;
    private int numFiltro;
    private int UNO = 1;

    @PostConstruct
    public void init() {

        data = new SaldosDeudas();
        gestionCredito = new GestionCredito();
        modelo = new ArrayList<SaldosDeudas>();
        lstAcionGestion = new ArrayList<AcionGestion>();
        lstResultadoGestion = new ArrayList<ResultadoGestion>();

        fechaSystema = context.getFechaSistema();
        usuarioDominio = context.getUsuarioAutenticado();
        setTitle("Consulta de Crédito");
        setViewEstate("init");
    }

    public void backView() {
        setTitle("Consulta de Crédito");
        setViewEstate("init");
    }

    public void consultaCredito() {
        modelo = ifaceCredito.getCreditosByEstatus(numFiltro, numDias);
        ArrayList<SaldosDeudas> modelTemp = new ArrayList<SaldosDeudas>();

        BigDecimal zero = new BigDecimal(0);
        int diasDiferencia = 0;
        Date fechaTemporal = null;

        for (SaldosDeudas saldos : modelo) {
            switch (numFiltro) {
                case 1:
                    if (saldos.getPeriodosAtraso().equals(zero)) {
                        diasDiferencia = TiempoUtil.diferenciasDeFechas(fechaSystema, saldos.getFechaProximaAbonar());

                        if (diasDiferencia <= numDias) {
                            saldos.setDiasAtraso(Integer.toString(0));
                            saldos.setStatusFechaProxima(new BigDecimal(UNO));
                            modelTemp.add(saldos);
                        }

                    }
                    break;
                case 2:
                    if (saldos.getPeriodosAtraso().compareTo(zero) == UNO && saldos.getPeriodosAtraso().compareTo(saldos.getNumeroPagos()) == -UNO) {
                        diasDiferencia = TiempoUtil.diferenciasDeFechas(fechaSystema, saldos.getFechaProximaAbonar());
                        diasDiferencia = (saldos.getPeriodosAtraso().intValue() - UNO) * (saldos.getPlazo().divide(saldos.getNumeroPagos())).intValue();

                        fechaTemporal = TiempoUtil.sumarRestarDias(saldos.getFechaVenta(), diasDiferencia);
                        diasDiferencia = TiempoUtil.diferenciasDeFechas(fechaTemporal, fechaSystema) + diasDiferencia;
                        diasDiferencia -= (saldos.getPlazo().divide(saldos.getNumeroPagos())).intValue();
                        if (diasDiferencia <= numDias) {
                            saldos.setDiasAtraso(Integer.toString(diasDiferencia));
                            saldos.setStatusFechaProxima(new BigDecimal(2));
                            modelTemp.add(saldos);
                        }

                    }
                    break;
                case 3:
                    if (saldos.getPeriodosAtraso().equals(saldos.getNumeroPagos())) {
                        diasDiferencia = TiempoUtil.diferenciasDeFechas(saldos.getFechaPromesaFinPago(), fechaSystema);

                        if (diasDiferencia <= numDias) {
                            saldos.setDiasAtraso(Integer.toString(diasDiferencia));
                            saldos.setStatusFechaProxima(new BigDecimal(4));
                            modelTemp.add(saldos);
                        }

                    }
                    break;
            }

        }

        if (modelTemp.isEmpty()) {
            JsfUtil.addWarnMessage("No se Encontraron Registros.");
            modelo.clear();
        } else {
            modelo = modelTemp;
        }

    }

    public void search() {
        setTitle("Gestion de Credito");
        lstResultadoGestion = ifaceResultadoGestion.getAll();
        idResultadoGestio = idResultadoGestio == null ? lstResultadoGestion.get(0).getIdResultadoGestion() : idResultadoGestio;
        obetenerAcionGestio();
        setViewEstate("search");
    }

    public void obetenerAcionGestio() {
        System.out.println("idResultadoGestio :" + idResultadoGestio);
        lstAcionGestion = ifaceAcionGestion.getByIdResultadoGestion(idResultadoGestio);
    }

    public void save() {
        BigDecimal tres = new BigDecimal(3);
        int tresInt = 3;
        try {
            gestionCredito.setIdCredito(data.getFolioCredito());
            gestionCredito.setIdUsario(usuarioDominio.getIdUsuario());
            if (ifaceGestionCredito.insert(gestionCredito) == UNO) {
                if (reprogramarFecha != null && numFiltro == tresInt) {
                    Credito credito = new Credito();

                    //Se optiene el credito para modificar la fecha promesa de pago
                    System.out.println("data.getFolioCredito() "+data.getFolioCredito());
                    credito = ifaceCredito.getById(data.getFolioCredito());
                    System.out.println("credito :"+credito.toString());
                    credito.setFechaPromesaPago(reprogramarFecha);
                    credito.setNumeroPromesaPago(credito.getNumeroPromesaPago().add(new BigDecimal(UNO)));

                    if (ifaceCredito.update(credito) == UNO) {
                        JsfUtil.addSuccessMessage("Registro Actualizado Correctamente.");

                    } else {
                        JsfUtil.addErrorMessage("Ocurrio un error al intentar modificar la fecha proxima de pago.");
                    }
                } else {
                    JsfUtil.addSuccessMessage("Registro Actualizado Correctamente.");
                }

                init();
            } else {
                JsfUtil.addErrorMessage("Ocurrio un error al insertar el registro.");
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Ocurrio un error al insertar el registro. " + ex.toString());
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

    public ArrayList<SaldosDeudas> getModelo() {
        return modelo;
    }

    public void setModelo(ArrayList<SaldosDeudas> modelo) {
        this.modelo = modelo;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(UsuarioDominio usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    public int getNumFiltro() {
        return numFiltro;
    }

    public void setNumFiltro(int numFiltro) {
        this.numFiltro = numFiltro;
    }

    public int getNumDias() {
        return numDias;
    }

    public void setNumDias(int numDias) {
        this.numDias = numDias;
    }

    public SaldosDeudas getData() {
        return data;
    }

    public void setData(SaldosDeudas data) {
        this.data = data;
    }

    public ArrayList<AcionGestion> getLstAcionGestion() {
        return lstAcionGestion;
    }

    public void setLstAcionGestion(ArrayList<AcionGestion> lstAcionGestion) {
        this.lstAcionGestion = lstAcionGestion;
    }

    public ArrayList<ResultadoGestion> getLstResultadoGestion() {
        return lstResultadoGestion;
    }

    public void setLstResultadoGestion(ArrayList<ResultadoGestion> lstResultadoGestion) {
        this.lstResultadoGestion = lstResultadoGestion;
    }

    public GestionCredito getGestionCredito() {
        return gestionCredito;
    }

    public void setGestionCredito(GestionCredito gestionCredito) {
        this.gestionCredito = gestionCredito;
    }

    public Date getReprogramarFecha() {
        return reprogramarFecha;
    }

    public void setReprogramarFecha(Date reprogramarFecha) {
        this.reprogramarFecha = reprogramarFecha;
    }

    public BigDecimal getIdResultadoGestio() {
        return idResultadoGestio;
    }

    public void setIdResultadoGestio(BigDecimal idResultadoGestio) {
        this.idResultadoGestio = idResultadoGestio;
    }

}
