package models;

public class PhysicalPerson extends Person {
    protected String email;

    public PhysicalPerson(String cpf, String name, String address, String email) {
        super(cpf, name, address);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return super.toString() + ";" + email;
    }
}
