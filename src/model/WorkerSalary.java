package model;

public class WorkerSalary {
    private String date;
    private String workerID;
    private int workPerMonth;
    private double salaryPerDay;
    private double salary;
    private String state;

    public WorkerSalary() {
    }

    public WorkerSalary(String date, String workerID, int workPerMonth, double salaryPerDay, double salary, String state) {
        this.date = date;
        this.workerID = workerID;
        this.workPerMonth = workPerMonth;
        this.salaryPerDay = salaryPerDay;
        this.salary = salary;
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWorkerID() {
        return workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }

    public int getWorkPerMonth() {
        return workPerMonth;
    }

    public void setWorkPerMonth(int workPerMonth) {
        this.workPerMonth = workPerMonth;
    }

    public double getSalaryPerDay() {
        return salaryPerDay;
    }

    public void setSalaryPerDay(double salaryPerDay) {
        this.salaryPerDay = salaryPerDay;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "WorkerSalary{" +
                "date='" + date + '\'' +
                ", workerID='" + workerID + '\'' +
                ", workPerMonth=" + workPerMonth +
                ", salaryPerDay=" + salaryPerDay +
                ", salary=" + salary +
                ", state='" + state + '\'' +
                '}';
    }

}
