package com.web.chon.bean;

import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.Venta;
import com.web.chon.dominio.VentaProducto;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceVenta;
import com.web.chon.service.IfaceVentaProducto;
import com.web.chon.util.NumeroALetra;
import com.web.chon.util.Utilerias;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
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
public class BeanVenta implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceVenta ifaceVenta;
    @Autowired
    private IfaceEmpaque ifaceEmpaque;
    @Autowired
    private IfaceSubProducto ifaceSubProducto;
    @Autowired
    private IfaceVentaProducto ifaceVentaProducto;

    private ArrayList<VentaProducto> lstVenta;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;

    private VentaProducto ventaProducto;
    private Subproducto subProducto;
    private VentaProducto dataRemove;
//   private BeanUsuario beanUsuario;

    private String title = "";
    private String viewEstate = "";
    private BigDecimal totalVenta;
    private VentaProducto data;
    private Venta venta;
    private String line = "___________________________________________\n";
    private String contentTicket = "" + (char) 27 + (char) 112 + (char) 0 + (char) 10 + (char) 100 + "\033[1m             COMERCIALIZADORA Y \n"
            + "\033[0m             EXPORTADORA CHONAJOS\033[0m\n"
            + "\033[0m                 S DE RL DE CV\033[0m\n"
            + "\033[0m                55-56-40-58-46\033[0m\n"
            + "\033[0m                   Bod.  Q85\033[0m\n"
            + "\033[0m                 VALE DE VENTA\033[0m\n"
            + "\033[0m                    {{dateTime}}\033[0m\n"
            + "Vale No. {{valeNum}}     \n"
            + "C:{{cliente}}\n"
            + "BULT/CAJ    PRODUCTO     PRECIO      TOTAL\n"
            + "{{items}}\n"
            + line
            + "\033[1mVENTA:        {{total}}\n"
            + "{{totalLetra}}\n\n"
            + "\033[1m               P A G A D O\033[0m"
            + "\n" + (char) 27 + (char) 112 + (char) 0 + (char) 10 + (char) 100 + "\n"
            + (char) 27 + "m";

    @PostConstruct
    public void init() {

        venta = new Venta();
        data = new VentaProducto();
        data.setIdTipoEmpaqueFk(new BigDecimal(-1));
        lstProducto = new ArrayList<Subproducto>();
        subProducto = new Subproducto();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();
        lstVenta = new ArrayList<VentaProducto>();
        //System.out.println("Nombre de usuario :" + beanUsuario.getUsuario().getNombreUsuario());

        setTitle("Venta de Productos.");
        setViewEstate("init");

    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    @Override
    public String delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String insert() {
        int idVenta = 0;
        Venta venta = new Venta();
        try {
            if (lstProducto.size() > 0) {

                idVenta = ifaceVenta.getNextVal();

                venta.setIdVentaPk(new BigDecimal(idVenta));
                int ventaInsertada = ifaceVenta.insertarVenta(venta);
                if (ventaInsertada != 0) {
                    for (VentaProducto producto : lstVenta) {

                        ifaceVentaProducto.insertarVentaProducto(producto, idVenta);
                    }
                    imprimirTicket(idVenta);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "La venta se realizo correctamente."));

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Ocurrio un error al insertar la venta."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Necesitas agregar al menos un producto para realizar la venta."));
            }

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.toString()));
        }
        return "venta";
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void searchById() {

        if (subProducto.getIdSubproductoPk() != null) {
            subProducto = ifaceSubProducto.getSubProductoById(subProducto.getIdSubproductoPk());
            data.setPrecioProducto(subProducto.getPrecioVenta());
            data.setIdProductoFk(subProducto.getIdSubproductoPk());
            data.setNombreProducto(subProducto.getNombreSubproducto());
        }
    }

    public void addProducto() {

        VentaProducto venta = new VentaProducto();
        TipoEmpaque empaque = new TipoEmpaque();
        empaque = getEmpaque(data.getIdTipoEmpaqueFk());

        venta.setCantidadEmpaque(data.getCantidadEmpaque());
        venta.setIdProductoFk(data.getIdProductoFk());
        venta.setIdTipoEmpaqueFk(data.getIdTipoEmpaqueFk());
        venta.setIdVentaProductoPk(data.getIdVentaProductoPk());
        venta.setKilosVenta(data.getKilosVenta());
        venta.setPrecioProducto(data.getPrecioProducto());
        venta.setNombreProducto(data.getNombreProducto());
        venta.setIdProductoFk(data.getIdProductoFk());
        venta.setNombreEmpaque(empaque.getNombreEmpaque());

        venta.setTotal(venta.getIdTipoEmpaqueFk().equals(new BigDecimal(-1)) ? venta.getKilosVenta().multiply(new BigDecimal(venta.getPrecioProducto())) : new BigDecimal(venta.getPrecioProducto().multiply(venta.getCantidadEmpaque())));
        lstVenta.add(venta);
        calcularTotalVenta();

        data.reset();
    }

    private void calcularTotalVenta() {
        setTotalVenta(new BigDecimal(0));
        for (VentaProducto venta : lstVenta) {
            setTotalVenta(getTotalVenta().add(venta.getTotal()));
        }
    }

    private TipoEmpaque getEmpaque(BigDecimal idEmpaque) {
        TipoEmpaque empaque = new TipoEmpaque();

        for (TipoEmpaque tipoEmpaque : lstTipoEmpaque) {
            if (tipoEmpaque.getIdTipoEmpaquePk().equals(idEmpaque)) {
                empaque = tipoEmpaque;
                break;
            }
        }
        return empaque;
    }

    public void remove() {
        lstVenta.remove(dataRemove);
        calcularTotalVenta();
    }

    private void imprimirTicket(int idVenta) {

        String productos = "";
        NumeroALetra numeroLetra = new NumeroALetra();
        for (VentaProducto venta : lstVenta) {
            String cantidad = venta.getIdTipoEmpaqueFk().equals(new BigDecimal(-1)) ? venta.getKilosVenta() + " Kilos" : venta.getCantidadEmpaque() + " " + venta.getNombreEmpaque();
            productos += line + cantidad + " " + venta.getNombreProducto() + " $" + venta.getPrecioProducto().toString() + " $" + venta.getTotal().toString() + "\n";
        }
        DecimalFormat df = new DecimalFormat("###.##");
        System.out.println("df " + df.format(totalVenta));
        String totalVentaStr = numeroLetra.Convertir(df.format(totalVenta), true);

        putValues("ChonAjos", "1", "Q85", "10/03/2016", productos, df.format(totalVenta), totalVentaStr, idVenta);
        imprimirDefault();

    }

    private void putValues(String nameLocal, String box, String caissier, String dateTime, String items, String total, String totalVentaStr, int idVenta) {

        Date date = new Date();

        contentTicket = contentTicket.replace("{{nameLocal}}", nameLocal);
        contentTicket = contentTicket.replace("{{box}}", box);
        contentTicket = contentTicket.replace("{{cajero}}", caissier);
        contentTicket = contentTicket.replace("{{dateTime}}", dateTime);
        contentTicket = contentTicket.replace("{{items}}", items);
        contentTicket = contentTicket.replace("{{total}}", total);
        contentTicket = contentTicket.replace("{{totalLetra}}", totalVentaStr);
        contentTicket = contentTicket.replace("{{valeNum}}", Integer.toString(idVenta));
        contentTicket = contentTicket.replace("{{dateTime}}", Utilerias.getFechaDDMMYYYYHHMM(date));

    }

    private String espacios(String str, int espacios) {

        return str;

    }

    public void imprimirDefault() {

        byte[] bytes;
        bytes = contentTicket.getBytes();
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(bytes, flavor, null);
        System.out.println(contentTicket);

        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            try {
                printJob.print(doc, attributeSet);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No existen impresoras instaladas."));
        }

    }

    public ArrayList<VentaProducto> getLstVenta() {
        return lstVenta;
    }

    public void setLstVenta(ArrayList<VentaProducto> lstVenta) {
        this.lstVenta = lstVenta;
    }

    public VentaProducto getVentaProducto() {
        return ventaProducto;
    }

    public void setVentaProducto(VentaProducto ventaProducto) {
        this.ventaProducto = ventaProducto;
    }

    public ArrayList<Subproducto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Subproducto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public ArrayList<TipoEmpaque> getLstTipoEmpaque() {
        return lstTipoEmpaque;
    }

    public void setLstTipoEmpaque(ArrayList<TipoEmpaque> lstTipoEmpaque) {
        this.lstTipoEmpaque = lstTipoEmpaque;
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

    public VentaProducto getData() {
        return data;
    }

    public void setData(VentaProducto data) {
        this.data = data;
    }

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

    public VentaProducto getDataRemove() {
        return dataRemove;
    }

    public void setDataRemove(VentaProducto dataRemove) {
        this.dataRemove = dataRemove;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public String getContentTicket() {
        return contentTicket;
    }

    public void setContentTicket(String contentTicket) {
        this.contentTicket = contentTicket;
    }

}
