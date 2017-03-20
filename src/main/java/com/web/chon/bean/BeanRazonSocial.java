package com.web.chon.bean;

import com.web.chon.dominio.DatosFacturacion;
import com.web.chon.dominio.FileDominio;
import com.web.chon.dominio.Municipios;
import com.web.chon.dominio.CodigoPostal;
import com.web.chon.dominio.Entidad;
import com.web.chon.service.IfaceCatCodigosPostales;
import com.web.chon.service.IfaceCatEntidad;
import com.web.chon.service.IfaceCatMunicipio;
import com.web.chon.service.IfaceDatosFacturacion;
import com.web.chon.util.Constantes;
import com.web.chon.util.FileUtils;
import com.web.chon.util.JsfUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Bean para el catlogo de Razon Social
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanRazonSocial implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired
    private IfaceCatEntidad ifaceCatEntidad;
    @Autowired
    private IfaceCatMunicipio ifaceCatMunicipio;
    @Autowired
    private IfaceDatosFacturacion ifaceDatosFacturacion;
    @Autowired
    private IfaceCatCodigosPostales ifaceCatCodigosPostales;

    private ArrayList<DatosFacturacion> model;
    private ArrayList<Entidad> lstEntidades;
    private ArrayList<Municipios> lstMunicipios;
    private ArrayList<CodigoPostal> lstCodigosPostales;
    private ArrayList<DatosFacturacion> selectedDatosFacturacion;
    private List<FileDominio> filesUser = new ArrayList<FileDominio>();

    private DatosFacturacion data;

    private String title = "";
    public String viewEstate = "";

    private String path = "";//path de prueba comentar cuando se suba al servidor
//    private String path = "/home/servidorapl/SIV/Facturas/Empresa/rfc";

    private File[] files = null;
    private String carpeta = "";
    private String destPath;

    private UploadedFile keyFile;
    private UploadedFile cerFile;
    private UploadedFile keyPemFile;
    private UploadedFile cerPemFile;

