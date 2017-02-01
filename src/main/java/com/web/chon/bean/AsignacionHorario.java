package com.web.chon.bean;

import com.web.chon.dominio.DiaDescansoUsuario;
import com.web.chon.dominio.HorarioUsuario;
import com.web.chon.dominio.Sucursal;
import com.web.chon.dominio.Usuario;
import com.web.chon.security.service.PlataformaSecurityContext;
import com.web.chon.service.IfaceCatSucursales;
import com.web.chon.service.IfaceCatUsuario;
import com.web.chon.service.IfaceDiaDescansoUsuario;
import com.web.chon.service.IfaceHorarioUsuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Juan de la Cruz
 */
@Component
@Scope("view")
public class AsignacionHorario implements BeanSimple {

    private static final long serialVersionUID = 1L;
  
    @Autowired
    private IfaceCatUsuario ifaceCatUsuario;
    @Autowired
    private IfaceCatSucursales ifaceCatSucursales;
    @Autowired
    private PlataformaSecurityContext context;
    @Autowired
    private IfaceHorarioUsuario ifaceHorariosUsuario;
    @Autowired
    private IfaceDiaDescansoUsuario ifaceDiaDescansoUsuario;

    private ArrayList<Usuario> lstUsuario;
    private List<Sucursal> lstSucursal;

    private HorarioUsuario horarioUsuario;
    private DiaDescansoUsuario diaDescanso;
    
    private BigDecimal idUsuario;
    private BigDecimal idSucursal;
    
    private String title;
    private String viewEstate;

    @PostConstruct
    public void init() {

        lstSucursal = new ArrayList<Sucursal>();
        horarioUsuario = new HorarioUsuario();
        diaDescanso = new DiaDescansoUsuario();
        
        Date horaCero = new Date();
        
        horaCero.setHours(05);
        horaCero.setMinutes(0);
        
        horarioUsuario.setHoraEntrada(horaCero);
        
        horaCero.setHours(16);
        horaCero.setMinutes(0);
        
        horarioUsuario.setHoraSalida(horaCero);

        lstUsuario = ifaceCatUsuario.getUsuariosbyIdSucursal(context.getUsuarioAutenticado().getSucId());
        
        lstSucursal = ifaceCatSucursales.getSucursales();
        setTitle("Asignación de Horarios");
        setViewEstate("init");

    }

    @Override
    public String delete() {
       
        return "";
    }

    @Override
    public String insert() {
       
        diaDescanso.setIdUsuario(idUsuario);
        diaDescanso.setFechaInicio(horarioUsuario.getFechaInicio());
        diaDescanso.setFechaFin(horarioUsuario.getFechaFin());
        
        horarioUsuario.setIdUsuario(idUsuario);

        if(ifaceDiaDescansoUsuario.insert(diaDescanso) == 1){
            System.out.println("se inserto correctamente");
            if(ifaceHorariosUsuario.insert(horarioUsuario) == 1){
                System.out.println("se insert dia de descanso");
            }else{
                System.out.println("no se inserto dia de descanso");
            }
        }else{
            System.out.println("no se pudo insertar nada");
        }
        
        
             
        return "";
    }

    @Override
    public String update() {
       

        return "";
    }

    @Override
    public void searchById() {
       

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

    public List<Sucursal> getLstSucursal() {
        return lstSucursal;
    }

    public void setLstSucursal(List<Sucursal> lstSucursal) {
        this.lstSucursal = lstSucursal;
    }

    public HorarioUsuario getHorarioUsuario() {
        return horarioUsuario;
    }

    public void setHorarioUsuario(HorarioUsuario horarioUsuario) {
        this.horarioUsuario = horarioUsuario;
    }

    public DiaDescansoUsuario getDiaDescanso() {
        return diaDescanso;
    }

    public void setDiaDescanso(DiaDescansoUsuario diaDescanso) {
        this.diaDescanso = diaDescanso;
    }

    public ArrayList<Usuario> getLstUsuario() {
        return lstUsuario;
    }

    public void setLstUsuario(ArrayList<Usuario> lstUsuario) {
        this.lstUsuario = lstUsuario;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(BigDecimal idSucursal) {
        this.idSucursal = idSucursal;
    }
    
    
    
    
}
