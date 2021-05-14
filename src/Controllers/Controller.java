package Controllers;

import Dao.alertaDao;
import Model.Alerta;
import Utils.ConDB;
import Utils.email;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

public class Controller {


    private static final String USER_NAME = "comunicacion.rioma";  // GMail user name (just the part before "@gmail.com")
    private static final String PASSWORD = "Emete01rioma"; // GMail password
    private static String articulo;

    public static void controlar() throws SQLException, ClassNotFoundException {


        Connection con = ConDB.getCon();
        Calendar c = Calendar.getInstance();
        List<Alerta> alertaDia = alertaDao.getTodasAlertasDia(con);
        List<Alerta> alertaMes = alertaDao.getTodasAlertasMes(con);
        // List<Alerta> alertaAno = alertaDao.getTodasAlertasAno(con);

        //1 DE TODOS LOS MESES
        if (5 == (c.get(Calendar.DAY_OF_MONTH))) {
            System.out.println("Entro en day");
            for (int i = 0; i < alertaDia.size(); i++) {
                stock(alertaMes.get(i));
                oferta(alertaMes.get(i));

            }
        }
        //LUNES
        if (5 == (c.get(Calendar.DAY_OF_WEEK))) {
            for (int i = 0; i <= alertaDia.size(); i++) {
                stock(alertaDia.get(i));
                oferta(alertaDia.get(i));

            }
        }
    }

    public static void stock(Alerta a) {

        double existencias = 0;
        Statement stmt;
        ResultSet rs;
        String subject = "Alerta de Stock";
        String body;
        String[] correo;

        try {
            String consulta = "select xexistencia from imp.pl_existalm where xempresa_id = '" + a.getEmpresaID() + "' and xarticulo_id = '" + a.getArticulo_id() + "' and xalmacen_id = '" + a.getAlmacenID() + "'";
            stmt = ConDB.getCon().createStatement();
            rs = stmt.executeQuery(consulta);

            //INICIO DE INSERCIÓN FILAS
            while (rs.next()) {
                existencias = rs.getDouble(1);
            }
        } catch (SQLException | ClassNotFoundException ignored) {

        }


        articulo = "";


        try {

            String consulta = "select xdescripcion from imp.pl_articulos where xarticulo_id= '" + a.getArticulo_id() + "'";
            stmt = ConDB.getCon().createStatement();
            rs = stmt.executeQuery(consulta);

            //INICIO DE INSERCIÓN FILAS
            while (rs.next()) {
                articulo = rs.getString(1);
            }
        } catch (SQLException | ClassNotFoundException ignored) {

        }

        switch (a.getCondicion()) {
            case ">":
                if (a.getCantidad() > existencias) {

                    body = "Del articulo " + articulo + " con ID " + a.getArticulo_id() + " con " + existencias + " existencias, está por debajo de lo especificado '" + a.getCantidad() + "'";
                    correo = new String[]{a.getEmailTo()};
                    email.sendFromGMail(USER_NAME, PASSWORD, correo, subject, body);
                    System.out.println("ENVIADO EMAIL");
                }
                break;
            case "<":
                if (a.getCantidad() < existencias) {
                    correo = new String[]{a.getEmailTo()};
                    body = "Del articulo " + articulo + " con ID " + a.getArticulo_id() + " con " + existencias + " existencias, está por encima de lo especificado '" + a.getCantidad() + "'";
                    email.sendFromGMail(USER_NAME, PASSWORD, correo, subject, body);
                    System.out.println("ENVIADO EMAIL");
                }
                break;
            case ">=":
                if (a.getCantidad() >= existencias) {
                    correo = new String[]{a.getEmailTo()};
                    body = "Del articulo " + articulo + " con ID " + a.getArticulo_id() + " con " + existencias + " existencias, está por debajo o igual de lo especificado '" + a.getCantidad() + "'";
                    email.sendFromGMail(USER_NAME, PASSWORD, correo, subject, body);
                    System.out.println("ENVIADO EMAIL");
                }
                break;
            case "<=":
                if (a.getCantidad() <= existencias) {
                    correo = new String[]{a.getEmailTo()};
                    body = "Del articulo " + articulo + " con ID " + a.getArticulo_id() + " con " + existencias + " existencias, está por encima o igual de lo especificado '" + a.getCantidad() + "'";
                    email.sendFromGMail(USER_NAME, PASSWORD, correo, subject, body);
                    System.out.println("ENVIADO EMAIL");

                }
                break;
        }
    }

    public static void oferta(Alerta a) {

        String oferta = "";
        Statement stmt;
        ResultSet rs;
        String subject = "Alerta de Oferta";
        String body;
        String[] correo;

        try {
            String consulta = "select p856_cod_abc from imp.pl_articulos where xempresa_id = '" + a.getEmpresaID() + "' and xarticulo_id = '" + a.getArticulo_id() + "'";
            stmt = ConDB.getCon().createStatement();
            rs = stmt.executeQuery(consulta);

            //INICIO DE INSERCIÓN FILAS
            while (rs.next()) {
                oferta = rs.getString(1);
            }
        } catch (SQLException | ClassNotFoundException ignored) {

        }
        articulo = "";

        try {

            String consulta = "select xdescripcion from imp.pl_articulos where xarticulo_id= '" + a.getArticulo_id() + "'";
            stmt = ConDB.getCon().createStatement();
            rs = stmt.executeQuery(consulta);

            //INICIO DE INSERCIÓN FILAS
            while (rs.next()) {
                articulo = rs.getString(1);
            }
        } catch (SQLException | ClassNotFoundException ignored) {

        }


        switch (oferta) {
            case "O":
            case "DO":
            case "EO":
                body = "El articulo " + articulo + " con ID " + a.getArticulo_id() + "está en oferta ";
                correo = new String[]{a.getEmailTo()};
                email.sendFromGMail(USER_NAME, PASSWORD, correo, subject, body);
                System.out.println("ENVIADO EMAIL");
                break;


        }
    }
}