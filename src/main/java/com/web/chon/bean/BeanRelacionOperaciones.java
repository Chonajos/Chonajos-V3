package com.web.chon.bean;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.RelacionOperaciones;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.Venta;
import com.web.chon.dominio.VentaProducto;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceVenta;
import com.web.chon.service.IfaceVentaProducto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para la Relacion de Operaciones
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanRelacionOperaciones implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceVenta ifaceVenta;
    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceVentaProducto ifaceVentaProducto;

    private BeanUsuario beanUsuario;
    private RelacionOperaciones data;
    private ArrayList<RelacionOperaciones> model;

    private String title;
    private String viewEstate;
    private int filtro;
    private Date fechaInicio;
    private Date fechaFin;
    private String line = "_______________________________________________\n";
    private String contentTicket = "              COMERCIALIZADORA Y \n"
            + "             EXPORTADORA CHONAJOS\n"
            + "                 S DE RL DE CV\n"
            + "                55-56-40-58-46\n"
            + "                   Bod.  Q85\n"
            + "                 VALE DE VENTA\n"
            + "                {{dateTime}}\n"
            + "Vale No. {{valeNum}}     \n"
            + "C:{{cliente}}\n"
            + "Vendedor:{{vendedor}}\n"
            + "BULT/CAJ    PRODUCTO     PRECIO      TOTAL\n"
            + "{{items}}\n"
            + line
            + "VENTA:        {{total}}\n"
            + "{{totalLetra}}\n\n\n"
            + "               P A G A D O\n\n"
            + "\n" + (char) 27 + (char) 112 + (char) 0 + (char) 10 + (char) 100 + "\n"
            + (char) 27 + "m";

    @PostConstruct
    public void init() {

        model = new ArrayList<RelacionOperaciones>();
        data = new RelacionOperaciones();
        setTitle("Relación de Operaciones.");
        setViewEstate("init");

    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String insert() {

        return "relacionOperaciones";
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchById() {
        viewEstate = "searchById";

    }
    
    public void setFechaInicioFin(){
        
    }

    public void cancel() {
        viewEstate = "init";
    }
    
    public void detallesVenta(){
        
    }

    public String getContentTicket() {
        return contentTicket;
    }

    public void setContentTicket(String contentTicket) {
        this.contentTicket = contentTicket;
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

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public RelacionOperaciones getData() {
        return data;
    }

    public void setData(RelacionOperaciones data) {
        this.data = data;
    }

    public ArrayList<RelacionOperaciones> getModel() {
        return model;
    }

    public void setModel(ArrayList<RelacionOperaciones> model) {
        this.model = model;
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
