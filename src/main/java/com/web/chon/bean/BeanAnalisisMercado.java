package com.web.chon.bean;

import com.web.chon.bean.mvc.SimpleViewBean;
import com.web.chon.dominio.AnalisisMercado;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.UsuarioDominio;

import com.web.chon.model.PaginationLazyDataModel;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.util.JsfUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.web.chon.service.IfaceAnalisisMercado;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.util.TiempoUtil;
import java.math.RoundingMode;

@Component
@Scope("view")
public class BeanAnalisisMercado extends SimpleViewBean<AnalisisMercado> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final static Logger logger = LoggerFactory.getLogger(AnalisisMercado.class);

    @Autowired
    private IfaceSubProducto ifaceProducto;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceAnalisisMercado ifaceEntradaProductoCentral;

    private ArrayList<AnalisisMercado> lstEntradaMercancia;
    private ArrayList<AnalisisMercado> lstEntradaMercanciaSemana;
    private ArrayList<AnalisisMercado> lstEntradaMercanciaMes;
    private ArrayList<Subproducto> lstProducto;

    private UsuarioDominio usuario;

    private LineChartModel chartLineByDias;
    private BarChartModel chartBarByDias;
    private LineChartModel chartLineBySemana;
    private BarChartModel chartBarBySemana;
    private LineChartModel chartLineBMes;
    private BarChartModel chartBarByMes;

    private Date filtroFechaInicio;
    private Date filtroFechaFin;
    private Date fechaRemanente;

    private String title;
    private String insertar;

    int maxChartValue;

    private boolean charLine = true;
    private boolean charExpander = false;

    private BigDecimal zero = new BigDecimal(0);

    @Override
    public void initModel() {
        usuario = context.getUsuarioAutenticado();
        data = new AnalisisMercado();
        model = new PaginationLazyDataModel<AnalisisMercado, BigDecimal>(ifaceEntradaProductoCentral, new AnalisisMercado());

        lstProducto = new ArrayList<Subproducto>();
        lstProducto = ifaceProducto.getSubProductos();

        filtroFechaInicio = new Date();

        lstEntradaMercancia = new ArrayList<AnalisisMercado>();
        setTitle("Análisis de Mercado");
        setInsertar("precioPromedio");


        /*Pantalla principal welcome.xhtml*/
        lstEntradaMercancia = ifaceEntradaProductoCentral.getEntradaMercanciaByFiltro(28, 1, TiempoUtil.sumarRestarDias(context.getFechaSistema(), -14), "00000001");
        generateChartLine();
        generateChartBar();
        /*Pantalla principal welcome.xhtml*/

    }

    @Override
    public String search() {
        setTitle("Análisis de Mercado");
        actionSearching();

        chartBarByDias = null;
        chartLineBMes = null;
        chartBarByMes = null;
        chartLineByDias = null;
        chartLineBySemana = null;
        chartBarBySemana = null;

        data.reset();

        return "analisisMercado";
    }

    @Override
    public String save() {
        try {
            if (insertar.equals("precioPromedio")) {
                if (data.getIdEntrada() != null) {
                    ifaceEntradaProductoCentral.update(data);
                    JsfUtil.addSuccessMessage("Registro actualizado Correctamente.");

                } else {

                    if(data.getFecha() == null){
                        data.setFecha(context.getFechaSistema());
                    }
                    ifaceEntradaProductoCentral.saveEntradaProductoCentral(data);
                    JsfUtil.addSuccessMessage("Registro insertado Correctamente.");
                }
            } else {
                data.setFecha(fechaRemanente);
                ifaceEntradaProductoCentral.updateByIdProductoAndFecha(data);
                JsfUtil.addSuccessMessage("Registro actualizado Correctamente.");
            }
        } catch (Exception e) {
            logger.error("Erros al insertar", "Error", e.getMessage());
        }

        return "analisisMercado";
    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void generateChartLine() {

        chartLineByDias = initChartLine();
        chartLineByDias.setSeriesColors("0404B4,088A08,81BEF7,D0F5A9");
        chartLineByDias.setTitle("Análisis de Mercado");
        chartLineByDias.setLegendPosition("ne");
        chartLineByDias.setZoom(true);
        chartLineByDias.setAnimate(true);
        chartLineByDias.setShowPointLabels(true);
        chartLineByDias.setBreakOnNull(true);
        chartLineByDias.setDatatipFormat("%2$d");
        chartLineByDias.setLegendCols(6);
        chartLineByDias.setStacked(true);
        chartLineByDias.getAxes().put(AxisType.X, new CategoryAxis("Fecha"));
        chartLineByDias.getAxis(AxisType.X).setTickAngle(90);

        Axis yAxis = chartLineByDias.getAxis(AxisType.Y);
        yAxis.setLabel("Toneladas");
        yAxis.setMin(0);
        yAxis.setTickInterval("10");
        yAxis.setMax(maxChartValue);
    }

    private LineChartModel initChartLine() {

        LineChartModel model = new LineChartModel();
        ChartSeries toneladas = new ChartSeries();
        ChartSeries precio = new ChartSeries();

        ChartSeries toneladasAnt = new ChartSeries();
        ChartSeries precioAnt = new ChartSeries();

        toneladas.setLabel("Toneladas");

        precio.setLabel("Precio");

        toneladasAnt.setLabel("Toneladas Año Anterior");
        precioAnt.setLabel("Precio Año Anterior");
        maxChartValue = 100;
        for (AnalisisMercado dominio : lstEntradaMercancia) {

            if (maxChartValue < dominio.getCantidadToneladasAnterior().intValue()) {
                maxChartValue = dominio.getCantidadToneladasAnterior().intValue();
            }
            if (maxChartValue < dominio.getCantidadToneladas().intValue()) {
                maxChartValue = dominio.getCantidadToneladas().intValue();
            }

            toneladas.set(dominio.getDescripcionFiltro(), dominio.getCantidadToneladas());
            precio.set(dominio.getDescripcionFiltro(), dominio.getPrecio());
            toneladasAnt.set(dominio.getDescripcionFiltro(), dominio.getCantidadToneladasAnterior());
            precioAnt.set(dominio.getDescripcionFiltro(), dominio.getPrecioAnterior());
        }
        maxChartValue += 10;
        model.addSeries(toneladas);
        model.addSeries(precio);
        model.addSeries(toneladasAnt);
        model.addSeries(precioAnt);

        return model;
    }

    public void generateChartBar() {

        chartBarByDias = initChartBar();
        chartBarByDias.setSeriesColors("0404B4,088A08,81BEF7,D0F5A9,");
        chartBarByDias.setTitle("Análisis de Mercado por Semana");
        chartBarByDias.setLegendPosition("ne");
        chartBarByDias.setZoom(true);
        chartBarByDias.setAnimate(true);
        chartBarByDias.setShowPointLabels(true);
        chartBarByDias.setDatatipFormat("%2$d");
        chartBarByDias.setLegendCols(6);
        chartLineByDias.setStacked(false);
        chartBarByDias.getAxes().put(AxisType.X, new CategoryAxis("Fecha"));
        chartBarByDias.getAxis(AxisType.X).setTickAngle(90);

        Axis yAxis = chartBarByDias.getAxis(AxisType.Y);

        yAxis.setLabel("Toneladas");
        yAxis.setMin(0);
        yAxis.setTickInterval("10");
        yAxis.setMax(maxChartValue);
    }

    private BarChartModel initChartBar() {

        BarChartModel model = new BarChartModel();
        ChartSeries toneladas = new ChartSeries();
        ChartSeries precio = new ChartSeries();

        ChartSeries toneladasAnt = new ChartSeries();
        ChartSeries precioAnt = new ChartSeries();

        toneladas.setLabel("Toneladas");

        precio.setLabel("Precio");

        toneladasAnt.setLabel("Toneladas Año Anterior");
        precioAnt.setLabel("Precio Año Anterior");

        for (AnalisisMercado dominio : lstEntradaMercancia) {
            toneladas.set(dominio.getDescripcionFiltro(), dominio.getCantidadToneladas());
            precio.set(dominio.getDescripcionFiltro(), dominio.getPrecio());
            toneladasAnt.set(dominio.getDescripcionFiltro(), dominio.getCantidadToneladasAnterior());
            precioAnt.set(dominio.getDescripcionFiltro(), dominio.getPrecioAnterior());
        }

        model.addSeries(toneladas);
        model.addSeries(precio);
        model.addSeries(toneladasAnt);
        model.addSeries(precioAnt);

        return model;
    }

    public void generateChartLineSemana() {

        chartLineBySemana = initChartLineSemana();
        chartLineBySemana.setSeriesColors("0404B4,088A08,81BEF7,D0F5A9,FF0040,FFFFFF");
        chartLineBySemana.setTitle("Análisis de Mercado por Semana");
        chartLineBySemana.setLegendPosition("nw");
        chartLineBySemana.setZoom(true);
        chartLineBySemana.setAnimate(true);
        chartLineBySemana.setShowPointLabels(true);
        chartLineBySemana.setBreakOnNull(true);
        chartLineBySemana.setDatatipFormat("%2$d");
        chartLineBySemana.setLegendCols(6);
        chartLineBySemana.getAxes().put(AxisType.X, new CategoryAxis("Semana"));
        chartLineBySemana.getAxis(AxisType.X).setTickAngle(90);

        Axis yAxis = chartLineBySemana.getAxis(AxisType.Y);

        yAxis.setLabel("Toneladas");
        yAxis.setMin(0);
        yAxis.setTickInterval("50");
        yAxis.setMax(maxChartValue);
    }

    private LineChartModel initChartLineSemana() {

        int index = 0;
        LineChartModel model = new LineChartModel();
        ChartSeries toneladas = new ChartSeries();
        ChartSeries precio = new ChartSeries();

        ChartSeries toneladasAnt = new ChartSeries();
        ChartSeries precioAnt = new ChartSeries();
        ChartSeries remanente = new ChartSeries();
        ChartSeries salidaMercancia = new ChartSeries();

        toneladas.setLabel("Toneladas");

        precio.setLabel("Precio");

        toneladasAnt.setLabel("Toneladas Año Anterior");
        precioAnt.setLabel("Precio Año Anterior");
        remanente.setLabel("Remanente");
        salidaMercancia.setLabel("Salida Mercancia");
        AnalisisMercado analisisMercadoTemp = null;

        for (AnalisisMercado dominio : lstEntradaMercanciaSemana) {

            if (index != 0) {
                toneladas.set(dominio.getDescripcionFiltro(), dominio.getCantidadToneladas());
                precio.set(dominio.getDescripcionFiltro(), dominio.getPrecio());
                toneladasAnt.set(dominio.getDescripcionFiltro(), dominio.getCantidadToneladasAnterior());
                precioAnt.set(dominio.getDescripcionFiltro(), dominio.getPrecioAnterior());
                remanente.set(dominio.getDescripcionFiltro(), dominio.getRemantePorSemana());

                BigDecimal ventaDia = null;
                if (analisisMercadoTemp != null) {
                    ventaDia = dominio.getCantidadToneladas().subtract(dominio.getRemantePorSemana()).add(analisisMercadoTemp.getRemantePorSemana());
                } else {
                    ventaDia = dominio.getCantidadToneladas().subtract(dominio.getRemantePorSemana());

                }

                ventaDia = ventaDia.equals(zero) ? ventaDia : ventaDia.divide(new BigDecimal(7), 1, RoundingMode.UP);
                salidaMercancia.set(dominio.getDescripcionFiltro(), ventaDia);

            }
            analisisMercadoTemp = dominio;
            index++;

        }

        model.addSeries(toneladas);
        model.addSeries(precio);
        model.addSeries(toneladasAnt);
        model.addSeries(precioAnt);
        model.addSeries(remanente);
        model.addSeries(salidaMercancia);

        return model;
    }

    public void generateChartBarSemana() {

        chartBarBySemana = initChartBarSemana();
        chartBarBySemana.setSeriesColors("0404B4,088A08,81BEF7,D0F5A9,FF0040,FFFFFF");
        chartBarBySemana.setTitle("Análisis de Mercado");
        chartBarBySemana.setLegendPosition("nw");
        chartBarBySemana.setZoom(true);
        chartBarBySemana.setAnimate(true);
        chartBarBySemana.setShowPointLabels(true);

        chartBarBySemana.setDatatipFormat("%2$d");
        chartBarBySemana.setLegendCols(6);
        chartBarBySemana.getAxes().put(AxisType.X, new CategoryAxis("Semana"));
        chartBarBySemana.getAxis(AxisType.X).setTickAngle(90);

        Axis yAxis = chartBarBySemana.getAxis(AxisType.Y);

        yAxis.setLabel("Toneladas");
        yAxis.setMin(0);
        yAxis.setTickInterval("50");
        yAxis.setMax(maxChartValue);
    }

    private BarChartModel initChartBarSemana() {

        int index = 0;

        BarChartModel model = new BarChartModel();
        ChartSeries toneladas = new ChartSeries();
        ChartSeries precio = new ChartSeries();

        ChartSeries toneladasAnt = new ChartSeries();
        ChartSeries precioAnt = new ChartSeries();
        ChartSeries remanente = new ChartSeries();
        ChartSeries salidaMercancia = new ChartSeries();

        toneladas.setLabel("Toneladas");

        precio.setLabel("Precio");

        toneladasAnt.setLabel("Toneladas Año Anterior");
        precioAnt.setLabel("Precio Año Anterior");
        remanente.setLabel("Remanente");
        salidaMercancia.setLabel("Salida Mercancia");
        AnalisisMercado analisisMercadoTemp = null;
        maxChartValue = 100;
        for (AnalisisMercado dominio : lstEntradaMercanciaSemana) {
            if (index != 0) {

                if (maxChartValue < dominio.getCantidadToneladasAnterior().intValue()) {
                    maxChartValue = dominio.getCantidadToneladasAnterior().intValue();
                }
                if (maxChartValue < dominio.getCantidadToneladas().intValue()) {
                    maxChartValue = dominio.getCantidadToneladas().intValue();
                }

                toneladas.set(dominio.getDescripcionFiltro(), dominio.getCantidadToneladas());
                precio.set(dominio.getDescripcionFiltro(), dominio.getPrecio());
                toneladasAnt.set(dominio.getDescripcionFiltro(), dominio.getCantidadToneladasAnterior());
                precioAnt.set(dominio.getDescripcionFiltro(), dominio.getPrecioAnterior());
                remanente.set(dominio.getDescripcionFiltro(), dominio.getRemantePorSemana());

                BigDecimal ventaDia = null;
                if (analisisMercadoTemp != null) {
                    ventaDia = dominio.getCantidadToneladas().subtract(dominio.getRemantePorSemana()).add(analisisMercadoTemp.getRemantePorSemana());
                } else {
                    ventaDia = dominio.getCantidadToneladas().subtract(dominio.getRemantePorSemana());

                }

                ventaDia = ventaDia.equals(zero) ? ventaDia : ventaDia.divide(new BigDecimal(7), 1, RoundingMode.UP);

                salidaMercancia.set(dominio.getDescripcionFiltro(), ventaDia);
            }
            index++;
            analisisMercadoTemp = dominio;
        }

        maxChartValue += 100;

        model.addSeries(toneladas);
        model.addSeries(precio);
        model.addSeries(toneladasAnt);
        model.addSeries(precioAnt);
        model.addSeries(remanente);
        model.addSeries(salidaMercancia);

        return model;
    }
    ///Mes

    public void generateChartLineMes() {

        chartLineBMes = initChartLineMes();
        chartLineBMes.setSeriesColors("0404B4,088A08,81BEF7,D0F5A9,FF0040,FFFFFF");
        chartLineBMes.setTitle("Análisis de Mercado por Mes");
        chartLineBMes.setLegendPosition("nw");
        chartLineBMes.setZoom(true);
        chartLineBMes.setAnimate(true);
        chartLineBMes.setShowPointLabels(true);
        chartLineBMes.setBreakOnNull(true);
        chartLineBMes.setDatatipFormat("%2$d");
        chartLineBMes.setLegendCols(6);
        chartLineBMes.getAxes().put(AxisType.X, new CategoryAxis("Mes"));
        chartLineBMes.getAxis(AxisType.X).setTickAngle(90);

        Axis yAxis = chartLineBMes.getAxis(AxisType.Y);

        yAxis.setLabel("Toneladas");
        yAxis.setMin(0);
        yAxis.setTickInterval("150");
        yAxis.setMax(maxChartValue);
    }

    private LineChartModel initChartLineMes() {

        LineChartModel model = new LineChartModel();
        ChartSeries toneladas = new ChartSeries();
        ChartSeries precio = new ChartSeries();

        ChartSeries toneladasAnt = new ChartSeries();
        ChartSeries precioAnt = new ChartSeries();
        ChartSeries remanente = new ChartSeries();
        ChartSeries salidaMercancia = new ChartSeries();

        toneladas.setLabel("Toneladas");
        precio.setLabel("Precio");

        toneladasAnt.setLabel("Toneladas Año Anterior");
        precioAnt.setLabel("Precio Año Anterior");
        remanente.setLabel("Remanente");
        salidaMercancia.setLabel("Salida Mercancia");
        AnalisisMercado analisisMercadoTemp = null;
        for (AnalisisMercado dominio : lstEntradaMercanciaMes) {

            if (maxChartValue < dominio.getCantidadToneladasAnterior().intValue()) {
                maxChartValue = dominio.getCantidadToneladasAnterior().intValue();
            }
            if (maxChartValue < dominio.getCantidadToneladas().intValue()) {
                maxChartValue = dominio.getCantidadToneladas().intValue();
            }

            toneladas.set(dominio.getDescripcionFiltro(), dominio.getCantidadToneladas());
            precio.set(dominio.getDescripcionFiltro(), dominio.getPrecio());
            toneladasAnt.set(dominio.getDescripcionFiltro(), dominio.getCantidadToneladasAnterior());
            precioAnt.set(dominio.getDescripcionFiltro(), dominio.getPrecioAnterior());
            remanente.set(dominio.getDescripcionFiltro(), dominio.getRemantePorSemana());
            BigDecimal ventaDia = null;
            if (analisisMercadoTemp != null) {
                ventaDia = dominio.getCantidadToneladas().subtract(dominio.getRemantePorSemana()).add(analisisMercadoTemp.getRemantePorSemana());
            } else {
                ventaDia = dominio.getCantidadToneladas().subtract(dominio.getRemantePorSemana());

            }

            ventaDia = ventaDia.equals(zero) ? ventaDia : ventaDia.divide(dominio.getDiasMes(), 1, RoundingMode.UP);

            salidaMercancia.set(dominio.getDescripcionFiltro(), ventaDia);

            analisisMercadoTemp = dominio;

        }
        maxChartValue += 300;

        model.addSeries(toneladas);
        model.addSeries(precio);
        model.addSeries(toneladasAnt);
        model.addSeries(precioAnt);
        model.addSeries(remanente);
        model.addSeries(salidaMercancia);

        return model;
    }

    public void generateChartBarMes() {

        chartBarByMes = initChartBarMes();
        chartBarByMes.setSeriesColors("0404B4,088A08,81BEF7,D0F5A9,FF0040,FFFFFF");
        chartBarByMes.setTitle("Análisis de Mercado por Mes");
        chartBarByMes.setLegendPosition("nw");
        chartBarByMes.setZoom(true);
        chartBarByMes.setAnimate(true);
        chartBarByMes.setShowPointLabels(true);

        chartBarByMes.setDatatipFormat("%2$d");
        chartBarByMes.setLegendCols(6);
        chartBarByMes.getAxes().put(AxisType.X, new CategoryAxis("Mes"));
        chartBarByMes.getAxis(AxisType.X).setTickAngle(90);

        Axis yAxis = chartBarByMes.getAxis(AxisType.Y);

        yAxis.setLabel("Toneladas");
        yAxis.setMin(0);
        yAxis.setTickInterval("150");
        yAxis.setMax(maxChartValue);
    }

    private BarChartModel initChartBarMes() {

        BarChartModel model = new BarChartModel();
        ChartSeries toneladas = new ChartSeries();
        ChartSeries precio = new ChartSeries();

        BigDecimal zero = new BigDecimal(0);

        ChartSeries toneladasAnt = new ChartSeries();
        ChartSeries precioAnt = new ChartSeries();
        ChartSeries remanente = new ChartSeries();
        ChartSeries salidaMercancia = new ChartSeries();

        toneladas.setLabel("Toneladas");

        precio.setLabel("Precio");

        toneladasAnt.setLabel("Toneladas Año Anterior");
        precioAnt.setLabel("Precio Año Anterior");
        remanente.setLabel("Remanente");
        salidaMercancia.setLabel("Salida Mercancia");

        maxChartValue = 300;
        AnalisisMercado analisisMercadoTemp = null;
        for (AnalisisMercado dominio : lstEntradaMercanciaMes) {

            if (maxChartValue < dominio.getCantidadToneladasAnterior().intValue()) {
                maxChartValue = dominio.getCantidadToneladasAnterior().intValue();
            }
            if (maxChartValue < dominio.getCantidadToneladas().intValue()) {
                maxChartValue = dominio.getCantidadToneladas().intValue();
            }

            toneladas.set(dominio.getDescripcionFiltro(), dominio.getCantidadToneladas());
            precio.set(dominio.getDescripcionFiltro(), dominio.getPrecio());
            toneladasAnt.set(dominio.getDescripcionFiltro(), dominio.getCantidadToneladasAnterior());
            precioAnt.set(dominio.getDescripcionFiltro(), dominio.getPrecioAnterior());
            remanente.set(dominio.getDescripcionFiltro(), dominio.getRemantePorSemana());
            BigDecimal ventaDia = null;
            if (analisisMercadoTemp != null) {
                ventaDia = dominio.getCantidadToneladas().subtract(dominio.getRemantePorSemana()).add(analisisMercadoTemp.getRemantePorSemana());
            } else {
                ventaDia = dominio.getCantidadToneladas().subtract(dominio.getRemantePorSemana());

            }

            ventaDia = ventaDia.equals(zero) ? ventaDia : ventaDia.divide(dominio.getDiasMes(), 1, RoundingMode.UP);

            salidaMercancia.set(dominio.getDescripcionFiltro(), ventaDia);

            analisisMercadoTemp = dominio;

        }
        maxChartValue += 300;

        model.addSeries(toneladas);
        model.addSeries(precio);
        model.addSeries(toneladasAnt);
        model.addSeries(precioAnt);
        model.addSeries(remanente);
        model.addSeries(salidaMercancia);

        return model;
    }

    public void filtroPorProducto() {
        try {
            int registrosMostrarDia = 8;
            int registrosMostrarSemana = 42;
            int registrosMostrarMes = 12;

            if (charExpander) {
                registrosMostrarDia = 40;
                registrosMostrarSemana = 320;
            }

            if (data.getIdProductoFk() != null) {
                lstEntradaMercancia = ifaceEntradaProductoCentral.getEntradaMercanciaByFiltro(registrosMostrarDia, 1, filtroFechaInicio, data.getIdProductoFk());
                lstEntradaMercanciaSemana = ifaceEntradaProductoCentral.getEntradaMercanciaByFiltro(registrosMostrarSemana, 2, filtroFechaInicio, data.getIdProductoFk());
                lstEntradaMercanciaMes = ifaceEntradaProductoCentral.getEntradaMercanciaByFiltro(registrosMostrarMes, 3, filtroFechaInicio, data.getIdProductoFk());
                System.out.println("3 bean");

                generateChartLine();
                generateChartBar();
                generateChartBarSemana();
                generateChartLineSemana();
                generateChartLineMes();
                generateChartBarMes();
            } else {
                lstEntradaMercancia.clear();
                lstEntradaMercanciaSemana.clear();
                lstEntradaMercanciaMes.clear();

                chartLineByDias = null;
                chartBarByDias = null;
                chartLineBySemana = null;
                chartBarBySemana = null;
                chartLineBMes = null;
                chartBarByMes = null;

            }

        } catch (Exception e) {
            logger.error("Error > " + e.getMessage());
        }
    }

    public void cancel() {

        actionBack();
        initModel();
        chartLineBySemana = null;
        chartLineBySemana = null;
        chartLineBySemana = null;
        chartBarBySemana = null;
    }

    public String searchDatabyIdProducto() {
        String idProducto = data.getIdProductoFk();
        data = ifaceEntradaProductoCentral.getEntradaProductoByIdProducto(data.getIdProductoFk(), TiempoUtil.getFechaDDMMYYYY(fechaRemanente));
        data.setIdProductoFk(idProducto);
        filtroPorProducto();

        return "entradaMercancia";
    }

    public void searchRemanente() {
        if (!insertar.equals("precioPromedio")) {
            data.setRemantePorSemana(ifaceEntradaProductoCentral.getRemanente(fechaRemanente, data.getIdProductoFk()));
        }else{
            searchDatabyIdProducto();
        }
    }

    @Override
    protected Logger getLogger() {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public ArrayList<AnalisisMercado> getLstEntradaMercancia() {
        return lstEntradaMercancia;
    }

    public void setLstEntradaMercancia(ArrayList<AnalisisMercado> lstEntradaMercancia) {
        this.lstEntradaMercancia = lstEntradaMercancia;
    }

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    @Override
    public String searchDatabyId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public LineChartModel getChartLineByDias() {
        return chartLineByDias;
    }

    public void setChartLineByDias(LineChartModel chartLineByDias) {
        this.chartLineByDias = chartLineByDias;
    }

    public BarChartModel getChartBarByDias() {
        return chartBarByDias;
    }

    public void setChartBarByDias(BarChartModel chartBarByDias) {
        this.chartBarByDias = chartBarByDias;
    }

    public LineChartModel getChartLineBySemana() {
        return chartLineBySemana;
    }

    public void setChartLineBySemana(LineChartModel chartLineBySemana) {
        this.chartLineBySemana = chartLineBySemana;
    }

    public BarChartModel getChartBarBySemana() {
        return chartBarBySemana;
    }

    public void setChartBarBySemana(BarChartModel chartBarBySemana) {
        this.chartBarBySemana = chartBarBySemana;
    }

    public boolean isCharLine() {
        return charLine;
    }

    public void setCharLine(boolean charLine) {
        this.charLine = charLine;
    }

    public boolean isCharExpander() {
        return charExpander;
    }

    public void setCharExpander(boolean charExpander) {
        this.charExpander = charExpander;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public Date getFechaRemanente() {
        return fechaRemanente;
    }

    public void setFechaRemanente(Date fechaRemanente) {
        this.fechaRemanente = fechaRemanente;
    }

    public String getInsertar() {
        return insertar;
    }

    public void setInsertar(String insertar) {
        this.insertar = insertar;
    }

    public LineChartModel getChartLineBMes() {
        return chartLineBMes;
    }

    public void setChartLineBMes(LineChartModel chartLineBMes) {
        this.chartLineBMes = chartLineBMes;
    }

    public BarChartModel getChartBarByMes() {
        return chartBarByMes;
    }

    public void setChartBarByMes(BarChartModel chartBarByMes) {
        this.chartBarByMes = chartBarByMes;
    }

}
