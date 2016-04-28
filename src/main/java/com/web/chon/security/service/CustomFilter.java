package com.web.chon.security.service;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.web.chon.core.exception.SecurityAccessException;
import com.web.chon.dominio.UsuarioDominio;

@Service(value = "customFilter")
public class CustomFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(CustomFilter.class);

    @Autowired
    private PlataformaSecurityContext context;

    private UsuarioDominio usuario;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        if (isAjaxRequest(req)) {
            chain.doFilter(req, res);
            return;
        }

        String url = req.getRequestURL().toString();
        String urlPrimePush = url.substring(url.length() - 16, url.length());

        int i = url.lastIndexOf("/views/");
        int j = url.lastIndexOf("7001");

        if ((url.substring(j)).equalsIgnoreCase("/login.xhtml")) {

            try {
                if (!context.getUsuarioAutenticado().getUsuId().equals("")) {
                    res.sendRedirect("/views/welcome.xhtml");
                    return;
                }
            } catch (Exception e) {
            }

        }

        if (j > 0 && (url.substring(j)).equalsIgnoreCase("") || (url.substring(j)).equalsIgnoreCase("/views/")) {
            url = url.substring(j);
            res.sendRedirect("/views/welcome.xhtml");
            return;

        }

        if (i > 0) {
            url = url.substring(i);
            try {
                hasPermission(url);
            } catch (SecurityAccessException e) {
                res.sendRedirect("/error/error.xhtml");
                return;
            }

        }

        if (!urlPrimePush.equals("primepush/notify")) {
            chain.doFilter(req, res);
        }
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return request.getParameter("ajax") != null;
    }

    private void hasPermission(String url) throws SecurityAccessException {
        usuario = context.getUsuarioAutenticado();
        
        if (url.equalsIgnoreCase("/views/welcome.xhtml")) {
            return;
        }
        if (usuario.getAllowedUrl().contains(url)) {
            return;

        }

        throw new SecurityAccessException(
                "No tiene permisos para ver esta Pagina.");
    }

}
