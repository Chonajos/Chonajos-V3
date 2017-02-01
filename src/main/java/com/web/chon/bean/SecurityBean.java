package com.web.chon.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class SecurityBean implements PhaseListener, Serializable {

    private static final long serialVersionUID = -7388960716549948523L;

    private String userName;
    private String password;
    private int sumaCapcha;
    private String capchaUser;
    private String capcha;
    private final int NUEVE = 9;
    private final int UNO = 1;

    @Autowired
    UsuarioWeb usuarioWeb;

    @Override
    public void afterPhase(PhaseEvent event) {
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        System.out.println("beforePhase");

        Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales incorrectas!", "Verificar valores de entrada."));

        if (e instanceof BadCredentialsException) {
            System.out.println("mensajes");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                    .put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales incorrectas!", "Usuario o contraseña no son validos.."));
        }

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    public String doLogin() throws IOException, ServletException {

        if (validaCapcha()) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                    .getRequestDispatcher("/j_spring_security_check");
            dispatcher.forward((ServletRequest) context.getRequest(),
                    (ServletResponse) context.getResponse());
            FacesContext.getCurrentInstance().responseComplete();
        } else {
            updateCapcha();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Capcha Incorrecto!", "El Capcha no es Correcto"));
        }

        return null;
    }

    public String logout() throws IOException, ServletException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_logout");
        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    @PostConstruct
    public void init() {
        updateCapcha();
    }

    public void updateCapcha() {
        capcha = "";
        sumaCapcha = 0;
        capchaUser = "";

        Random random = new Random();

         
        int valUno = (int)(random.nextDouble() * NUEVE + UNO);
        int valDos = (int)(random.nextDouble() * NUEVE + UNO);
        int tipoOperacion;

        sumaCapcha = valUno + valDos;
        capcha = valUno + " + " + valDos + " = ";
    }

    public boolean validaCapcha() {

        if (capchaUser != null && !capchaUser.equals("")) {
            if (esNumero()) {
                if (Integer.parseInt(capchaUser) == sumaCapcha) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean esNumero() {
        try {
            Integer.parseInt(capchaUser);
        } catch (NumberFormatException ne) {
            return false;
        }

        return true;
    }

    public String cancel() {
        return null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSumaCapcha() {
        return sumaCapcha;
    }

    public void setSumaCapcha(int sumaCapcha) {
        this.sumaCapcha = sumaCapcha;
    }

    public String getCapcha() {
        return capcha;
    }

    public void setCapcha(String capcha) {
        this.capcha = capcha;
    }

    public String getCapchaUser() {
        return capchaUser;
    }

    public void setCapchaUser(String capchaUser) {
        this.capchaUser = capchaUser;
    }

}
