package models;

public class PhysicalPerson extends Person {
    protected String email;

    public PhysicalPerson(String cpf, String nome, String endereco, String natureza) {
        super(cpf, nome, endereco);
        this.email = natureza;
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
