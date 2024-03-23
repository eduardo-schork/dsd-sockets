package models;

public class LegalPerson extends Person {
    protected String cnpj;

    public LegalPerson(String cpf, String name, String address, String cnpj) {
        super(cpf, name, address);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return super.toString() + ";" + cnpj;
    }
}
