package entity;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

@Named("dashboardController")
@ManagedBean
@RequestScoped
public class DashboardController implements Serializable {
    @EJB
    private entity.ProjectFacade projectFacade;
    private PieChartModel projectStatusModel;
    private HorizontalBarChartModel runningProjectBarModel; 
    private HorizontalBarChartModel incomeBarModel;
    
    @PostConstruct
    public void init() {
        createProjectStatusModel();
        createRunningProjectBarModel();
        createIncomeBarModel();
    }
 
    public PieChartModel getProjectStatusModel() {
        return projectStatusModel;
    }

    public HorizontalBarChartModel getRunningProjectBarModel() {
        return runningProjectBarModel;
    }
    
    public HorizontalBarChartModel getIncomeBarModel(){
        return incomeBarModel;
    }
     
    private void createProjectStatusModel() {
        projectStatusModel = new PieChartModel();
        
        projectStatusModel.set("Lezárt", projectFacade.findProjectCountByStatus(true));
        projectStatusModel.set("Folyamatban", projectFacade.findProjectCountByStatus(false));
        projectStatusModel.setShowDataLabels(true);
        projectStatusModel.setTitle("Projektek állapota");
        projectStatusModel.setLegendPosition("w");
    }

    private void createRunningProjectBarModel() {
        runningProjectBarModel = new HorizontalBarChartModel();
 
        List<Project> projectList = projectFacade.findOpenProjects();
        ChartSeries done = new ChartSeries();
        done.setLabel("Kész");
        ChartSeries open = new ChartSeries();
        open.setLabel("Folyamatban");
        int max = 1;
        for (Project project: projectList) {
            int doneCounter = 0;
            int openCounter = 0;
            for(ProjectPhase phase: project.getProjectPhaseCollection()) {
                if (Boolean.TRUE.equals(phase.getCompleted())) {
                    doneCounter++;
                } else {
                    openCounter++;
                }
            }
            
            done.set(project.getName(), doneCounter);
            open.set(project.getName(), openCounter);
            if ((doneCounter + openCounter) > max) {
                max = doneCounter + openCounter;
            }
        }
        runningProjectBarModel.addSeries(done);
        runningProjectBarModel.addSeries(open);
         
        runningProjectBarModel.setTitle("Futó projektek állapota");
        runningProjectBarModel.setLegendPosition("e");
        runningProjectBarModel.setStacked(true);
         
        Axis xAxis = runningProjectBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Kész / Hátravan");
        xAxis.setMin(0);
        xAxis.setMax(max);
         
        Axis yAxis = runningProjectBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Projekt");        
    }

    private void createIncomeBarModel() {
        incomeBarModel = new HorizontalBarChartModel();
        List<Project> projectList = projectFacade.findAll();
        
        for (Project project : projectList) {
            
        }
        
        ChartSeries q1 = new ChartSeries();
        q1.setLabel("Első negyedév");
        
        
        ChartSeries q2 = new ChartSeries();
        q2.setLabel("Második negyedév");
        q2.set("2015",1.5);
        q2.set("2016",1);
        q2.set("2017",2);
        
        ChartSeries q3 = new ChartSeries();
        q3.setLabel("Harmadik negyedév");
        q3.set("2015",1.2);
        q3.set("2016",1);
        q3.set("2017",2);
        
        ChartSeries q4 = new ChartSeries();
        q4.setLabel("Negyedik negyedév");
        q4.set("2015",1.5);
        q4.set("2016",1);
        q4.set("2017",2);
        
        int max=10;
        
        incomeBarModel.addSeries(q1);
        incomeBarModel.addSeries(q2);
        incomeBarModel.addSeries(q3);
        incomeBarModel.addSeries(q4);
        
        incomeBarModel.setTitle("Évi bevételek negyedévenként");
        incomeBarModel.setLegendPosition("e");
        incomeBarModel.setStacked(true);
         
        Axis xAxis = incomeBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Kész / Hátravan");
        xAxis.setMin(0);
        xAxis.setMax(max);
         
        Axis yAxis = incomeBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Projekt");    
    }

    
     
}
