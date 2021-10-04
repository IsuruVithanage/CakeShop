package view.tm;

import javafx.scene.control.Button;

public class SalaryTM {
    private String workerID;
    private int workPerMonth;
    private double salaryPerDay;
    private double salary;
    private Button pay;

    public SalaryTM() {
    }

    public SalaryTM(String workerID, int workPerMonth, double salaryPerDay, double salary, Button pay) {
        this.workerID = workerID;
        this.workPerMonth = workPerMonth;
        this.salaryPerDay = salaryPerDay;
        this.salary = salary;
        this.pay = pay;
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

    public Button getPay() {
        return pay;
    }

    public void setPay(Button pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "SalaryTM{" +
                "workerID='" + workerID + '\'' +
                ", workPerMonth=" + workPerMonth +
                ", salaryPerDay=" + salaryPerDay +
                ", salary=" + salary +
                ", pay=" + pay +
                '}';
    }
}
