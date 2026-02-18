package nimblix.in.HealthCareHub.dto;




public class DoctorPerformanceReport {
    private String doctorName;
    private Long totalAppointments;
    private Double totalRevenue;

    public DoctorPerformanceReport(String doctorName, Long totalAppointments, Double totalRevenue) {
        this.doctorName = doctorName;
        this.totalAppointments = totalAppointments;
        this.totalRevenue = totalRevenue;
    }

    // Getter and Setter for doctorName
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    // Getter and Setter for totalAppointments
    public Long getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(Long totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    // Getter and Setter for totalRevenue
    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}