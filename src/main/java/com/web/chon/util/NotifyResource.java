package com.web.chon.util;
 
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;
 
@PushEndpoint("/notify")
public class NotifyResource {
    
    @PostConstruct 
    public void init(){
        System.out.println("init posty");
    }
         
    @OnMessage(encoders = {JSONEncoder.class})
    public FacesMessage onMessage(FacesMessage message) {
        System.out.println("message");
        return message;
    }
 
}