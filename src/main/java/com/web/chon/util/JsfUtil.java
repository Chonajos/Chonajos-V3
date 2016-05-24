package com.web.chon.util;

import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.primefaces.context.RequestContext;

/**
 * Helper con utilerias para JSF.
 *
 */
public class JsfUtil {

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {

        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Alerta!", msg);
        FacesContext.getCurrentInstance().addMessage("msgs", facesMsg);
        RequestContext.getCurrentInstance().addCallbackParam("errorDetected", true);
    }
    public static void addSuccessMessageClean(String msg) {
        removeContextMessage();
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Éxito!", msg);
        FacesContext.getCurrentInstance().addMessage("msgs", facesMsg);
    }
    public static void addWarnMessageClean(String msg) {
        removeContextMessage();
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "¡Alerta!", msg);
        FacesContext.getCurrentInstance().addMessage("msgs", facesMsg);
    }

    public static void addErrorMessageClean(String msg) {

        removeContextMessage();
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Alerta!", msg);
        FacesContext.getCurrentInstance().addMessage("msgs", facesMsg);
        RequestContext.getCurrentInstance().addCallbackParam("errorDetected", true);
    }

    public static void addErrorMessageById(String msg, String clientId) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Alerta!", msg);
        FacesContext.getCurrentInstance().addMessage(clientId, facesMsg);
        RequestContext.getCurrentInstance().addCallbackParam("errorDetected", true);
    }

    public static void addErrorMessage(String summary, String detail) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage("msgs", facesMsg);
        RequestContext.getCurrentInstance().addCallbackParam("errorDetected", true);
    }

    public static void addWarnMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "¡Alerta!", msg);
        FacesContext.getCurrentInstance().addMessage("msgs", facesMsg);
        RequestContext.getCurrentInstance().addCallbackParam("msgs", true);
    }

    public static void addWarnMessageById(String msg, String msgId) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "¡Alerta!", msg);
        FacesContext.getCurrentInstance().addMessage(msgId, facesMsg);
        RequestContext.getCurrentInstance().addCallbackParam(msgId, true);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Éxito!", msg);
        FacesContext.getCurrentInstance().addMessage("msgs", facesMsg);
    }

    public static void addSuccessMessageById(String msg, String msgId) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Éxito!", msg);
        FacesContext.getCurrentInstance().addMessage(msgId, facesMsg);
        RequestContext.getCurrentInstance().addCallbackParam(msgId, true);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(),
                component, theId);
    }

    public static Boolean existeMensajeErrorEnFacesContext() {
        Boolean result = false;

        Iterator<FacesMessage> iterator = FacesContext.getCurrentInstance().getMessages();

        while (iterator.hasNext()) {
            FacesMessage msg = iterator.next();
            if (FacesMessage.SEVERITY_ERROR.compareTo(msg.getSeverity()) == 0) {
                result = true;
                break;
            }
        }

        return result;
    }

    public static void removeContextMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        Iterator<FacesMessage> it = context.getMessages();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

}
