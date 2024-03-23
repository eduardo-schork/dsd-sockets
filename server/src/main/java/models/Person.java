package models;

public class Person {

    protected String cpf;
    protected String name;
    protected String address;

    public Person(String cpf, String name, String address) {
        this.cpf = cpf;
        this.name = name;
        this.address = address;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return cpf + ";" + name + ";" + address;
    }
}
