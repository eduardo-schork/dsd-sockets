package models;

import java.util.HashMap;

public class Wallet {

    protected String nome;
    protected Person responsavel;
    protected Double faixaSalarialInicial;
    protected Double faixaSalarialFinal;

    protected HashMap<String, Person> clientes = new HashMap<String, Person>();

    public Wallet(String nome, Person responsavel, Double faixaSalarialInicial, Double faixaSalarialFinal,
            HashMap<String, Person> clientes) {
        this.nome = nome;
        this.responsavel = responsavel;
        this.faixaSalarialInicial = faixaSalarialInicial;
        this.faixaSalarialFinal = faixaSalarialFinal;
        this.clientes = clientes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Person getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Person responsavel) {
        this.responsavel = responsavel;
    }

    public Double getFaixaSalarialInicial() {
        return faixaSalarialInicial;
    }

    public void setFaixaSalarialInicial(Double faixaSalarialInicial) {
        this.faixaSalarialInicial = faixaSalarialInicial;
    }

    public Double getFaixaSalarialFinal() {
        return faixaSalarialFinal;
    }

    public void setFaixaSalarialFinal(Double faixaSalarialFinal) {
        this.faixaSalarialFinal = faixaSalarialFinal;
    }

    public HashMap<String, Person> getClientes() {
        return clientes;
    }

    public void setClientes(HashMap<String, Person> clientes) {
        this.clientes = clientes;
    }

    public void addCliente(Person cliente) {
        this.clientes.put(cliente.getCpf(), cliente);
    }

    public void removeCliente(Person cliente) {
        this.clientes.remove(cliente.getCpf());
    }

    @Override
    public String toString() {
        return nome + ";" + faixaSalarialInicial + ";" + faixaSalarialFinal + ";" + responsavel.toString() + ";"
                + clientes.size();
    }
}
