/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

/**
 *
 * @author marcogante
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.jboss.logging.Logger;
 
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class FileUploadView {
     
    private UploadedFile file;
    private String destination = "/home/marcogante/Documentos/Chonajos-V2/target/Chonajos-1.0-SNAPSHOT/resources/entrada";
    
 
    public UploadedFile getFile()
    {
        return file;
    }
 
    public void setFile(UploadedFile file) 
    {
        this.file = file;
    }
     
    public void upload() 
    {
        String extValidate;
        
        if(file != null) 
        {
            String ext = getFile().getFileName();
            if(ext!=null){
                extValidate = ext.substring(ext.indexOf(".")+1);}
               else
            {
                extValidate="null";
            }
            if(extValidate.equals("pdf"))
            {
                try{
                    transferFile(getFile().getFileName(),getFile().getInputstream());
                }catch(IOException ex){
                    Logger.getLogger(FileUploadView.class.getName()).log(Logger.Level.ERROR, ex);
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Error","Error Cargando imagen"));
                }
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Éxito","se ha subido correctamente el archivo: "+getFile().getFileName()));
                
            }else
            {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null,new FacesMessage("Error","Solo extension .pdf"));
            }
           // FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            //FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else
        {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error","Selecciona un archivo"));
        }
    }
    public void transferFile(String fileName, InputStream in){
        try{
            OutputStream out = new FileOutputStream(new File(destination+fileName));
            int reader = 0;
            byte[] bytes  = new byte[(int)getFile().getSize()];
            while((reader = in.read(bytes))!= -1)
            {
                out.write(bytes, 0, reader);
            }
            in.close();
            out.flush();
            out.close();
        
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}