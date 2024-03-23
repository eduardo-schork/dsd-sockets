package models;

import java.util.HashMap;

public class Wallet {

    protected String name;
    protected Person responsible;
    protected Double initialSalary;
    protected Double finalSalary;

    protected HashMap<String, Person> customers = new HashMap<String, Person>();

    public Wallet(String name, Person responsible, Double initialSalary, Double finalSalary,
            HashMap<String, Person> customers) {
        this.name = name;
        this.responsible = responsible;
        this.initialSalary = initialSalary;
        this.finalSalary = finalSalary;
        this.customers = customers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getResponsible() {
        return responsible;
    }

    public void setResponsible(Person responsible) {
        this.responsible = responsible;
    }

    public Double getInitialSalary() {
        return initialSalary;
    }

    public void setInitialSalary(Double initialSalary) {
        this.initialSalary = initialSalary;
    }

    public Double getFinalSalary() {
        return finalSalary;
    }

    public void setFinalSalary(Double finalSalary) {
        this.finalSalary = finalSalary;
    }

    public HashMap<String, Person> getcustomers() {
        return customers;
    }

    public void setCustomers(HashMap<String, Person> customers) {
        this.customers = customers;
    }

    public void addCustomer(Person customer) {
        this.customers.put(customer.getCpf(), customer);
    }

    public void removeCustomer(Person customer) {
        this.customers.remove(customer.getCpf());
    }

    @Override
    public String toString() {
        return name + ";" + initialSalary + ";" + finalSalary + ";" + responsible.toString() + ";"
                + customers.size();
    }
}
