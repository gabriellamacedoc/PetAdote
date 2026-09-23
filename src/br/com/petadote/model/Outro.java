package br.com.petadote.model;

import java.time.LocalDate;

public class Outro extends Animal {
    private String especie;

    public Outro() {
    }

    public Outro(String nome, LocalDate dataNascimento, String especie) {
        super(nome, dataNascimento);
        this.especie = especie;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
}