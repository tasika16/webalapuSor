package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import org.joda.time.DateTime;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

@Named("dashboardController")
@ManagedBean
@RequestScoped
public class DashboardController implements Serializable {
    @EJB
    private entity.ProjectFacade projectFacade;
    @EJB
    private entity.EmployeeFacade employeeFacade;
    private PieChartModel projectStatusModel;
    private BarChartModel yearIncomeBarModel;
    private List<Project> openProjects = new ArrayList<>();
    private List<Employee> freeEmployees = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        this.openProjects = projectFacade.findOpenProjects();
        this.freeEmployees = employeeFacade.findFreeEmployees();
        createProjectStatusModel();
        createRunningProjectBarModel();
    }

    public List<Employee> getFreeEmployees() {
        return freeEmployees;
    }

    public void setFreeEmployees(List<Employee> freeEmployees) {
        this.freeEmployees = freeEmployees;
    }
 
    public PieChartModel getProjectStatusModel() {
        return projectStatusModel;
    }

    public BarChartModel getYearIncomeBarModel() {
        return yearIncomeBarModel;
    }

    public List<Project> getOpenProjects() {
        return openProjects;
    }

    public void setOpenProjects(List<Project> openProjects) {
        this.openProjects = openProjects;
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
        yearIncomeBarModel = new BarChartModel();
 
        List<Project> projectList = projectFacade.findClosedProjects();
        Map<Integer,Integer> valMap = new HashMap();
        Map<Integer,Integer> valMapEst = new HashMap();
        for (Project project : projectList) {
            Integer year = new DateTime(project.getCreatedAt()).getYear();
            if (!valMap.containsKey(year)) {
                valMap.put(year, project.getFullPrice());
                valMapEst.put(year, project.getEstimatedPrice());
            } else {
                valMap.put(year, valMap.get(year) + project.getFullPrice());
                valMapEst.put(year, valMap.get(year) + project.getEstimatedPrice());
            }

        }        
        
        Map<Integer,ChartSeries> seriesMap = new HashMap();
        ChartSeries csE = new ChartSeries();
        csE.setLabel("Bevétel");
        ChartSeries csF = new ChartSeries();
        csF.setLabel("Becslés");
        int from = new DateTime().getYear();
        for (int i=from-10; i<=from;i++) {
            Integer val = valMap.get(i);
            Integer valE = valMapEst.get(i);
            csF.set(i, (val == null)?0:val);
            csE.set(i, (valE == null)?0:valE);
            seriesMap.put(i, csF);
        }
        yearIncomeBarModel.addSeries(csF);
        yearIncomeBarModel.addSeries(csE);
        
        yearIncomeBarModel.setTitle("Bevétel");
        yearIncomeBarModel.setLegendPosition("ne");
        yearIncomeBarModel.setStacked(false);
        
        int max = 0;
        for (Integer y: seriesMap.keySet()) {
            Integer val = valMap.get(y);
            Integer valE = valMapEst.get(y);
            if (val != null && val > max) {
                max = val;
            }
            if (valE != null && valE > max) {
                max = valE;
            }
        }
        Axis xAxis = yearIncomeBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Év");
         
        Axis yAxis = yearIncomeBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Összeg");        
        yAxis.setMax(max*1.20);
        yAxis.setMin(0);
    }

}
