package Model;

import Controllers.Controller;

import java.sql.SQLException;

public class Alerta extends Thread {
    public String id;
    String emailTo;
    String almacenID;
    String articulo_id;
    double cantidad;
    String condicion;
    String empresaID;
    String periodicidad;
    String tipoAlerta;
    String tipoAlertaCompleto;
    String usaurio_id;

    public Alerta(String id) {
    }

    public enum tipoA{
        STK,
        VTS,
        PRU
    }


    public Alerta() {
        this("-1","","","",0,"","","","","");
    }

    public Alerta(String id, String emailTo, String almacenID, String articulo_id, double cantidad, String condicion, String empresaID, String periodicidad, String tipoAlerta, String usaurio_id) {
        this.id = id;
        this.emailTo = emailTo;
        this.almacenID = almacenID;
        this.articulo_id = articulo_id;
        this.cantidad = cantidad;
        this.condicion = condicion;
        this.empresaID = empresaID;
        this.periodicidad = periodicidad;
        this.tipoAlerta = tipoAlerta;
        this.usaurio_id = usaurio_id;
    }

    public String getTipoAlertaCompleto() {
        return tipoAlertaCompleto;
    }

    public void setTipoAlertaCompleto(String tipoAlertaCompleto) {
        this.tipoAlertaCompleto = tipoAlertaCompleto;
    }

    /*public String getId() {
        return id;
    }*/

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getAlmacenID() {
        return almacenID;
    }

    public void setAlmacenID(String almacenID) {
        this.almacenID = almacenID;
    }

    public String getArticulo_id() {
        return articulo_id;
    }

    public void setArticulo_id(String articulo_id) {
        this.articulo_id = articulo_id;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getEmpresaID() {
        return empresaID;
    }

    public void setEmpresaID(String empresaID) {
        this.empresaID = empresaID;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public String getUsaurio_id() {
        return usaurio_id;
    }

    public void setUsaurio_id(String usaurio_id) {
        this.usaurio_id = usaurio_id;
    }

    @Override
    public String toString() {
        return "Alerta{" +
                "id='" + id + '\'' +
                ", emailTo='" + emailTo + '\'' +
                ", almacenID='" + almacenID + '\'' +
                ", articulo_id='" + articulo_id + '\'' +
                ", cantidad=" + cantidad +
                ", condicion='" + condicion + '\'' +
                ", empresaID='" + empresaID + '\'' +
                ", periodicidad='" + periodicidad + '\'' +
                ", tipoAlerta='" + tipoAlerta + '\'' +
                ", usaurio_id='" + usaurio_id + '\'' +
                '}';
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Comprando otra vez de aqui a 15 s");
            try {
                Controller.controlar();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(15000);
            } catch (InterruptedException ie) {
                System.out.println("ERROR FATAL");
            }
        }
    }
}
