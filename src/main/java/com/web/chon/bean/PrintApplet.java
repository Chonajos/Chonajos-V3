/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;

/**
 * Applet para la impresion de archivos en formato PDF en modo silencionso en la
 * PC del cliente Requiere de las librerias de PDFRenderer-0.9.1.jar que puede
 * encontrarse en http://java.net/projects/pdf-renderer/downloads
 *
 * Los archivos fueron generados con JasperReports y exportados a PDF. Imprimir
 * desde el HTML generaba algunos errores de formato o posición de los elementos
 * sobre todo al imprimir sobre formatos pre-impresos (facturas, recibos, etc.)
 * por esa razón se eligió exportarlos a PDF e imprimirlos desde ahí.
 *
 * Se requiere de un servlet que envié el archivo PDF al cliente, este lo recibe
 * como un array de Bytes y lo envía a Imprimir en la impresora definida.
 *
 * Basado en los siguientes ejemplos
 * http://www.javaworld.com/javaworld/jw-06-2008/jw-06-opensourcejava-pdf-renderer.html?page=1
 * http://publicajava.blogspot.com/
 *
 * Información de la impresion en Java
 * http://docs.oracle.com/javase/6/docs/technotes/guides/jps/spec/JPSTOC.fm.html
 *
 * @author corosco
 *
 */
public class PrintApplet extends javax.swing.JApplet implements Printable {

    private static final long serialVersionUID = 3325035839231751544L;

    PDFFile pdfFile;

    /**
     * Initializes the applet AppletPrint
     */
    public void init() {
        try {
//URL del servlet que debe llamar para recibir el archivo PDF
            this.urlService = getParameter("urlService");

            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jBtnImprimir = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

//Opcional, puede mostrarse una ventana con un botón para imprimir
        jBtnImprimir.setText("Imprimir");
        jBtnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnImprimirActionPerformed(evt);
            }
        });

//jPanel1.add(jBtnImprimir);
        add(jPanel1, java.awt.BorderLayout.CENTER);

    }

    private void jBtnImprimirActionPerformed(java.awt.event.ActionEvent evt) {
        comunicacionServer();//para recibir los datos del server
    }

// Variables declaration - do not modify
    private javax.swing.JButton jBtnImprimir;
    private javax.swing.JPanel jPanel1;
// End of variables declaration

