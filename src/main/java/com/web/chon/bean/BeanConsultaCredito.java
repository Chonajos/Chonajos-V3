package com.web.chon.bean;

import com.web.chon.dominio.AcionGestion;
import com.web.chon.dominio.Credito;
import com.web.chon.dominio.GestionCredito;
import com.web.chon.dominio.ResultadoGestion;
import com.web.chon.dominio.SaldosDeudas;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceAcionGestion;
import com.web.chon.service.IfaceCatSucursales;
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
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;

    private String title;
    private String viewEstate;

    private Date fechaSystema;

    private ArrayList<SaldosDeudas> modelo;
    private ArrayList<AcionGestion> lstAcionGestion;
    private ArrayList<ResultadoGestion> lstResultadoGestion;
    private ArrayList<Sucursal> lstSucursales;

    private SaldosDeudas data;
    private UsuarioDominio usuario;
    private UsuarioDominio usuarioDominio;
    private GestionCredito gestionCredito;

    private Date reprogramarFecha;

    private BigDecimal idResultadoGestio;
    private BigDecimal totalLiquidar;
    private BigDecimal idSucursal;
    private BigDecimal tipoVenta;

    private int numDias;
    private int numFiltro;

    private int UNO = 1;

    @PostConstruct
    public void init() {

        data = new SaldosDeudas();
        gestionCredito = new GestionCredito();
        modelo = new ArrayList<SaldosDeudas>();
        usuario = context.getUsuarioAutenticado();
        idSucursal = new BigDecimal(usuario.getSucId());
        lstAcionGestion = new ArrayList<AcionGestion>();
        lstResultadoGestion = new ArrayList<ResultadoGestion>();

        lstSucursales = ifaceCatSucursales.getSucursales();

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
        modelo = ifaceCredito.getCreditosByEstatus(numFiltro, numDias, idSucursal,tipoVenta);
        ArrayList<SaldosDeudas> modelTemp = new ArrayList<SaldosDeudas>();
        ArrayList<SaldosDeudas> modelTempPrincipal = new ArrayList<SaldosDeudas>();

        BigDecimal zero = new BigDecimal(0);
        totalLiquidar = zero;
        int diasDiferencia = 0;
        Date fechaTemporal = null;

        for (SaldosDeudas saldos : modelo) {
            if (numFiltro == 0) {
                if (saldos.getPeriodosAtraso().equals(zero)) {
                    diasDiferencia = TiempoUtil.diferenciasDeFechas(fechaSystema, saldos.getFechaProximaAbonar());
                    saldos.setDiasAtraso(0);
                    saldos.setStatusFechaProxima(new BigDecimal(UNO));
                    totalLiquidar = totalLiquidar.add(saldos.getSaldoLiquidar());

                } else if (saldos.getPeriodosAtraso().compareTo(zero) == UNO && saldos.getPeriodosAtraso().compareTo(saldos.getNumeroPagos()) == -UNO) {
                    diasDiferencia = TiempoUtil.diferenciasDeFechas(fechaSystema, saldos.getFechaProximaAbonar());
                    diasDiferencia = (saldos.getPeriodosAtraso().intValue() - UNO) * (saldos.getPlazo().divide(saldos.getNumeroPagos())).intValue();

                    fechaTemporal = TiempoUtil.sumarRestarDias(saldos.getFechaVenta(), diasDiferencia);
                    diasDiferencia = TiempoUtil.diferenciasDeFechas(fechaTemporal, fechaSystema) + diasDiferencia;
                    diasDiferencia -= (saldos.getPlazo().divide(saldos.getNumeroPagos())).intValue();
                    saldos.setDiasAtraso(diasDiferencia);
                    saldos.setStatusFechaProxima(new BigDecimal(2));
                    totalLiquidar = totalLiquidar.add(saldos.getSaldoLiquidar());

                } else if (saldos.getPeriodosAtraso().equals(saldos.getNumeroPagos())) {
                    diasDiferencia = TiempoUtil.diferenciasDeFechas(saldos.getFechaPromesaFinPago(), fechaSystema);

                    saldos.setDiasAtraso(diasDiferencia);
                    if (diasDiferencia < 0) {
                        diasDiferencia = TiempoUtil.diferenciasDeFechas(TiempoUtil.sumarRestarDias(saldos.getFechaVenta(), saldos.getPlazo().intValue()), fechaSystema);
                        saldos.setStatusFechaProxima(new BigDecimal(1));
                        saldos.setDiasAtraso(diasDiferencia);
                    } else {
                        saldos.setStatusFechaProxima(new BigDecimal(4));
                    }

                    saldos.setFechaProximaAbonar(saldos.getFechaPromesaFinPago());
                    totalLiquidar = totalLiquidar.add(saldos.getSaldoLiquidar());
                }

                //Se recorre para sacar solo un registro para la tabla principal
                boolean exist = false;
                SaldosDeudas saldosDeudasTemp = new SaldosDeudas();
                for (SaldosDeudas dominio : modelTempPrincipal) {
                    if (dominio.getIdCliente().equals(saldos.getIdCliente())) {
                        exist = true;
                        dominio.getSaldoLiquidar().add(saldos.getSaldoLiquidar());
                        break;
                    } else {
                        exist = false;
                    }
                }
                if (!exist) {
                    saldosDeudasTemp.setCorreo(saldos.getCorreo());
                    saldosDeudasTemp.setSaldoLiquidar(saldos.getSaldoLiquidar());
                    saldosDeudasTemp.setNumeroTelefono(saldos.getNumeroTelefono());
                    saldosDeudasTemp.setNombreCompleto(saldos.getNombreCompleto());
                    saldosDeudasTemp.setIdCliente(saldos.getIdCliente());
                    modelTempPrincipal.add(saldosDeudasTemp);
                }

                modelTemp.add(saldos);

            } else {
                boolean exist = false;
                SaldosDeudas saldosDeudasTemp = new SaldosDeudas();
                boolean add = false;
                switch (numFiltro) {
                    case 1:
                        if (saldos.getPeriodosAtraso().equals(zero)) {
                            diasDiferencia = TiempoUtil.diferenciasDeFechas(fechaSystema, saldos.getFechaProximaAbonar());

                            if (diasDiferencia <= numDias) {
                                saldos.setDiasAtraso(0);
                                saldos.setStatusFechaProxima(new BigDecimal(UNO));
                                totalLiquidar = totalLiquidar.add(saldos.getSaldoLiquidar());
                                add = true;
                                modelTemp.add(saldos);
                            } else {
                                add = false;
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
                                saldos.setDiasAtraso(diasDiferencia);
                                saldos.setStatusFechaProxima(new BigDecimal(2));
                                totalLiquidar = totalLiquidar.add(saldos.getSaldoLiquidar());
                                modelTemp.add(saldos);
                                add = true;
                            } else {
                                add = false;
                            }

                        }
                        break;
                    case 3:
                        if (saldos.getPeriodosAtraso().equals(saldos.getNumeroPagos())) {
                            diasDiferencia = TiempoUtil.diferenciasDeFechas(saldos.getFechaPromesaFinPago(), fechaSystema);

                            if (diasDiferencia <= numDias) {
                                saldos.setDiasAtraso(diasDiferencia);
                                if (diasDiferencia < 0) {
                                    diasDiferencia = TiempoUtil.diferenciasDeFechas(TiempoUtil.sumarRestarDias(saldos.getFechaVenta(), saldos.getPlazo().intValue()), fechaSystema);
                                    saldos.setStatusFechaProxima(new BigDecimal(1));
                                    saldos.setDiasAtraso(diasDiferencia);
                                } else {
                                    saldos.setStatusFechaProxima(new BigDecimal(4));
                                }

                                saldos.setFechaProximaAbonar(saldos.getFechaPromesaFinPago());
                                totalLiquidar = totalLiquidar.add(saldos.getSaldoLiquidar());
                                modelTemp.add(saldos);
                                add = true;

                            } else {
                                add = false;
                            }

                        }
                        break;
                }
                //Se recorre para sacar solo un registro para la tabla principal
                for (SaldosDeudas dominio : modelTempPrincipal) {
                    if (dominio.getNombreCompleto().trim().equals(saldos.getNombreCompleto().trim()) && add) {
                        exist = true;
                        dominio.getSaldoLiquidar().add(saldos.getSaldoLiquidar());
                    } else {

                        exist = false;
                    }
                }

//                }
                if (!exist && add) {
                    saldosDeudasTemp.setCorreo(saldos.getCorreo());
                    saldosDeudasTemp.setSaldoLiquidar(saldos.getSaldoLiquidar());
                    saldosDeudasTemp.setNumeroTelefono(saldos.getNumeroTelefono());
                    saldosDeudasTemp.setNombreCompleto(saldos.getNombreCompleto());

                    modelTempPrincipal.add(saldosDeudasTemp);
                }

            }

        }

        if (modelTemp.isEmpty()) {
            JsfUtil.addWarnMessage("No se Encontraron Registros.");
            modelo.clear();
        } else if (modelTempPrincipal != null || !modelTempPrincipal.isEmpty()) {

            modelo = modelTempPrincipal;

            for (SaldosDeudas dominioPrincipal : modelo) {
                BigDecimal saldoUtilizado = new BigDecimal(0);
                ArrayList<SaldosDeudas> lstTemp = new ArrayList<SaldosDeudas>();
                for (SaldosDeudas dominio : modelTemp) {
                    if (dominioPrincipal.getNombreCompleto().equals(dominio.getNombreCompleto())) {
                        lstTemp.add(dominio);
                        saldoUtilizado = saldoUtilizado.add(dominio.getSaldoLiquidar());
                    }
                }
                dominioPrincipal.setLstSaldosDeudas(lstTemp);
                dominioPrincipal.setSaldoLiquidar(saldoUtilizado);
            }
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
                    System.out.println("data.getFolioCredito() " + data.getFolioCredito());
                    credito = ifaceCredito.getById(data.getFolioCredito());
                    System.out.println("credito :" + credito.toString());
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

    public BigDecimal getTotalLiquidar() {
        return totalLiquidar;
    }

    public void setTotalLiquidar(BigDecimal totalLiquidar) {
        this.totalLiquidar = totalLiquidar;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public ArrayList<Sucursal> getLstSucursales() {
        return lstSucursales;
    }

    public void setLstSucursales(ArrayList<Sucursal> lstSucursales) {
        this.lstSucursales = lstSucursales;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(BigDecimal tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    
    
}
