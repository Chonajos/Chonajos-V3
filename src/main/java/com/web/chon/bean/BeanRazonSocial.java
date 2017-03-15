package com.web.chon.bean;

import com.web.chon.dominio.DatosFacturacion;
import com.web.chon.dominio.FileDominio;
import com.web.chon.service.IfaceDatosFacturacion;
import com.web.chon.util.Constantes;
import com.web.chon.util.FileUtils;
import com.web.chon.util.JsfUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.web.chon.service.IfaceFacturas;

/**
 * Bean para el catlogo de Razon Social
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class BeanRazonSocial implements Serializable, BeanSimple {

    private static final long serialVersionUID = 1L;

    @Autowired private IfaceDatosFacturacion ifaceDatosFacturacion;
    
    private ArrayList<DatosFacturacion> model;
    private ArrayList<DatosFacturacion> selectedDatosFacturacion;
    private List<FileDominio> filesUser = new ArrayList<FileDominio>();

    private DatosFacturacion data;
    
    private String title = "";
    public String viewEstate = "";

    private String path = "C:/Users/Juan/SIV";//path de prueba comentar cuando se suba al servidor
//    private String path = "/home/servidorapl/SIV";

    private File[] files = null;
    private String carpeta = "";
    private String destPath;

    private byte[] bytes;

    @PostConstruct
    public void init() {

        data = new DatosFacturacion();
        model = ifaceDatosFacturacion.getByIdSucursal(BigDecimal.ZERO);
        selectedDatosFacturacion = new ArrayList<DatosFacturacion>();

        setTitle("Catalogo de Razón Social");
        setViewEstate("init");

    }

    @Override
    public String delete() {
        if (!selectedDatosFacturacion.isEmpty()) {
            for (DatosFacturacion datosFacturacion : selectedDatosFacturacion) {
                try {
                    if (ifaceDatosFacturacion.deleteDatosFacturacion(datosFacturacion.getIdDatosFacturacionPk().toString()) > 0) {
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

            if(ifaceDatosFacturacion.insertarDatosFacturacion(data) > 0){
                JsfUtil.addSuccessMessage("Registro modificado.");
            }else{
                JsfUtil.addErrorMessage("Ocurrio un error al insertar el registro.");
            }

        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Ocurrio un error al insertar el registro. "+ex.getMessage());

        }

        return "catRazonSocial";
    }

    @Override
    public String update() {

        try {
            if (ifaceDatosFacturacion.updateDatosFacturacion(data) > 0) {
                JsfUtil.addSuccessMessage("Registro modificado.");
            } else {
                JsfUtil.addErrorMessage("Ocurrio un error al intentar modificar el registro.");
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Ocurrio un error al intentar modificar el registro."+ex.getMessage());
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

    public void handleFileUpload(FileUploadEvent event) {
        String fileName = event.getFile().getFileName().trim();

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

        String temporal = "";
        if (servletContext.getRealPath("") == null) {
            temporal = Constantes.PATHSERVER;
        } else {
            temporal = servletContext.getRealPath("");
        }
        path = temporal + File.separatorChar + "resources" + File.separatorChar + "img" + File.separatorChar + "RODUCTOS";

        if (existFileOnFolder(fileName)) {
            JsfUtil.addErrorMessage("Archivo existente, seleccione otro.");
        } else {
            UploadedFile uploadedFile = (UploadedFile) event.getFile();
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
                        + event.getFile().getFileName().trim()
                        + " fue cargado.");
                FacesContext.getCurrentInstance().addMessage(null, message);
//                fileSaved = 1;
//
//                String filePath = (data.getUrlImagenSubproducto() != null ? data
//                        .getUrlImagenSubproducto() : null);
//
//                if (filePath != null && data.getIdProductoFk() != null) {
//                    if (existFile(filePath)) {
//                        deleteFile(filePath);
//                    }
//
//                }

                //Se cambia la ruta a guardar en la bd 
                String strPath = "";
                strPath = ".." + File.separatorChar + "resources" + File.separatorChar + "img" + File.separatorChar + "RODUCTOS" + File.separatorChar + fileName;

//                data.setUrlImagenSubproducto(strPath);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

//    public void descarga(Subproducto dominio) throws Exception {
//        try {
//
//            if (dominio.getUrlImagenSubproducto() != null && !dominio.getUrlImagenSubproducto().isEmpty()) {
//                File file = new File(dominio.getUrlImagenSubproducto().trim());
//                InputStream input = new FileInputStream(file);
////                setDownload(new DefaultStreamedContent(input, file.getName(), file.getName()));
//            }
//
//        } catch (FileNotFoundException e) {
//            JsfUtil.addErrorMessage(e.toString());
//        }
//
//    }

    public void deleteFile(String filePath) throws Exception {
        File file = new File(filePath);
        file.delete();
    }

    @Override
    public void searchById() {
        setTitle("Editar Productos");
        setViewEstate("searchById");

    }

//    public void viewNew() {
//        data = new RazonSocial();
//        setTitle("Alta de Producto");
//        setViewEstate("new");
//    }

    public void backView() {
        setTitle("Catalogo de Producto");
        setViewEstate("init");
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

   
    private void manageException(IOException e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
