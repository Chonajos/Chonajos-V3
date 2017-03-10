package com.web.chon.bean;

import com.web.chon.dominio.Bodega;
import com.web.chon.dominio.ComprobantesDigitales;
import com.web.chon.dominio.EntradaMercancia;
import com.web.chon.dominio.EntradaMercanciaProducto;
import com.web.chon.dominio.ExistenciaProducto;
import com.web.chon.dominio.Provedor;
import com.web.chon.dominio.Subproducto;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.TipoEmpaque;
import com.web.chon.dominio.TipoConvenio;
import com.web.chon.dominio.UsuarioDominio;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatBodegas;
import com.web.chon.service.IfaceCatProvedores;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceComprobantes;
import com.web.chon.service.IfaceEmpaque;
import com.web.chon.service.IfaceEntradaMercancia;
import com.web.chon.service.IfaceEntradaMercanciaProducto;
import com.web.chon.service.IfaceNegocioExistencia;
import com.web.chon.service.IfaceSubProducto;
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
import com.web.chon.service.IfaceTipoCovenio;
import java.io.IOException;
import java.io.InputStream;
import java.math.MathContext;
import java.math.RoundingMode;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author fredy
 */
@Component
@Scope("view")
public class BeanEntradaMercancia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired private IfaceEmpaque ifaceEmpaque;
    @Autowired private IfaceTipoCovenio ifaceCovenio;
    @Autowired private IfaceCatBodegas ifaceCatBodegas;
    @Autowired private IfaceSubProducto ifaceSubProducto;
    @Autowired private PlataformaSecurityContext context;
    @Autowired private IfaceComprobantes ifaceComprobantes;
    @Autowired private IfaceCatProvedores ifaceCatProvedores;
    @Autowired private IfaceCatSucursales ifaceCatSucursales;
    @Autowired private IfaceEntradaMercancia ifaceEntradaMercancia;
    @Autowired private IfaceNegocioExistencia ifaceNegocioExistencia;
    @Autowired private IfaceEntradaMercanciaProducto ifaceEntradaMercanciaProducto;

    private EntradaMercancia data;
    private UsuarioDominio usuario;
    private Subproducto subProducto;
    private EntradaMercanciaProducto dataEdit;
    private EntradaMercanciaProducto dataRemove;
    private EntradaMercanciaProducto dataProducto;

    private ArrayList<String> labels;
    private ArrayList<Bodega> listaBodegas;
    private ArrayList<Subproducto> lstProducto;
    private ArrayList<Provedor> listaProvedores;
    private ArrayList<Sucursal> listaSucursales;
    private ArrayList<TipoEmpaque> lstTipoEmpaque;
    private ArrayList<TipoConvenio> listaTiposConvenio;
    private ArrayList<ExistenciaProducto> existencia_repetida;
    private ArrayList<ComprobantesDigitales> listaComprobantes;
    private ArrayList<EntradaMercanciaProducto> listaMercanciaProducto;

    private String title = "";
    private String labelCompra;
    private String viewEstate = "";

    private int year;
    private int movimiento;

    private BigDecimal kilos;
    private BigDecimal totalKilos;
    private BigDecimal cantidadReal;

    private boolean permisionPacto;
    private boolean permisionPrecio;
    private boolean permisionToPush;
    private boolean permisionComision;
    private boolean permisionToGenerate;
    private boolean permisionToEditProducto;

    private byte[] bytes;

    @PostConstruct
    public void init() {
        permisionToEditProducto = false;
        labels = new ArrayList<String>();
        labels.add("Precio");
        labels.add("Precio %");
        labels.add("Pacto");
        labelCompra = "Precio";

        permisionToPush = true;

        lstTipoEmpaque = ifaceEmpaque.getEmpaques();
        listaTiposConvenio = ifaceCovenio.getTipos();
        listaComprobantes = new ArrayList<ComprobantesDigitales>();
        listaSucursales = ifaceCatSucursales.getSucursales();
        listaProvedores = ifaceCatProvedores.getProvedores();
        listaMercanciaProducto = new ArrayList<EntradaMercanciaProducto>();

        usuario = context.getUsuarioAutenticado();
        dataProducto = new EntradaMercanciaProducto();

        data = new EntradaMercancia();
        data.setIdSucursalFK(new BigDecimal(usuario.getSucId()));
        data.setIdUsuario(usuario.getIdUsuario());

        year = 0;
        movimiento = 0;
        permisionPacto = true;
        permisionPrecio = false;
        permisionComision = true;
        permisionToGenerate = true;

        kilos = new BigDecimal(0);
        cantidadReal = new BigDecimal(0);

        existencia_repetida = new ArrayList<ExistenciaProducto>();
        listaBodegas = ifaceCatBodegas.getBodegaByIdSucursal(data.getIdSucursalFK());

        setTitle("Registro Entrada de Mercancia");
        setViewEstate("init");
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        InputStream inputStr = null;
        try {

            inputStr = uploadedFile.getInputstream();
        } catch (IOException e) {
            JsfUtil.addErrorMessage("No se permite guardar valores nulos.");
            manageException(e);
        }

        try {
            bytes = IOUtils.toByteArray(inputStr);
            ComprobantesDigitales cd = new ComprobantesDigitales();
            cd.setFichero(bytes);
            listaComprobantes.add(cd);

            JsfUtil.addSuccessMessageClean("El archivo " + event.getFile().getFileName().trim() + "fue cargado con éxito");
            for (ComprobantesDigitales c : listaComprobantes) {
                System.out.println("Fichero :" + c.getFichero());
            }
        } catch (IOException e) {
            JsfUtil.addErrorMessageClean("Ocurrio un error al cargar el archivo");
            e.printStackTrace();
        }
    }

    public void sumaDias() {
        Date hoy = context.getFechaSistema();
        data.setFechaPago(TiempoUtil.sumarRestarDias(hoy, data.getDiasPago().intValue()));
    }

    public void permisions() {

        labelCompra = labels.get(dataProducto.getIdTipoConvenio().intValue() - 1);
        permisionPrecio = false;
    }

    public void calculaPesoNeto() {
        if (dataProducto.getKilosTotalesProducto() != null && dataProducto.getPesoTara() != null) {

            BigDecimal h = dataProducto.getKilosTotalesProducto().subtract(dataProducto.getPesoTara(), MathContext.UNLIMITED);
            //dataProducto.setPesoNeto(dataProducto.getKilosTotalesProducto().subtract(dataProducto.getPesoTara(), MathContext.UNLIMITED));

            int t = h.compareTo(new BigDecimal(0));
            if (t == 0 || t < 0) {
                JsfUtil.addErrorMessageClean("Cantidad en kilos igual a cero o negativo");
            } else {
                dataProducto.setPesoNeto(h);
            }
        }
    }

    public void calculaCantidadReal() {
        cantidadReal = new BigDecimal(0);
        for (EntradaMercanciaProducto p : listaMercanciaProducto) {
            cantidadReal = cantidadReal.add(p.getCantidadPaquetes(), MathContext.UNLIMITED);
        }
    }

    public boolean validaDatos() {
        if (data.getIdUsuario() == null || data.getIdProvedorFK() == null || data.getIdSucursalFK() == null || data.getAbreviacion() == null || data.getMovimiento() == null || data.getRemision() == null || data.getFechaRemision() == null) {
            return false;
        } else {
            return true;
        }
    }

    public void inserts() {

        int idEntradaMercancia = 0;
        int idCarroSucursal = 0;
        EntradaMercancia entrada_mercancia = new EntradaMercancia();

        try {
            //if (validaDatos()) {
            if (!listaMercanciaProducto.isEmpty() && listaMercanciaProducto.size() > 0) {
                calculaCantidadReal();
                idEntradaMercancia = ifaceEntradaMercancia.getNextVal();
                idCarroSucursal = ifaceEntradaMercancia.getCarroSucursal(data.getIdSucursalFK());
                entrada_mercancia.setIdEmPK(new BigDecimal(idEntradaMercancia));
                entrada_mercancia.setIdUsuario(data.getIdUsuario());
                entrada_mercancia.setIdProvedorFK(data.getIdProvedorFK());
                entrada_mercancia.setIdSucursalFK(data.getIdSucursalFK());
                entrada_mercancia.setAbreviacion(data.getAbreviacion());
                entrada_mercancia.setMovimiento(data.getMovimiento());
                entrada_mercancia.setRemision(data.getRemision());
                entrada_mercancia.setFecha(data.getFecha());
                entrada_mercancia.setFolio(data.getFolio());
                entrada_mercancia.setCantidadEmpaquesProvedor(data.getCantidadEmpaquesProvedor());
                //System.out.println("CantidadReal ===" +cantidadReal);
                entrada_mercancia.setCantidadEmpaquesReales(cantidadReal);
                entrada_mercancia.setKilosTotales(kilos);
                entrada_mercancia.setKilosTotalesProvedor(data.getKilosTotalesProvedor());
                entrada_mercancia.setComentariosGenerales(data.getComentariosGenerales());
                entrada_mercancia.setFechaRemision(data.getFechaRemision());
                entrada_mercancia.setIdCarroSucursal(new BigDecimal(idCarroSucursal + 1));
                entrada_mercancia.setFechaPago(data.getFechaPago());

                int mercanciaOrdenada = ifaceEntradaMercancia.insertEntradaMercancia(entrada_mercancia);
                if (mercanciaOrdenada != 0) {
                    for (ComprobantesDigitales cd : listaComprobantes) {
                        cd.setIdComprobantesDigitalesPk(new BigDecimal(ifaceComprobantes.getNextVal()));
                        cd.setIdTipoFk(new BigDecimal(1));
                        cd.setIdLlaveFk(entrada_mercancia.getIdEmPK());
                        ifaceComprobantes.insertaComprobante(cd);
                        ifaceComprobantes.insertarImagen(cd.getIdComprobantesDigitalesPk(), cd.getFichero());
                    }
                    for (int i = 0; i < listaMercanciaProducto.size(); i++) {
                        EntradaMercanciaProducto producto = new EntradaMercanciaProducto();
                        producto = listaMercanciaProducto.get(i);
                        int idEnTMerPro = ifaceEntradaMercanciaProducto.getNextVal();
                        producto.setIdEmpPK(new BigDecimal(idEnTMerPro));
                        producto.setIdEmFK(new BigDecimal(idEntradaMercancia));
                        producto.setKilospromprod(producto.getPesoNeto().divide(producto.getCantidadPaquetes(), 2, RoundingMode.HALF_EVEN));
                        producto.setKilosProProvedor(producto.getPesoNeto());
                        producto.setEmpaquesProProvedor(producto.getCantidadPaquetes());
                        producto.setKilosTotalesProducto(producto.getPesoNeto());
                        //int idEntradaMercanciaProducto = ifaceEntradaMercanciaProducto.getNextVal();
                        if (ifaceEntradaMercanciaProducto.insertEntradaMercanciaProducto(producto) != 0) {
                            //BUSCAR SI YA EXISTE EN LA TABLA EXISTENCIA PRODUCTO.
                            ExistenciaProducto ep = new ExistenciaProducto();
                            ep.setIdSubProductoFK(producto.getIdSubProductoFK());
                            ep.setIdTipoEmpaqueFK(producto.getIdTipoEmpaqueFK());
                            ep.setKilosTotalesProducto(producto.getPesoNeto());
                            ep.setCantidadPaquetes(producto.getCantidadPaquetes());
                            ep.setComentarios(producto.getComentarios());
                            ep.setIdBodegaFK(producto.getIdBodegaFK());
                            ep.setIdTipoConvenio(producto.getIdTipoConvenio());
                            ep.setPrecio(producto.getPrecio());
                            ep.setKilospromprod(producto.getKilospromprod());
                            ep.setIdSucursal(entrada_mercancia.getIdSucursalFK());
                            ep.setIdProvedor(entrada_mercancia.getIdProvedorFK());
                            ep.setIdEntradaMercanciaProductoFK(new BigDecimal(idEnTMerPro));
                            if (ifaceNegocioExistencia.insertExistenciaProducto(ep) == 1) {
                                JsfUtil.addSuccessMessageClean("¡Registro de Mercancias correcto !");
                            } else {
                                JsfUtil.addErrorMessage("Error!", "Ocurrio un error al registrar la mercancia en existencias");
                            }

                        } else {
                            JsfUtil.addErrorMessage("Error!", "Ocurrio un error al registrar un producto de la entrada de Mercancia");
                        }

                    } //fin for

                    data.reset();
                    listaMercanciaProducto.clear();
                    dataProducto.reset();
                    kilos = new BigDecimal(0);
                    setViewEstate("init");
                    permisionToPush = true;
                    permisionToGenerate = true;
                    reset();

                } else {
                    JsfUtil.addErrorMessage("Error!", "Ocurrio un error al registrar la mercancia");
                }
            } else {
                JsfUtil.addErrorMessage("Error!", "Necesitas agregar al menos un producto para realizar la orden de venta.");

            }

        } catch (StackOverflowError ex) {
            ex.printStackTrace();
            JsfUtil.addErrorMessage("Error!", "Ocurrio un error .");

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error!", "Ocurrio un error ");
            e.printStackTrace();

        }
    }

    public void reset() {
        listaBodegas = ifaceCatBodegas.getBodegaByIdSucursal(data.getIdSucursalFK());
        data.setRemision(null);
        data.setFolio(null);
        data.setAbreviacion(null);
        permisionToPush = true;

    }

    public void remove() {

        listaMercanciaProducto.remove(dataRemove);
        sumaTotales();

    }

    public void editProducto() {
        permisionToEditProducto = true;
        subProducto = ifaceSubProducto.getSubProductoById(dataEdit.getIdSubProductoFK());
        dataProducto.setIdSubProductoFK(dataEdit.getIdSubProductoFK());
        dataProducto.setNombreProducto(dataEdit.getNombreProducto());
        dataProducto.setIdTipoEmpaqueFK(dataEdit.getIdTipoEmpaqueFK());
        dataProducto.setNombreEmpaque(dataEdit.getNombreEmpaque());
        dataProducto.setCantidadPaquetes(dataEdit.getCantidadPaquetes());
        dataProducto.setPrecio(dataEdit.getPrecio());
        dataProducto.setKilosTotalesProducto(dataEdit.getKilosTotalesProducto());
        dataProducto.setComentarios(dataEdit.getComentarios());
        dataProducto.setIdBodegaFK(dataEdit.getIdBodegaFK());
        dataProducto.setPesoTara(dataEdit.getPesoTara());
        dataProducto.setIdTipoConvenio(dataEdit.getIdTipoConvenio());
        dataProducto.setNombreTipoConvenio(dataEdit.getNombreTipoConvenio());
        viewEstate = "update";
        dataProducto.setPesoNeto(dataEdit.getPesoNeto());

    }

    public void sumaTotales() {
        kilos = new BigDecimal(0);
        for (EntradaMercanciaProducto producto : listaMercanciaProducto) {
            kilos = kilos.add(producto.getPesoNeto(), MathContext.UNLIMITED);
        }
    }

    public void cancel() {
        dataProducto.reset();
        subProducto = new Subproducto();
        viewEstate = "init";
    }

    public void updateProducto() {
        permisionToEditProducto = false;
        EntradaMercanciaProducto p = new EntradaMercanciaProducto();
        TipoEmpaque empaque = new TipoEmpaque();
        dataEdit.setIdSubProductoFK(subProducto.getIdSubproductoPk());
        dataEdit.setNombreProducto(subProducto.getNombreSubproducto());
        dataEdit.setIdTipoEmpaqueFK(dataProducto.getIdTipoEmpaqueFK());
        empaque = getEmpaque(dataProducto.getIdTipoEmpaqueFK());
        dataEdit.setNombreEmpaque(empaque.getNombreEmpaque());
        dataEdit.setIdTipoConvenio(dataProducto.getIdTipoConvenio());
        TipoConvenio to = new TipoConvenio();
        to = getTipoConvenio(dataProducto.getIdTipoConvenio());
        dataEdit.setIdBodegaFK(dataProducto.getIdBodegaFK());
        Bodega b = new Bodega();
        b = getBodega(dataProducto.getIdBodegaFK());
        p.setNombreBodega(b.getNombreBodega());
        dataEdit.setNombreBodega(p.getNombreBodega());
        dataEdit.setNombreTipoConvenio(to.getNombreTipoConvenio());
        dataEdit.setCantidadPaquetes(dataProducto.getCantidadPaquetes());
        dataEdit.setPrecio(dataProducto.getPrecio());
        dataEdit.setKilosTotalesProducto(dataProducto.getKilosTotalesProducto());
        p.setKilosTotalesProducto(dataProducto.getKilosTotalesProducto().subtract((dataProducto.getPesoTara() == null ? new BigDecimal(0) : dataProducto.getPesoTara()), MathContext.UNLIMITED));
        dataEdit.setComentarios(dataProducto.getComentarios());
        dataEdit.setPesoTara(dataProducto.getPesoTara());
        dataEdit.setPesoNeto(dataProducto.getPesoNeto());
        kilos = kilos.add(dataProducto.getPesoNeto(), MathContext.UNLIMITED);
        viewEstate = "init";
        subProducto = new Subproducto();
        dataProducto.reset();
        sumaTotales();

    }

    public void buscaMovimiento() {
        data.setFechaFiltroInicio(TiempoUtil.getDayOneYear(new Date()));
        data.setFechaFiltroFin(TiempoUtil.getDayEndYear(new Date()));
        movimiento = ifaceEntradaMercancia.buscaMaxMovimiento(data);
        year = TiempoUtil.getYear(data.getFechaFiltroInicio());
        movimiento = movimiento + 1;
        updateAbreviacion();
        if (data.getRemision() == null) {
            data.setRemision("S/R");
        }
        data.setMovimiento(new BigDecimal(movimiento));
        data.setFolio(data.getAbreviacion() + "-" + year + "-" + movimiento + ":" + data.getRemision());
        permisionToPush = false;

    }

    public void addProducto() {
        EntradaMercanciaProducto p = new EntradaMercanciaProducto();
        TipoEmpaque empaque = new TipoEmpaque();
        TipoConvenio to = new TipoConvenio();
        p.setIdSubProductoFK(subProducto.getIdSubproductoPk());
        p.setNombreProducto(subProducto.getNombreSubproducto());
        p.setIdTipoEmpaqueFK(dataProducto.getIdTipoEmpaqueFK());
        empaque = getEmpaque(dataProducto.getIdTipoEmpaqueFK());
        p.setNombreEmpaque(empaque.getNombreEmpaque());
        p.setCantidadPaquetes(dataProducto.getCantidadPaquetes());
        p.setPrecio(dataProducto.getPrecio());
        p.setPesoNeto(dataProducto.getKilosTotalesProducto().subtract((dataProducto.getPesoTara() == null ? new BigDecimal(0) : dataProducto.getPesoTara()), MathContext.UNLIMITED));
        p.setKilosTotalesProducto(dataProducto.getKilosTotalesProducto());

        if (p.getPesoNeto().compareTo(new BigDecimal(0)) <= 0) {
            JsfUtil.addErrorMessageClean("El peso de la tara es mayor que el producto");
        } else {

            p.setComentarios(dataProducto.getComentarios());
            to = getTipoConvenio(dataProducto.getIdTipoConvenio());
            p.setNombreTipoConvenio(to.getNombreTipoConvenio());
            p.setIdTipoConvenio(dataProducto.getIdTipoConvenio());
            Bodega b = new Bodega();
            b = getBodega(dataProducto.getIdBodegaFK());
            p.setNombreBodega(b.getNombreBodega());
            p.setIdBodegaFK(dataProducto.getIdBodegaFK());
            p.setPesoTara(dataProducto.getPesoTara() == null ? new BigDecimal(0) : dataProducto.getPesoTara());

            boolean bandera = false;
            for (EntradaMercanciaProducto producto : listaMercanciaProducto) {
                if (producto.getIdSubProductoFK().equals(p.getIdSubProductoFK()) && producto.getIdTipoEmpaqueFK().intValue() == p.getIdTipoEmpaqueFK().intValue() && producto.getIdTipoConvenio().intValue() == p.getIdTipoConvenio().intValue() && producto.getIdBodegaFK().intValue() == p.getIdBodegaFK().intValue()) {
                    if (producto.getPrecio().setScale(2, RoundingMode.CEILING).compareTo(p.getPrecio().setScale(2, RoundingMode.CEILING)) == 0) {
                        bandera = true;
                    }
                }
            }
            if (!bandera) {
                listaMercanciaProducto.add(p);
                permisionToGenerate = false;
                dataProducto.reset();
                subProducto = new Subproducto();
                listaTiposConvenio = ifaceCovenio.getTipos();

                JsfUtil.addSuccessMessageClean("Producto agregado correctamente");
            } else {
                JsfUtil.addErrorMessageClean("Este Producto ya se encuentra en la lista, modificar existente");
            }
            sumaTotales();
        }

    }

    private Bodega getBodega(BigDecimal idBodega) {
        Bodega b = new Bodega();

        for (Bodega bodeguita : listaBodegas) {
            if (bodeguita.getIdBodegaPK().equals(idBodega)) {
                b = bodeguita;
                break;
            }
        }
        return b;
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

    private TipoConvenio getTipoConvenio(BigDecimal idTipoConvenio) {
        TipoConvenio compra = new TipoConvenio();

        for (TipoConvenio tipoOrden : listaTiposConvenio) {
            if (tipoOrden.getIdTcPK().equals(idTipoConvenio)) {
                compra = tipoOrden;
                break;
            }
        }
        return compra;
    }

    public void updateAbreviacion() {

        for (Provedor p : listaProvedores) {
            if (p.getIdProvedorPK().intValue() == data.getIdProvedorFK().intValue()) {
                data.setAbreviacion(p.getNickName());
            }

        }

    }
    
     public ArrayList<Subproducto> autoComplete(String nombreProducto) {
        lstProducto = ifaceSubProducto.getSubProductoByNombre(nombreProducto.toUpperCase());
        return lstProducto;

    }

    public int getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    public int getYear() {
        return year;
    }

    //Getters y Setters
    public void setYear(int year) {
        this.year = year;
    }

    public IfaceCatSucursales getIfaceCatSucursales() {
        return ifaceCatSucursales;
    }

    public void setIfaceCatSucursales(IfaceCatSucursales ifaceCatSucursales) {
        this.ifaceCatSucursales = ifaceCatSucursales;
    }

    public ArrayList<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(ArrayList<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public IfaceCatProvedores getIfaceCatProvedores() {
        return ifaceCatProvedores;
    }

    public void setIfaceCatProvedores(IfaceCatProvedores ifaceCatProvedores) {
        this.ifaceCatProvedores = ifaceCatProvedores;
    }

    public ArrayList<Provedor> getListaProvedores() {
        return listaProvedores;
    }

    public void setListaProvedores(ArrayList<Provedor> listaProvedores) {
        this.listaProvedores = listaProvedores;
    }

    public EntradaMercancia getData() {
        return data;
    }

    public void setData(EntradaMercancia data) {
        this.data = data;
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

    public IfaceEntradaMercancia getIfaceEntradaMercancia() {
        return ifaceEntradaMercancia;
    }

    public void setIfaceEntradaMercancia(IfaceEntradaMercancia ifaceEntradaMercancia) {
        this.ifaceEntradaMercancia = ifaceEntradaMercancia;
    }

    public ArrayList<EntradaMercanciaProducto> getListaMercanciaProducto() {
        return listaMercanciaProducto;
    }

    public void setListaMercanciaProducto(ArrayList<EntradaMercanciaProducto> listaMercanciaProducto) {
        this.listaMercanciaProducto = listaMercanciaProducto;
    }

    public EntradaMercanciaProducto getDataProducto() {
        return dataProducto;
    }

    public void setDataProducto(EntradaMercanciaProducto dataProducto) {
        this.dataProducto = dataProducto;
    }

    public Subproducto getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(Subproducto subProducto) {
        this.subProducto = subProducto;
    }

    public ArrayList<TipoEmpaque> getLstTipoEmpaque() {
        return lstTipoEmpaque;
    }

    public void setLstTipoEmpaque(ArrayList<TipoEmpaque> lstTipoEmpaque) {
        this.lstTipoEmpaque = lstTipoEmpaque;
    }

    public IfaceEmpaque getIfaceEmpaque() {
        return ifaceEmpaque;
    }

    public void setIfaceEmpaque(IfaceEmpaque ifaceEmpaque) {
        this.ifaceEmpaque = ifaceEmpaque;
    }

    public boolean isPermisionToPush() {
        return permisionToPush;
    }

    public void setPermisionToPush(boolean permisionToPush) {
        this.permisionToPush = permisionToPush;
    }

    public EntradaMercanciaProducto getDataRemove() {
        return dataRemove;
    }

    public void setDataRemove(EntradaMercanciaProducto dataRemove) {
        this.dataRemove = dataRemove;
    }

    public EntradaMercanciaProducto getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(EntradaMercanciaProducto dataEdit) {
        this.dataEdit = dataEdit;
    }

    public IfaceSubProducto getIfaceSubProducto() {
        return ifaceSubProducto;
    }

    public void setIfaceSubProducto(IfaceSubProducto ifaceSubProducto) {
        this.ifaceSubProducto = ifaceSubProducto;
    }

    public BigDecimal getTotalKilos() {
        return totalKilos;
    }

    public void setTotalKilos(BigDecimal totalKilos) {
        this.totalKilos = totalKilos;
    }

    public IfaceTipoCovenio getIfaceCovenio() {
        return ifaceCovenio;
    }

    public void setIfaceCovenio(IfaceTipoCovenio ifaceCovenio) {
        this.ifaceCovenio = ifaceCovenio;
    }

    public boolean isPermisionPacto() {
        return permisionPacto;
    }

    public void setPermisionPacto(boolean permisionPacto) {
        this.permisionPacto = permisionPacto;
    }

    public boolean isPermisionComision() {
        return permisionComision;
    }

    public void setPermisionComision(boolean permisionComision) {
        this.permisionComision = permisionComision;
    }

    public boolean isPermisionPrecio() {
        return permisionPrecio;
    }

    public void setPermisionPrecio(boolean permisionPrecio) {
        this.permisionPrecio = permisionPrecio;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public String getLabelCompra() {
        return labelCompra;
    }

    public void setLabelCompra(String labelCompra) {
        this.labelCompra = labelCompra;
    }

    public IfaceEntradaMercanciaProducto getIfaceEntradaMercanciaProducto() {
        return ifaceEntradaMercanciaProducto;
    }

    public void setIfaceEntradaMercanciaProducto(IfaceEntradaMercanciaProducto ifaceEntradaMercanciaProducto) {
        this.ifaceEntradaMercanciaProducto = ifaceEntradaMercanciaProducto;
    }

    public boolean isPermisionToGenerate() {
        return permisionToGenerate;
    }

    public void setPermisionToGenerate(boolean permisionToGenerate) {
        this.permisionToGenerate = permisionToGenerate;
    }

    public IfaceCatBodegas getIfaceCatBodegas() {
        return ifaceCatBodegas;
    }

    public void setIfaceCatBodegas(IfaceCatBodegas ifaceCatBodegas) {
        this.ifaceCatBodegas = ifaceCatBodegas;
    }

    public ArrayList<Bodega> getListaBodegas() {
        return listaBodegas;
    }

    public void setListaBodegas(ArrayList<Bodega> listaBodegas) {
        this.listaBodegas = listaBodegas;
    }

    public BigDecimal getKilos() {
        return kilos;
    }

    public void setKilos(BigDecimal kilos) {
        this.kilos = kilos;
    }

    public ArrayList<TipoConvenio> getListaTiposConvenio() {
        return listaTiposConvenio;
    }

    public void setListaTiposConvenio(ArrayList<TipoConvenio> listaTiposConvenio) {
        this.listaTiposConvenio = listaTiposConvenio;
    }

    public boolean isPermisionToEditProducto() {
        return permisionToEditProducto;
    }

    public void setPermisionToEditProducto(boolean permisionToEditProducto) {
        this.permisionToEditProducto = permisionToEditProducto;
    }

    public ArrayList<ExistenciaProducto> getExistencia_repetida() {
        return existencia_repetida;
    }

    public void setExistencia_repetida(ArrayList<ExistenciaProducto> existencia_repetida) {
        this.existencia_repetida = existencia_repetida;
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDominio usuario) {
        this.usuario = usuario;
    }

    public ArrayList<ComprobantesDigitales> getListaComprobantes() {
        return listaComprobantes;
    }

    public void setListaComprobantes(ArrayList<ComprobantesDigitales> listaComprobantes) {
        this.listaComprobantes = listaComprobantes;
    }

    private void manageException(IOException e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
