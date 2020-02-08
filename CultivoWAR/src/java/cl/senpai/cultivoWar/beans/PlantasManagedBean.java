/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.senpai.cultivoWar.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
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

    private LineChartModel modeloGrafico;
    private LineChartSeries medidasSeries;
    
    public PlantasManagedBean() {
    }
    
    @PostConstruct
    public void init(){
        this.cargarGrafico();
    }

    public LineChartModel getModeloGrafico() {
        return modeloGrafico;
    }

    public void setModeloGrafico(LineChartModel modeloGrafico) {
        this.modeloGrafico = modeloGrafico;
    }
    
    public void cargarGrafico(){
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
        this.medidasSeries.set("2020-01-01 03:30:15", 30.5);
        this.medidasSeries.set("2019-01-01 03:30:15", 40.5);
        this.medidasSeries.set("2018-01-01 03:30:15", 35.5);
        this.medidasSeries.set("2017-01-01 03:30:15", 60.5);
        this.modeloGrafico.addSeries(medidasSeries);
    }
}
