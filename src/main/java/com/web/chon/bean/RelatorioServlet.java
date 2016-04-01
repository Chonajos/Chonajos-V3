/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import com.web.chon.util.JasperReportUtil;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

/**
 *
 * @author Paulo Vinícius Moreira Dutra
 * @version 1.0
 *
 */
@WebServlet(name = "RelatorioServlet", urlPatterns = {"/RelatorioServlet"})
public class RelatorioServlet extends HttpServlet {

    private ByteArrayOutputStream outputStream;

    public static final String IMAGES_DIR = "/br/requisitebuilder/relatorios/";
    public static final String IMAGE_LOGO = "logo.png";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {/*
        response.setHeader("application/pdf", "Content-Type");  
        response.setContentType("application/pdf");
        //Para download
        //   response.setContentType("application/vnd.ms-excel");  
        //response.setHeader("Content-Disposition","attachment;filename=relatorioExcel.xls"); 
        InputStream caminho = null;
        ServletOutputStream ouputStream = null;               
        try{                         
             List listagem = new ArrayList();
             HttpSession session = request.getSession(true);                          
             Object attribute = session.getAttribute("listagem");
             String relatorio = session.getAttribute("relatorio").toString();           
             System.out.println(attribute);
             System.out.println(relatorio);
             
             String logoPath = IMAGES_DIR + IMAGE_LOGO;

             System.out.println(caminho);
             HashMap parametros = new HashMap();
             parametros = (HashMap)attribute;
       
             JasperPrint preencher = JasperFillManager.fillReport("C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper", parametros, new JREmptyDataSource());
             byte[] bytes = JasperExportManager.exportReportToPdf(preencher);
             
             response.setContentLength(bytes.length);                   
             ouputStream = response.getOutputStream();  
             ouputStream.write(bytes, 0, bytes.length);               
             ouputStream.flush();
         }catch(Exception e){
             e.printStackTrace();         
         }finally {
            if (caminho != null)
                caminho.close();
            if (ouputStream != null)
                ouputStream.close();
         }*/


        JasperReport compiledTemplate = null;
        JRExporter exporter = null;
        ByteArrayOutputStream out = null;
        ByteArrayInputStream input = null;
        BufferedOutputStream output = null;

        List listagem = new ArrayList();
        HttpSession session = request.getSession(true);
        Object attribute = session.getAttribute("listagem");
        
        System.out.println(attribute);
       
        HashMap parametros = new HashMap();
        parametros = (HashMap) attribute;
      
        

        try {

            JasperPrint jp = JasperFillManager.fillReport("C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper", parametros, new JREmptyDataSource());
            outputStream = JasperReportUtil.getOutputStreamFromReport(parametros, "C:/Users/Juan/Documents/NetBeansProjects/Chonajos-V2/ticket.jasper");
            exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);

            exporter.exportReport();
            input = new ByteArrayInputStream(outputStream.toByteArray());

            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(outputStream.toByteArray().length));
            response.setHeader("Content-Disposition", "inline; filename=\"fileName.pdf\"");
            output = new BufferedOutputStream(response.getOutputStream(), 80);

            byte[] buffer = new byte[80];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();

        } catch (Exception exception) {
            System.out.println("Error > 1"  + exception.getMessage());
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (Exception exception) {
                System.out.println("Error > 2" + exception.getMessage());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
