package com.web.chon.bean;

import com.web.chon.dominio.Cliente;
import com.web.chon.dominio.MantenimientoPrecios;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.Usuario;
import com.web.chon.dominio.Venta;
import com.web.chon.dominio.VentaProducto;
import com.web.chon.service.IfaceCatCliente;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceMantenimientoPrecio;
import com.web.chon.service.IfaceSubProducto;
import com.web.chon.service.IfaceVenta;
import com.web.chon.service.IfaceVentaProducto;
import com.web.chon.util.NumeroALetra;
import com.web.chon.util.Utilerias;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
    @Autowired
    IfaceCatCliente ifaceCatCliente;
    @Autowired
    IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    IfaceMantenimientoPrecio ifaceMantenimientoPrecio;
    @Autowired
    BeanUsuario beanUsuario;
    private ArrayList<VentaProducto> lstVenta;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;
    private ArrayList<Usuario> lstUsuario;
    private ArrayList<Cliente> lstCliente;

    private VentaProducto ventaProducto;
    private Subproducto subProducto;
    private VentaProducto dataRemove;
    private VentaProducto dataEdit;
    private Usuario usuario;
    private Cliente cliente;
    private String title = "";
    private String nombreEmpaque = "";
    private String viewEstate = "";
    private BigDecimal totalVenta;
    private VentaProducto data;
    private Venta venta;
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
        usuario = beanUsuario.getUsuario();
        venta = new Venta();
        data = new VentaProducto();
        data.setIdTipoEmpaqueFk(new BigDecimal(-1));
        lstProducto = new ArrayList<Subproducto>();
        lstTipoEmpaque = ifaceEmpaque.getEmpaques();
        lstVenta = new ArrayList<VentaProducto>();

        selectedTipoEmpaque();
        setTitle("Venta de Productos.");
        setViewEstate("init");

    }

    public void selectedTipoEmpaque() {

        for (TipoEmpaque empaque : lstTipoEmpaque) {

            if ((empaque.getNombreEmpaque().equals("Kilos") && data.getIdTipoEmpaqueFk().equals(new BigDecimal(-1))) || data.getIdTipoEmpaqueFk().equals(empaque.getIdTipoEmpaquePk())) {
                data.setIdTipoEmpaqueFk(empaque.getIdTipoEmpaquePk());
                setNombreEmpaque(empaque.getNombreEmpaque());
                break;
            }

        }

        searchById();

    }

    public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public ArrayList<Cliente> autoCompleteCliente(String nombreCliente) {
        lstCliente = ifaceCatCliente.getClienteByNombreCompleto(nombreCliente.toUpperCase());
        return lstCliente;

    }

    public ArrayList<Usuario> autoCompleteVendedor(String nombreUsuario) {
        lstUsuario = ifaceCatUsuario.getUsuarioByNombreCompleto(nombreUsuario.toUpperCase());
        return lstUsuario;

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
            if (!lstVenta.isEmpty() && lstVenta.size() > 0) {

                idVenta = ifaceVenta.getNextVal();

                venta.setIdVentaPk(new BigDecimal(idVenta));
                venta.setIdClienteFk(new BigDecimal(cliente.getId_cliente()));
                venta.setIdVendedorFk(usuario.getIdUsuarioPk());
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
            ex.printStackTrace();
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

        MantenimientoPrecios mantenimentoPrecio = new MantenimientoPrecios();
        int idEmpaque = data.getIdTipoEmpaqueFk() == null ? 0 : data.getIdTipoEmpaqueFk().intValue();
        String idSubProducto = subProducto == null ? "" : (subProducto.getIdSubproductoPk() == null ? "" : subProducto.getIdSubproductoPk());
        mantenimentoPrecio = ifaceMantenimientoPrecio.getMantenimientoPrecioById(idSubProducto.trim(), idEmpaque);

        data.setIdProductoFk(idSubProducto);
        data.setIdTipoEmpaqueFk(new BigDecimal(idEmpaque));
        data.setPrecioProducto(mantenimentoPrecio.getPrecioVenta() == null ? null : mantenimentoPrecio.getPrecioVenta().toBigInteger());
    }

    public void addProducto() {

        VentaProducto venta = new VentaProducto();
        TipoEmpaque empaque = new TipoEmpaque();
        empaque = getEmpaque(data.getIdTipoEmpaqueFk());

        venta.setCantidadEmpaque(data.getCantidadEmpaque());

        venta.setIdTipoEmpaqueFk(data.getIdTipoEmpaqueFk());
        venta.setIdVentaProductoPk(data.getIdVentaProductoPk());
        venta.setKilosVenta(data.getKilosVenta());
        venta.setPrecioProducto(data.getPrecioProducto());
        venta.setNombreProducto(subProducto.getNombreSubproducto());
        venta.setIdProductoFk(subProducto.getIdSubproductoPk());
        venta.setNombreEmpaque(empaque.getNombreEmpaque());

        venta.setTotal(new BigDecimal(venta.getPrecioProducto()).multiply(venta.getCantidadEmpaque()));
        lstVenta.add(venta);
        calcularTotalVenta();

        data.reset();
        subProducto = new Subproducto();
        selectedTipoEmpaque();

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

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("###.##");
        Date date = new Date();
        String productos = "";
        NumeroALetra numeroLetra = new NumeroALetra();
        for (VentaProducto venta : lstVenta) {
            String cantidad = venta.getCantidadEmpaque() + " " + venta.getNombreEmpaque();
            productos += line + cantidad + " " + venta.getNombreProducto() + " " + nf.format(venta.getPrecioProducto()) + " " + nf.format(venta.getTotal()) + "\n";
        }

        String totalVentaStr = numeroLetra.Convertir(df.format(totalVenta), true);
        putValues("ChonAjos", "1", "Q85", Utilerias.getFechaDDMMYYYYHHMM(date), productos, nf.format(totalVenta), totalVentaStr, idVenta);
        imprimirDefault();

    }

    private void putValues(String nameLocal, String box, String caissier, String dateTime, String items, String total, String totalVentaStr, int idVenta) {

        contentTicket = contentTicket.replace("{{nameLocal}}", nameLocal);
        contentTicket = contentTicket.replace("{{box}}", box);
        contentTicket = contentTicket.replace("{{cajero}}", caissier);
        contentTicket = contentTicket.replace("{{dateTime}}", dateTime);
        contentTicket = contentTicket.replace("{{items}}", items);
        contentTicket = contentTicket.replace("{{total}}", total);
        contentTicket = contentTicket.replace("{{totalLetra}}", totalVentaStr);
        contentTicket = contentTicket.replace("{{valeNum}}", Integer.toString(idVenta));
        contentTicket = contentTicket.replace("{{dateTime}}", dateTime);
        contentTicket = contentTicket.replace("{{vendedor}}", usuario.getNombreCompletoUsuario());
        contentTicket = contentTicket.replace("{{cliente}}", cliente.getNombreCombleto());

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

    public void editProducto() {

        subProducto = ifaceSubProducto.getSubProductoById(dataEdit.getIdProductoFk());
        autoComplete(dataEdit.getNombreProducto());
        searchById();
        data.setCantidadEmpaque(dataEdit.getCantidadEmpaque());
        data.setIdProductoFk(dataEdit.getIdProductoFk());
        data.setIdTipoEmpaqueFk(dataEdit.getIdTipoEmpaqueFk());
        data.setIdVentaProductoPk(dataEdit.getIdVentaProductoPk());
        data.setKilosVenta(dataEdit.getKilosVenta());
        data.setPrecioProducto(dataEdit.getPrecioProducto());
        data.setNombreProducto(dataEdit.getNombreProducto());
        data.setIdProductoFk(dataEdit.getIdProductoFk());

        viewEstate = "update";

        System.out.println("data :" + data.toString());

    }

    public void updateProducto() {

        TipoEmpaque empaque = new TipoEmpaque();
        empaque = getEmpaque(data.getIdTipoEmpaqueFk());

        dataEdit.setCantidadEmpaque(data.getCantidadEmpaque());
        dataEdit.setIdProductoFk(data.getIdProductoFk());
        dataEdit.setIdTipoEmpaqueFk(data.getIdTipoEmpaqueFk());
        dataEdit.setIdVentaProductoPk(data.getIdVentaProductoPk());
        dataEdit.setKilosVenta(data.getKilosVenta());
        dataEdit.setPrecioProducto(data.getPrecioProducto());
        dataEdit.setNombreProducto(data.getNombreProducto());
        dataEdit.setIdProductoFk(data.getIdProductoFk());
        dataEdit.setNombreEmpaque(empaque.getNombreEmpaque());

        dataEdit.setTotal(new BigDecimal(dataEdit.getPrecioProducto()).multiply(dataEdit.getCantidadEmpaque()));

        calcularTotalVenta();
        viewEstate = "init";
        data.reset();
    }

    public void cancel() {
        subProducto = new Subproducto();

        viewEstate = "init";

        data.reset();
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

    public VentaProducto getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(VentaProducto dataEdit) {
        this.dataEdit = dataEdit;
    }

    public String getNombreEmpaque() {
        return nombreEmpaque;
    }

    public void setNombreEmpaque(String nombreEmpaque) {
        this.nombreEmpaque = nombreEmpaque;
    }

    public ArrayList<Usuario> getLstUsuario() {
        return lstUsuario;
    }

    public void setLstUsuario(ArrayList<Usuario> lstUsuario) {
        this.lstUsuario = lstUsuario;
    }

    public ArrayList<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(ArrayList<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
