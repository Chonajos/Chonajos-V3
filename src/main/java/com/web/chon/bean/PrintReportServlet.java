/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.chon.bean;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrintReportServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public PrintReportServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGetAndDoPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGetAndDoPost(request, response);
    }

    private void doGetAndDoPost(HttpServletRequest request, HttpServletResponse response) {

        File file = null;

        try {
            file = new File("C:/Windows/temp/archivo.pdf");

            if (!file.exists()) {
                return;
            }

            ServletContext context = getServletConfig().getServletContext();
            String mimetype = context.getMimeType("C:/Windows/temp/archivo.pdf");
            response.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
            response.setContentLength((int) file.length());
//response.setHeader( "Content-Disposition", "attachment; filename=\"" + output + "\"" );

            OutputStream op = response.getOutputStream();

            int length = 0;
            byte[] bbuf = new byte[1024];
            DataInputStream in = new DataInputStream(new FileInputStream(file));

            while ((in != null) && ((length = in.read(bbuf)) != -1)) {
                op.write(bbuf, 0, length);
            }

            in.close();
            op.flush();
            op.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (file != null && file.exists()) {
                file.delete();
            }
            file = null;
        }
    }

}
