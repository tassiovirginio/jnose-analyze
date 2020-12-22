package com.tassiovirginio.jnoseanalyze.entidades;

import java.io.Serializable;

public class TestSmellBean implements Serializable {

    private Integer id;
    private String nome;
    private String classTest;
    private String classProduction;
    private String range;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClassTest() {
        return classTest;
    }

    public void setClassTest(String classTest) {
        this.classTest = classTest;
    }

    public String getClassProduction() {
        return classProduction;
    }

    public void setClassProduction(String classProduction) {
        this.classProduction = classProduction;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

}
