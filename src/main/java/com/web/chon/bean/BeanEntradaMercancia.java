package com.web.chon.bean;

import com.web.chon.bean.mvc.SimpleViewBean;
import com.web.chon.dominio.BuscaVenta;
import com.web.chon.dominio.EntradaMercancia;

import com.web.chon.model.PaginationLazyDataModel;
import com.web.chon.service.IfaceBuscaVenta;
import com.web.chon.service.IfaceEntradaProductoCentral;
import com.web.chon.util.JasperReportUtil;
import com.web.chon.util.JsfUtil;
import com.web.chon.util.UtilUpload;
import com.web.chon.util.Utilerias;
import com.web.chon.util.ViewState;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.JstlUtils;

@Component
@Scope("view")
public class BeanEntradaMercancia extends SimpleViewBean<EntradaMercancia> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final static Logger logger = LoggerFactory.getLogger(EntradaMercancia.class);
    private EntradaMercancia dataModel;
    public ArrayList<EntradaMercancia> lstEntradaMercancia;
    private int filtro;
    private int chartMaxValue = 10;
    private Date filtroFechaInicio;
    private Date filtroFechaFin;
    private LineChartModel lineModel;

    private ByteArrayOutputStream outputStream;

    private String pathFileJasper = "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/src/main/webapp/resources/report/entradaMercancia/entradaSucursal.jasper";
    @Autowired
    private IfaceEntradaProductoCentral ifaceEntradaProductoCentral;

    private String title;

    @Override
    protected Logger getLogger() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initModel() {

        data = new EntradaMercancia();
        model = new PaginationLazyDataModel<EntradaMercancia, BigDecimal>(ifaceEntradaProductoCentral,new EntradaMercancia());

        lstEntradaMercancia = ifaceEntradaProductoCentral.getEntradaMercanciaByFiltro(1, new Date(), new Date());
        if (!lstEntradaMercancia.isEmpty()) {
            data = lstEntradaMercancia.get(0);
        }
        lstEntradaMercancia = new ArrayList<EntradaMercancia>();
        setTitle("Registro Entradas de Mercancias");

    }

    public void setFechaInicioFin() {
        try {
            if (filtroFechaInicio != null && filtroFechaFin != null) {
                lstEntradaMercancia = ifaceEntradaProductoCentral.getEntradaMercanciaByFiltro(filtro, filtroFechaInicio, filtroFechaFin);
            }

        } catch (Exception e) {
            logger.error("Error > " + e.getMessage());
        }
    }

    public void generateChart() {

        lineModel = initChart();

        lineModel.setTitle("Entrada de Mercancia a Central");
        lineModel.setLegendPosition("e");
        lineModel.setZoom(true);
        lineModel.setAnimate(true);
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis("Filtro"));
        lineModel.getAxis(AxisType.X).setTickAngle(90);

        Axis yAxis = lineModel.getAxis(AxisType.Y);

        yAxis.setLabel("Valores");
        yAxis.setMin(0);
        yAxis.setMax(chartMaxValue);
    }

    private LineChartModel initChart() {

        LineChartModel model = new LineChartModel();
        ChartSeries toneladas = new ChartSeries();
        ChartSeries precio = new ChartSeries();

        toneladas.setLabel("Toneladas");
        precio.setLabel("Precio promedio");

        for (EntradaMercancia dominio : lstEntradaMercancia) {

            if (dominio.getCantidadToneladas().intValue() > chartMaxValue) {
                chartMaxValue = dominio.getCantidadToneladas().intValue() + 1000;
            }
            if (dominio.getPrecio().intValue() > chartMaxValue) {
                chartMaxValue = dominio.getPrecio().intValue() + 1000;
            }
            toneladas.set(dominio.getDescripcionFiltro(), dominio.getCantidadToneladas());
            precio.set(dominio.getDescripcionFiltro(), dominio.getPrecio());
        }

        model.addSeries(toneladas);
        model.addSeries(precio);

        return model;
    }

    @Override
    public String search() {
        setTitle("Graficas.");
        actionSearching();

        return "entradaMercancia";
    }

    public void cancel() {

        actionBack();
        initModel();
        lineModel = null;
    }

    @Override
    public String searchDatabyId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String save() {
        try {
            if (data.getIdEntrada() != null) {
                ifaceEntradaProductoCentral.update(data);
                 JsfUtil.addSuccessMessage("Registro actualizado Correctamente.");

            } else {

                ifaceEntradaProductoCentral.saveEntradaProductoCentral(data);
                JsfUtil.addSuccessMessage("Registro insertado Correctamente.");
            }
        } catch (Exception e) {
            logger.error("Erros al insertar", "Error", e.getMessage());
        }

        return "entradaMercancia";
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public Date getFiltroFechaInicio() {
        return filtroFechaInicio;
    }

    public void setFiltroFechaInicio(Date filtroFechaInicio) {
        this.filtroFechaInicio = filtroFechaInicio;
    }

    public Date getFiltroFechaFin() {
        return filtroFechaFin;
    }

    public void setFiltroFechaFin(Date filtroFechaFin) {
        this.filtroFechaFin = filtroFechaFin;
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String getPathFileJasper() {
        return pathFileJasper;
    }

    public void setPathFileJasper(String pathFileJasper) {
        this.pathFileJasper = pathFileJasper;
    }

    public ArrayList<EntradaMercancia> getLstEntradaMercancia() {
        return lstEntradaMercancia;
    }

    public void setLstEntradaMercancia(ArrayList<EntradaMercancia> lstEntradaMercancia) {
        this.lstEntradaMercancia = lstEntradaMercancia;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

}
