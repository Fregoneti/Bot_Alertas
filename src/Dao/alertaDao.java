package Dao;

import Model.Alerta;
import Utils.ConDB;
import Utils.ConnectionUtil;
import Utils.Dialog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class alertaDao extends Alerta {

    Connection con;

    enum queries {
        ALL("select email,xalmacen_id,xarticulo_id,xcantidad,xcondicion,xempresa_id,xperiodicidad,xtipo_alerta,xusuario_id,xalerta_id from imp.p856_alertas"),
        GETBYID("SELECT email,xalmacen_id,xarticulo_id,xcantidad,xcondicion,xempresa_id,xperiodicidad,xtipo_alerta,xusuario_id,xalerta_id FROM imp.p856_alertas WHERE xalerta_id=?"),
        GETDIA("select email,xalmacen_id,xarticulo_id,xcantidad,xcondicion,xempresa_id,xperiodicidad,xtipo_alerta,xusuario_id,xalerta_id from imp.p856_alertas WHERE xperiodicidad='Diariamente'"),
        GETMES("select email,xalmacen_id,xarticulo_id,xcantidad,xcondicion,xempresa_id,xperiodicidad,xtipo_alerta,xusuario_id,xalerta_id from imp.p856_alertas WHERE xperiodicidad='Mensualmente'"),
        GETAÑO("select email,xalmacen_id,xarticulo_id,xcantidad,xcondicion,xempresa_id,xperiodicidad,xtipo_alerta,xusuario_id,xalerta_id from imp.p856_alertas WHERE xperiodicidad='Anualmente'");
        private final String q;

        queries(String q) {
            this.q = q;
        }

        public String getQ() {
            return this.q;
        }
    }

    ResultSet rs;
    private boolean persist;

    // UTILS for JUGADOR DAO
    public static Alerta instanceBuilder(ResultSet rs) throws SQLException {
        //ojo rs.getMetaData()
        Alerta c = new Alerta();
        if (rs != null) {
            c.setEmailTo(rs.getString(1));
            c.setAlmacenID(rs.getString(2));
            c.setArticulo_id(rs.getString(3));
            c.setCantidad(rs.getDouble(4));
            c.setCondicion(rs.getString(5));
            c.setEmpresaID(rs.getString(6));
            c.setPeriodicidad(rs.getString(7));
            c.setTipoAlerta(rs.getString(8));
            c.setUsaurio_id(rs.getString(9));
            c.setId(rs.getString(10));

        }
        return c;
    }


    public static List<Alerta> getTodasAlertas(Connection con) {
        List<Alerta> result = new ArrayList<>();
        try {
            con = ConDB.getCon();
            ResultSet rs = ConnectionUtil.execQuery(con, queries.ALL.getQ(), null);
            if (rs != null) {
                while (rs.next()) {
                    Alerta n = alertaDao.instanceBuilder(rs);
                    result.add(n);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Dialog.showError("ERRPR", "Error cargando la alerta", ex.toString());
        }
        return result;
    }

    public static List<Alerta> getTodasAlertasDia(Connection con) {
        List<Alerta> result = new ArrayList<>();
        try {
            con = ConDB.getCon();
            ResultSet rs = ConnectionUtil.execQuery(con, queries.GETDIA.getQ(), null);
            if (rs != null) {
                while (rs.next()) {
                    Alerta n = alertaDao.instanceBuilder(rs);
                    result.add(n);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Dialog.showError("ERRPR", "Error cargando la alerta", ex.toString());
        }
        return result;
    }

    public static List<Alerta> getTodasAlertasMes(Connection con) {
        List<Alerta> result = new ArrayList<>();
        try {
            con = ConDB.getCon();
            ResultSet rs = ConnectionUtil.execQuery(con, queries.GETMES.getQ(), null);
            if (rs != null) {
                while (rs.next()) {
                    Alerta n = alertaDao.instanceBuilder(rs);
                    result.add(n);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Dialog.showError("ERRPR", "Error cargando la alerta", ex.toString());
        }
        return result;
    }

    public static List<Alerta> getTodasAlertasAno(Connection con) {
        List<Alerta> result = new ArrayList<>();
        try {
            con = ConDB.getCon();
            ResultSet rs = ConnectionUtil.execQuery(con, queries.GETAÑO.getQ(), null);
            if (rs != null) {
                while (rs.next()) {
                    Alerta n = alertaDao.instanceBuilder(rs);
                    result.add(n);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Dialog.showError("ERRPR", "Error cargando la alerta", ex.toString());
        }
        return result;
    }
}


