/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.senpai.cultivoWar.beans;

import cl.senpai.cultivoEjb.dao.PlantasDAOLocal;
import cl.senpai.cultivoEjb.dto.Planta;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Senpai
 */
@Named(value = "plantasManagedBean")
@ViewScoped
public class PlantasManagedBean implements Serializable{

    
    @Inject
    private PlantasDAOLocal plantasDAO;
    private LineChartModel modeloGrafico;
    private LineChartSeries medidasSeries;
    private List<Planta> registros;
    private Date fechaDeMedida;
    private double valorHumedad;
    
    public PlantasManagedBean() {
    }
    
    @PostConstruct
    public void init(){
        this.cargarGrafico();
        this.fechaDeMedida = new Date();
    }

    public Date getFechaDeMedida() {
        return fechaDeMedida;
    }

    public void setFechaDeMedida(Date fechaDeMedida) {
        this.fechaDeMedida = fechaDeMedida;
    }

    public double getValorHumedad() {
        return valorHumedad;
    }

    public void setValorHumedad(double valorHumedad) {
        this.valorHumedad = valorHumedad;
    }
    

    public LineChartModel getModeloGrafico() {
        return modeloGrafico;
    }

    public void setModeloGrafico(LineChartModel modeloGrafico) {
        this.modeloGrafico = modeloGrafico;
    }

    public List<Planta> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Planta> registros) {
        this.registros = registros;
    }
    
    public void cargarGrafico(){
        
        this.registros = this.plantasDAO.findAll();
        //Construir modelo grafico
        this.modeloGrafico = new LineChartModel();
        this.modeloGrafico.setTitle("Mediciones de plantas historicas");
        this.modeloGrafico.setZoom(true);
        this.modeloGrafico.getAxis(AxisType.Y).setLabel("Humedad");
        DateAxis fechaAxis = new DateAxis("Fecha");
        fechaAxis.setTickFormat("%d-%m-%Y-%H:%M:%S");
        this.modeloGrafico.getAxes().put(AxisType.X, fechaAxis);
        fechaAxis.setTickAngle(-50);
        
        //Construir series
        this.medidasSeries = new LineChartSeries("Medidas");
        
        //java 8 - java antes 8
        //DateTimeFormater - SimpleTimeFormat
        //LocalDate - Date
        //LocalDateTime - Date
        //ZonedDateTime - Calendar
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             
        registros.forEach(p ->{
            this.medidasSeries.set(sdf.format(p.getFecha().getTime()), p.getHumedad());
        });
        
        this.modeloGrafico.addSeries(medidasSeries);
    }
    
    public void remove(Planta p){
        
        //Eliminar Planta
        this.plantasDAO.remove(p);
        //Recargar Lista
        this.registros = this.plantasDAO.findAll();
        //Recargar DAO
        this.cargarGrafico();
        
    }
    
    public void registrarMedida(ActionEvent e){
        
        Planta p = new Planta();
        
        //Obtengo una instancia de calentar
        Calendar fecha = Calendar.getInstance();
        //Defino el tiempo del calendar con el date
        fecha.setTime(fechaDeMedida);
        //Le paso el calendar a planta
        p.setFecha(fecha);
        p.setHumedad(valorHumedad);
        this.plantasDAO.add(p);
        this.recargarRegistro();
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Medida Registrada"));
        
    }
    
    private void recargarRegistro(){
        this.registros = this.plantasDAO.findAll();
        this.cargarGrafico();
    }
}