//Guardara la url del servlet al que se comunicara para imprimir
    private String urlService;

    private void comunicacionServer() {

//long tamFile=0;

        /* Primero creamos la URL para la conexión. Tiene sentido construir la
dirección de esta forma tan "complicada" puesto que el applet solo puede
establecer conexiones con su servidor, y así, al construir la dirección
dinámicamente, no tenemos que retocar el código al irnos a otro servidor.
En todo caso, lo siguiente sería válido:
URL direccion = new URL ("http://www.javahispano.com/servlet/MiServlet");
         */
        URL pagina = this.getCodeBase();
        String protocolo = pagina.getProtocol();
        String servidor = pagina.getHost();
        int puerto = pagina.getPort();
        String servlet = "";

        if (this.urlService != null && !this.urlService.equals("")) {
            servlet = this.urlService;
        } else {
            servlet = "/example/printReport";
        }

        URL direccion = null;
        URLConnection conexion = null;
        try {
            direccion = new URL(protocolo, servidor, puerto, servlet);
            conexion = direccion.openConnection();
            System.out.println(" Url del Servlet: " + direccion.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        /*Lo siguiente es decirle al navegador que no use su
cache para esta conexión, porque si lo hace vamos a
tener un página estatica, y para eso no nos metemos
en estos líos ;-). */
        conexion.setUseCaches(false);

        /* Ahora añadimos todas las cabeceras de HTTP que necesitemos, Cookies, contenido,
autorizacion, etc. con el método:
conexion.setRequestProperty ("cabecera", "valor");
Consultar la especificación de HTTP para más detalles. Por ejemplo, para decir
que preferentemente hablamos español: */
        conexion.setRequestProperty("Accept-Language", "es");

        try {


            /* Procesamos la información de la forma adecuada, según se
trate de datos ASCII o binarios.
Obtenemos el stream de entrada para leer la informacion que nos envie el
server*/
            System.out.println("Obteniendo DataInputStream");
            DataInputStream dataIn = new DataInputStream(conexion.getInputStream());

// Recibo el numero de archivos que enviara el servidor
//int numArchivosRecibir = dataIn.readInt();
//Recibo un unico archivo
            int numArchivosRecibir = 1;
            System.out.println(" Numero de archivos para imprimir " + numArchivosRecibir);

// Recibe los archivos
/*String nameFiles[] = new String[numArchivosRecibir];
File f = null;
File files[] = new File[numArchivosRecibir];
FileOutputStream outputStream = null;*/
            ByteArrayOutputStream outputStream = null;

            System.out.println("lectura del archivo");
            for (int k = 0; k < numArchivosRecibir; k++) {

                outputStream = new ByteArrayOutputStream();
                byte buf[] = new byte[1024];
                int len;

                while ((dataIn != null) && ((len = dataIn.read(buf)) != -1)) {
                    outputStream.write(buf, 0, len);
                }

                /*
* Si tenemos varias impresoras configuradas se hace una busqueda
* de las impresoras
                 */
                DocFlavor psInFormat = DocFlavor.BYTE_ARRAY.AUTOSENSE;
                PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
                PrintService[] services = PrintServiceLookup.lookupPrintServices(psInFormat, aset);

// this step is necessary because I have several printers configured
                PrintService myPrinter = null;
                for (int i = 0; i < services.length; i++) {

                    String svcName = services[i].toString();
                    System.out.println("service found: " + svcName);
//Cambiar el nombre de la impresora
                    if (svcName.contains("HP Deskjet 2050 J510 series")) {
                        myPrinter = services[i];
                        System.out.println("my printer found: " + svcName);
                        break;
                    }
                }

                ByteBuffer buff = ByteBuffer.wrap(outputStream.toByteArray());

                pdfFile = new PDFFile(buff);

                if (myPrinter != null) {
                    PrinterJob job = PrinterJob.getPrinterJob();

//Se le asigna la impresora configurada
                    job.setPrintService(myPrinter);
                    try {

                        PageFormat pf = job.defaultPage();
                        Paper paper = new Paper();
                        paper.setSize(595, 842); //A4
                        paper.setImageableArea(72, 72, 523, 770);
                        pf.setPaper(paper);
                        job.setPrintable(this, pf);
                        job.print();

                    } catch (Exception pe) {
                        pe.printStackTrace();
                    }
                } else {
                    System.out.println("no printer services found");
                }

                outputStream.close();

            }

            /* Y finalmente cerramos la conexión. */
            dataIn.close();
            dataIn = null;
            outputStream = null;

        } catch (PrinterException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Método que será ejecutado desde JavaScript para lanzar la impresion
     *
     * @param urlConParametros ruta del servlet que envía el PDF
     */
    @SuppressWarnings("unchecked")
    public void ejecutaConJavascript(final String urlConParametros) {

        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                try {
                    comunicacionServer(urlConParametros);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return null;
            }
        });
    }

    public void comunicacionServer(String urlConParametros) {
        this.urlService = urlConParametros;
        this.comunicacionServer();
    }

    /**
     * Método para realizar la impresión Las clases de PDFRenderer se encarga de
     * dibujar el PDF en AWT y se lanza para impresión está ventana, se hace de
     * esta forma porque no todas las impresoras aceptan directamente un archivo
     * PDF para impresión.
     *
     * @param g
     * @param format
     * @param index
     */
    public int print(Graphics g, PageFormat format, int index) throws PrinterException {
        int pagenum = index + 1;
        if (pagenum < 1 || pagenum > pdfFile.getNumPages()) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = g2d.getTransform();

        PDFPage pdfPage = pdfFile.getPage(pagenum);

        Dimension dim;
        dim = pdfPage.getUnstretchedSize((int) format.getImageableWidth(),
                (int) format.getImageableHeight(),
                pdfPage.getBBox());

        Rectangle bounds = new Rectangle((int) format.getImageableX(),
                (int) format.getImageableY(),
                dim.width,
                dim.height);

        PDFRenderer rend = new PDFRenderer(pdfPage, (Graphics2D) g, bounds,
                null, null);
        try {
            pdfPage.waitForFinish();
            rend.run();
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }

        g2d.setTransform(at);

        return PAGE_EXISTS;
    }

}
