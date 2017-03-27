package com.web.chon.bean;

import com.web.chon.dominio.AnalisisMercado;
import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.Entidad;
import com.web.chon.dominio.Motivos;
import com.web.chon.dominio.Municipios;
import com.web.chon.dominio.ReporteClienteVentas;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.util.JsfUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author freddy
 */
@Component
@Scope("view")
public class BeanReporteClienteVenta implements BeanSimple {

    @Autowired
    private IfaceCatCliente ifaceCatCliente;

    private ArrayList<Cliente> lstCliente;
    private ArrayList<ReporteClienteVentas> model;

    private String title;
    private String viewEstate;
    private Cliente data;

    private BarChartModel chartBar;
    private LineChartModel chartLine;

    private boolean charLine;
    private BigDecimal maxChartValue;
    private BigDecimal diasRecuperacion;

    @PostConstruct
    public void init() {

        data = new Cliente();
        model = new ArrayList<ReporteClienteVentas>();
        
        diasRecuperacion = new BigDecimal(0);

        charLine = false;
        setTitle("Reporte de Ventas del Cliente.");
        setViewEstate("init");

    }

    public void generateChartLine() {
        
        diasRecuperacion = new BigDecimal(0);

        chartLine = initChartLine();
        chartLine.setSeriesColors("0404B4,088A08,81BEF7,D0F5A9");
        chartLine.setTitle(data.getNombreCompleto());
        chartLine.setLegendPosition("ne");
        chartLine.setZoom(true);
        chartLine.setAnimate(true);
        chartLine.setShowPointLabels(true);
        chartLine.setBreakOnNull(true);
        chartLine.setDatatipFormat("%2$d");
        chartLine.setLegendCols(6);
        chartLine.setStacked(true);
//        chartLine.getAxes().put(AxisType.X, new CategoryAxis("Fecha"));
        chartLine.getAxis(AxisType.X).setTickAngle(90);

        Axis yAxis = chartLine.getAxis(AxisType.Y);
//        yAxis.setLabel("Toneladas");
        yAxis.setMin(0);
        yAxis.setTickInterval("100000");
//        yAxis.setMax(maxChartValue);
    }

    private LineChartModel initChartLine() {

        LineChartModel lineChartModel = new LineChartModel();

        ChartSeries ventaTotal = new ChartSeries();
        ChartSeries totalContado = new ChartSeries();
        ChartSeries totalCredito = new ChartSeries();
        ChartSeries utilidad = new ChartSeries();

        ventaTotal.setLabel("Venta");
        totalContado.setLabel("Contado");
        totalCredito.setLabel("Crédito");
        utilidad.setLabel("Utilidad");

        ventaTotal.set("Venta", model.get(0).getTotalMayoreoContado().add(model.get(0).getTotalMayoreoCredito()).add(model.get(0).getTotalMenudeoContado().add(model.get(0).getTotalMenudeoCredito())));
        totalContado.set("Contado", model.get(0).getTotalMayoreoContado().add(model.get(0).getTotalMenudeoContado()));
        totalCredito.set("Crédito", model.get(0).getTotalMayoreoCredito().add(model.get(0).getTotalMenudeoCredito()));
        utilidad.set("Utilidad", model.get(0).getUtilidadMenudeo().add(model.get(0).getUtilidadMayoreoPacto()).add(model.get(0).getUtilidadMayoreoCosto().add(model.get(0).getUtilidadMayoreoComision())));

        lineChartModel.addSeries(ventaTotal);
        lineChartModel.addSeries(totalContado);
        lineChartModel.addSeries(totalCredito);
        lineChartModel.addSeries(utilidad);

        return lineChartModel;
    }

    public void generateChartBar() {

        chartBar = initChartBar();
        chartBar.setSeriesColors("0404B4,088A08,81BEF7,D0F5A9,FA3030");
        chartBar.setTitle(data.getNombreCompleto());
        chartBar.setLegendPosition("ne");
        chartBar.setZoom(true);
        chartBar.setAnimate(true);
//        chartBar.setDatatipFormat("%#.$d");
        chartBar.setShowPointLabels(true);
//        chartBar.setDatatipFormat("%2$d");
        chartBar.setLegendCols(6);
        chartBar.setStacked(false);
//        chartBar.getAxes().put(AxisType.X, new CategoryAxis("Fecha"));
        chartBar.getAxis(AxisType.X).setTickAngle(90);

        Axis yAxis = chartBar.getAxis(AxisType.Y);

//        yAxis.setLabel("Toneladas");
        yAxis.setMin(0);
        int interval = 50;
        int iMaxChartValue = 0;

        iMaxChartValue = maxChartValue.intValue();
        
        if(iMaxChartValue <= 1000000000 && iMaxChartValue >= 100000000){
            interval = 50000000;
        }else if(iMaxChartValue <= 100000000 && iMaxChartValue >= 10000000){
            interval = 5000000;
        }else if(iMaxChartValue <= 10000000 && iMaxChartValue >= 1000000){
            interval = 500000;
        }
        else if(iMaxChartValue <= 1000000 && iMaxChartValue >= 100000){
            interval = 50000;
        }
        else if(iMaxChartValue <= 100000 && iMaxChartValue >= 10000){
            interval = 5000;
        }
        else if(iMaxChartValue <= 10000 && iMaxChartValue >= 1000){
            interval = 500;
        }
        
        yAxis.setTickInterval(Integer.toString(interval));
        yAxis.setMax(maxChartValue.add(new BigDecimal(interval)));
    }

