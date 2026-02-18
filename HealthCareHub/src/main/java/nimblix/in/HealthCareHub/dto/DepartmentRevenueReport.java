package nimblix.in.HealthCareHub.dto;

public class DepartmentRevenueReport {
    private String departmentName;
    private Double totalRevenue;

    public DepartmentRevenueReport(String departmentName, Double totalRevenue) {
        this.departmentName = departmentName;
        this.totalRevenue = totalRevenue;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}