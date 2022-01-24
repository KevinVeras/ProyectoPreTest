package com.example.myapplicationapp.ui.dispensador;

public class ModelDispensador {

    String horario1;
    String horario2;
    String horario3;
    String horario4;
    String cantidadAgua;
    String cantidadComida;
    String automatico;
    String programable;

    public ModelDispensador() {
        //builder required
    }


    public String getHorario1() {
        return horario1;
    }

    public void setHorario1(String horario1) {
        this.horario1 = horario1;
    }

    public String getHorario2() {
        return horario2;
    }

    public void setHorario2(String horario2) {
        this.horario2 = horario2;
    }

    public String getHorario3() {
        return horario3;
    }

    public void setHorario3(String horario3) {
        this.horario3 = horario3;
    }

    public String getHorario4() {
        return horario4;
    }

    public void setHorario4(String horario4) {
        this.horario4 = horario4;
    }

    public String getCantidadAgua() {
        return cantidadAgua;
    }

    public void setCantidadAgua(String cantidadAgua) {
        this.cantidadAgua = cantidadAgua;
    }

    public String getCantidadComida() {
        return cantidadComida;
    }

    public void setCantidadComida(String cantidadComida) {
        this.cantidadComida = cantidadComida;
    }

    public String getAutomatico() {
        return automatico;
    }

    public void setAutomatico(String automatico) {
        this.automatico = automatico;
    }

    public String getProgramable() {
        return programable;
    }

    public void setProgramable(String programable) {
        this.programable = programable;
    }
}