    private BarChartModel initChartBar() {

        BarChartModel barChartModel = new BarChartModel();

        maxChartValue = new BigDecimal(0);
        
        BigDecimal porcentaje = new BigDecimal(0);
        BigDecimal cien = new BigDecimal(100);
        BigDecimal bVenta = new BigDecimal(0);
        BigDecimal bContado = new BigDecimal(0);
        BigDecimal bCredito = new BigDecimal(0);
        BigDecimal bUtilidad = new BigDecimal(0);
        BigDecimal bRecuperacion = new BigDecimal(0);

        ChartSeries ventaTotal = new ChartSeries();
        ChartSeries totalContado = new ChartSeries();
        ChartSeries totalCredito = new ChartSeries();
        ChartSeries utilidad = new ChartSeries();
        ChartSeries recuperacion = new ChartSeries();

        bVenta = model.get(0).getTotalMayoreoContado().add(model.get(0).getTotalMayoreoCredito()).add(model.get(0).getTotalMenudeoContado().add(model.get(0).getTotalMenudeoCredito()));
        bContado = model.get(0).getTotalMayoreoContado().add(model.get(0).getTotalMenudeoContado());
        bCredito = model.get(0).getTotalMayoreoCredito().add(model.get(0).getTotalMenudeoCredito());
        bUtilidad = model.get(0).getUtilidadMenudeo().add(model.get(0).getUtilidadMayoreoPacto()).add(model.get(0).getUtilidadMayoreoCosto().add(model.get(0).getUtilidadMayoreoComision()));
        bRecuperacion = model.get(0).getRecuperacion();
        
        ventaTotal.setLabel("Venta");
        totalContado.setLabel("Contado");
        totalCredito.setLabel("Crédito");
        utilidad.setLabel("Utilidad");
        recuperacion.setLabel("Recuperación");
        
        
        
        
        
        diasRecuperacion =model.get(0).getDiasRecuperacion().setScale(2,RoundingMode.DOWN);
        
        porcentaje = bVenta.multiply(cien).divide(bVenta,2,RoundingMode.CEILING);
        ventaTotal.set("Venta", bVenta);
        porcentaje = bVenta.multiply(cien).divide(bVenta,2,RoundingMode.CEILING);
        totalContado.set("Contado", bContado);
        porcentaje = bVenta.multiply(cien).divide(bVenta,2,RoundingMode.CEILING);
        totalCredito.set("Crédito", bCredito);
        porcentaje = bVenta.multiply(cien).divide(bVenta,2,RoundingMode.CEILING);
        utilidad.set("Utilidad", bUtilidad);
        porcentaje = bVenta.multiply(cien).divide(bVenta,2,RoundingMode.CEILING);
        recuperacion.set("Recuperacón",bRecuperacion);

        maxChartValue = bVenta;
        barChartModel.addSeries(ventaTotal);
        barChartModel.addSeries(totalContado);
        barChartModel.addSeries(totalCredito);
        barChartModel.addSeries(utilidad);
        barChartModel.addSeries(recuperacion);

        return barChartModel;
    }

    @Override
    public void searchById() {
        model = ifaceCatCliente.getReporteClienteVentasUtilidad(data.getIdClientePk(), null, null);
        if (model != null && !model.isEmpty()) {
            generateChartLine();
            generateChartBar();
        } else {
            JsfUtil.addWarnMessage("No se Encontraron Registros.");
        }

    }

    public ArrayList<Cliente> autoCompleteCliente(String nombreCliente) {
        lstCliente = ifaceCatCliente.getClienteByNombreCompleto(nombreCliente.toUpperCase());
        return lstCliente;

    }

    @Override
    public String delete() {
        return "";
    }

    @Override
    public String insert() {
        return "";
    }

    public void backView() {
        setViewEstate("init");
    }

    @Override
    public String update() {
        return "";
    }

    public Cliente getCliente() {
        return data;
    }

    public void setCliente(Cliente cliente) {
        this.data = cliente;
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

    public ArrayList<ReporteClienteVentas> getModel() {
        return model;
    }

    public void setModel(ArrayList<ReporteClienteVentas> model) {
        this.model = model;
    }

    public Cliente getData() {
        return data;
    }

    public void setData(Cliente data) {
        this.data = data;
    }

    public BarChartModel getChartBar() {
        return chartBar;
    }

    public void setChartBar(BarChartModel chartBar) {
        this.chartBar = chartBar;
    }

    public LineChartModel getChartLine() {
        return chartLine;
    }

    public void setChartLine(LineChartModel chartLine) {
        this.chartLine = chartLine;
    }

    public ArrayList<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(ArrayList<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

    public boolean isCharLine() {
        return charLine;
    }

    public void setCharLine(boolean charLine) {
        this.charLine = charLine;
    }

    public BigDecimal getDiasRecuperacion() {
        return diasRecuperacion;
    }

    public void setDiasRecuperacion(BigDecimal diasRecuperacion) {
        this.diasRecuperacion = diasRecuperacion;
    }
    
    

}
