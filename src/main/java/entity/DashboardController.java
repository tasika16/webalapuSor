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
    
    @PostConstruct
    public void init() {
        createProjectStatusModel();
        createRunningProjectBarModel();
    }
 
    public PieChartModel getProjectStatusModel() {
        return projectStatusModel;
    }

    public HorizontalBarChartModel getRunningProjectBarModel() {
        return runningProjectBarModel;
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

     
}