//    private String codigoPostal;
//    private BigDecimal idEntidadFK;
//    private BigDecimal idMunicipioFK;
//    private BigDecimal idCodigoPostalFk;
    @PostConstruct
    public void init() {

        data = new DatosFacturacion();
        model = ifaceDatosFacturacion.getAll();
        selectedDatosFacturacion = new ArrayList<DatosFacturacion>();

        lstEntidades = ifaceCatEntidad.getEntidades();
        lstCodigosPostales = ifaceCatCodigosPostales.getCodigoPostalById("1");

        setTitle("Catalogo de Razón Social");
        setViewEstate("init");

    }

    @Override
    public String delete() {
        if (!selectedDatosFacturacion.isEmpty()) {
            for (DatosFacturacion datosFacturacion : selectedDatosFacturacion) {
                try {
                    if (ifaceDatosFacturacion.deleteDatosFacturacion(datosFacturacion.getIdDatosFacturacionPk()) > 0) {
                        JsfUtil.addSuccessMessage("Registro eliminado.");
                    } else {
                        JsfUtil.addErrorMessage("Ocurrio un error al eliminar el registro.");
                    }

                } catch (Exception ex) {
                    JsfUtil.addErrorMessage("Ocurrio un error al eliminar el registro.");
                }
            }
        } else {
            JsfUtil.addWarnMessage("Elija un registro a eliminar.");
        }
        return "catRazonSocial";
    }

    @Override
    public String insert() {
        try {

            if (data.getRfc() != null && !data.getRfc().equals("")) {

                data.setRuta_llave_privada(handleFileUpload(keyFile, data.getRfc()));
                data.setRuta_certificado(handleFileUpload(cerFile, data.getRfc()));
                data.setRuta_llave_privada_cancel(handleFileUpload(keyPemFile, data.getRfc()));
                data.setRuta_certificado_cancel(handleFileUpload(cerPemFile, data.getRfc()));

                if (ifaceDatosFacturacion.insertarDatosFacturacion(data) > 0) {
                    JsfUtil.addSuccessMessage("Registro Insertado.");
                    backView();
                    init();
                } else {
                    JsfUtil.addErrorMessage("Ocurrio un Error al Insertar el Registro.");
                }

            }

        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Ocurrio un Error al Insertar el Registro. " + ex.getMessage());

        }

        return "catRazonSocial";
    }

    @Override
    public String update() {

        try {
            if (data.getRfc() != null && !data.getRfc().equals("")) {

                data.setRuta_llave_privada(handleFileUpload(keyFile, data.getRfc()));
                data.setRuta_certificado(handleFileUpload(cerFile, data.getRfc()));
                data.setRuta_llave_privada_cancel(handleFileUpload(keyPemFile, data.getRfc()));
                data.setRuta_certificado_cancel(handleFileUpload(cerPemFile, data.getRfc()));
                if (ifaceDatosFacturacion.updateDatosFacturacion(data) > 0) {
                    JsfUtil.addSuccessMessage("Registro modificado.");
                    backView();
                } else {
                    JsfUtil.addErrorMessage("Ocurrio un error al intentar modificar el registro.");
                }
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Ocurrio un error al intentar modificar el registro." + ex.getMessage());
        }

        return "catRazonSocial";
    }

    /**
     * ** Archivo ****
     */
    private boolean existFileOnFolder(String fileName) {
        boolean exist = false;
        getFiles();
        for (FileDominio o : filesUser) {
            if (fileName.equals(o.getFileName())) {
                exist = true;
            }
        }
        return exist;
    }

    public void preparaArchivos(FileUploadEvent event) {
        int item = Integer.parseInt(event.getComponent().getAttributes().get("item").toString());
        UploadedFile file = event.getFile();
        switch (item) {
            case 1:
                keyFile = file;
                break;
            case 2:
                cerFile = file;
                break;
            case 3:
                keyPemFile = file;
                break;
            case 4:
                cerPemFile = file;
                break;
        }

        RequestContext.getCurrentInstance().execute("PF('statusDialog').hide();");

    }

    public String handleFileUpload(UploadedFile file, String rfc) {
        String fileName = null;
        if (file != null) {
            fileName = file.getFileName().trim();

            path = Constantes.PATHSERVERFACTURACION + File.separatorChar + rfc;

            if (existFileOnFolder(fileName)) {
                JsfUtil.addErrorMessage("Archivo existente, seleccione otro.");
            } else {
                UploadedFile uploadedFile = file;
                InputStream inputStr = null;
                try {
                    inputStr = uploadedFile.getInputstream();
                } catch (IOException e) {
                    JsfUtil.addErrorMessage("No se permite guardar valores nulos. ");
                    manageException(e);
                }
                try {

                    FileUtils.creaCarpeta(path);

                } catch (Exception e) {

                }

                destPath = path + File.separatorChar + fileName;

                try {

                    FileUtils.guardaArchivo(destPath, inputStr);

                    FacesMessage message = new FacesMessage("exito", "El archivo "
                            + file.getFileName().trim()
                            + " fue cargado.");
                    FacesContext.getCurrentInstance().addMessage(null, message);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return fileName;
    }

    public void getFiles() {
        File folder = new File(path + carpeta.trim());
        if (folder.exists()) {
            files = new File(path + carpeta.trim()).listFiles();
            if (files.length > 0) {
                filesUser.clear();
                for (File file : files) {
                    FileDominio fileDto = new FileDominio();
                    if (file.isFile()) {
                        fileDto.setFileName(file.getName());
                        filesUser.add(fileDto);
                    }
                }
            }
        } else {
            try {
                FileUtils.creaCarpeta(path + carpeta.trim() + "/");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean existFile(String filePath) throws Exception {
        File file = new File(filePath);
        return file.exists();
    }

    public void buscaMunicipios() {
        data.setIdMunicipio(null);
        data.setCodigoPostal("");
        data.setIdCodigoPostalFk(null);
        int idEntidad = data.getIdEntidad() == null ? 0 : data.getIdEntidad().intValue();

        lstMunicipios = ifaceCatMunicipio.getMunicipios(idEntidad);

        buscaColonias();
    }

    public void buscaColonias() {

        lstCodigosPostales = ifaceCatCodigosPostales.getCodigoPostalById(data.getCodigoPostal());

        if (lstCodigosPostales.isEmpty()) {

            int idEntidad = data.getIdEntidad() == null ? 0 : data.getIdEntidad().intValue();

            lstEntidades = ifaceCatEntidad.getEntidades();
            lstMunicipios = ifaceCatMunicipio.getMunicipios(idEntidad);

            data.setIdEntidad(new BigDecimal(0));
            data.setIdMunicipio(new BigDecimal(0));
            data.setCodigoPostal("");

            lstCodigosPostales = ifaceCatCodigosPostales.getCodigoPostalById("");

        } else {
            data.setIdEntidad(new BigDecimal(lstCodigosPostales.get(0).getIdEntidad()));
            int idEntidad = data.getIdEntidad() == null ? 0 : data.getIdEntidad().intValue();
            data.setIdMunicipio(new BigDecimal(lstCodigosPostales.get(0).getIdMunicipio()));

            if (data.getCodigoPostal().equals("")) {
                data.setCodigoPostal(lstCodigosPostales.get(0).getCodigoPostal());
                data.setIdCodigoPostalFk(new BigDecimal(lstCodigosPostales.get(0).getId_cp()));
            }

            lstMunicipios = ifaceCatMunicipio.getMunicipios(idEntidad);
        }

    }

    public void ActualizaCodigoPostal() {
        for (int i = 0; i < lstCodigosPostales.size(); i++) {
            if (lstCodigosPostales.get(i).getId_cp() == data.getIdCodigoPostalFk().intValue()) {
                data.setCodigoPostal(lstCodigosPostales.get(i).getNumeropostal());
            }
        }

    }

    public void buscaColoniasMun() {
        int idMunicipio = data.getIdMunicipio() == null ? 0 : data.getIdMunicipio().intValue();

        lstCodigosPostales = ifaceCatCodigosPostales.getCodigoPostalByIdMun(idMunicipio);
        data.setCodigoPostal(lstCodigosPostales.get(0).getNumeropostal());
    }

    public void deleteFile(String filePath) throws Exception {
        File file = new File(filePath);
        file.delete();
    }

    @Override
    public void searchById() {
        setTitle("Editar Razón Social");
        setViewEstate("searchById");
        buscaColonias();

    }

    public void viewNew() {
        data.reset();
        setTitle("Alta de Razón Social");
        setViewEstate("new");
    }

    public void backView() {
        data.reset();
        keyFile = null;
        cerFile = null;
        keyPemFile = null;
        cerPemFile = null;
        setTitle("Catalogo de Razón Social");
        setViewEstate("init");

        model.clear();
        model = ifaceDatosFacturacion.getAll();
    }

    public ArrayList<DatosFacturacion> getModel() {
        return model;
    }

    public void setModel(ArrayList<DatosFacturacion> model) {
        this.model = model;
    }

    public DatosFacturacion getData() {
        return data;
    }

    public void setData(DatosFacturacion data) {
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

    public ArrayList<DatosFacturacion> getSelectedDatosFacturacion() {
        return selectedDatosFacturacion;
    }

    public void setSelectedDatosFacturacion(ArrayList<DatosFacturacion> selectedDatosFacturacion) {
        this.selectedDatosFacturacion = selectedDatosFacturacion;
    }

    public ArrayList<Municipios> getLstMunicipios() {
        return lstMunicipios;
    }

    public void setLstMunicipios(ArrayList<Municipios> lstMunicipios) {
        this.lstMunicipios = lstMunicipios;
    }

    public ArrayList<CodigoPostal> getLstCodigosPostales() {
        return lstCodigosPostales;
    }

    public void setLstCodigosPostales(ArrayList<CodigoPostal> lstCodigosPostales) {
        this.lstCodigosPostales = lstCodigosPostales;
    }

    public UploadedFile getKeyFile() {
        return keyFile;
    }

    public void setKeyFile(UploadedFile keyFile) {
        System.out.println("" + keyFile.getFileName());
        this.keyFile = keyFile;
    }

    public UploadedFile getCerFile() {
        return cerFile;
    }

    public void setCerFile(UploadedFile cerFile) {
        this.cerFile = cerFile;
    }

    public UploadedFile getKeyPemFile() {
        return keyPemFile;
    }

    public void setKeyPemFile(UploadedFile keyPemFile) {
        this.keyPemFile = keyPemFile;
    }

//    public String getCodigoPostal() {
//        return codigoPostal;
//    }
//
//    public void setCodigoPostal(String codigoPostal) {
//        this.codigoPostal = codigoPostal;
//    }
//
//    public BigDecimal getIdEntidadFK() {
//        return idEntidadFK;
//    }
//
//    public void setIdEntidadFK(BigDecimal idEntidadFK) {
//        this.idEntidadFK = idEntidadFK;
//    }
//
//    public BigDecimal getIdMunicipioFK() {
//        return idMunicipioFK;
//    }
//
//    public void setIdMunicipioFK(BigDecimal idMunicipioFK) {
//        this.idMunicipioFK = idMunicipioFK;
//    }
//
//    public BigDecimal getIdCodigoPostalFk() {
//        return idCodigoPostalFk;
//    }
//
//    public void setIdCodigoPostalFk(BigDecimal idCodigoPostalFk) {
//        this.idCodigoPostalFk = idCodigoPostalFk;
//    }
    public ArrayList<Entidad> getLstEntidades() {
        return lstEntidades;
    }

    public void setLstEntidades(ArrayList<Entidad> lstEntidades) {
        this.lstEntidades = lstEntidades;
    }

    public UploadedFile getCerPemFile() {
        return cerPemFile;
    }

    public void setCerPemFile(UploadedFile cerPemFile) {
        this.cerPemFile = cerPemFile;
    }

    private void manageException(IOException e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
