package br.edu.ifsuldeminas.mch.todolist.domain;

import java.io.Serializable;

public class Task implements Serializable {
    private Integer id;
    private String description;
    private Boolean active;

    public Task(Integer id, String desc, Boolean active){
        this.id=id;
        description = desc;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    private String getActiveString(){

        String result = active ? "Ativa" : "Realizada";

        return result;
    }

    @Override
    public String toString(){
        String formatted = String.format("%s - %s", this.getDescription(), this.getActiveString());
        return formatted;
    }
}
