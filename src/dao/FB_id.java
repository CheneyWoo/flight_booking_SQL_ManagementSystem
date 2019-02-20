package dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import dao.FB_db;

public class FB_id {
    public static String getId(String path, String dbase) {
        String file_path = "";
        String mod = "";
        String id = "";
        if (path.indexOf("/classes/") != -1) {
            file_path = path.split("/classes/")[1].substring(0, path.split("/classes/")[1].length() - 1);
            mod = file_path.split("/")[0];
        } else {
            file_path = path;
            mod = file_path.split("/")[0];
        }
        try {
            FB_db erp_db = new FB_db(dbase);
            String sql = "select file_id from " + mod + "_tree where file_path='" + file_path + "'";
            ResultSet rs = erp_db.executeQuery(sql);
            if (rs.next()) {
                id = rs.getString("file_id");
                id = id + "000000";
                id = id.substring(0, 6);
                int filenum_temp = readTime(dbase, file_path);
                writeTime(dbase, file_path);
                String time = "";
                java.util.Date now = new java.util.Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                time = formatter.format(now);// 获得当前时间
                id = id + time + filenum_temp + "";
            }
            erp_db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static int readTime(String dbase, String file_path) {
        int filenum = 0;
        java.util.Date now = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String time = formatter.format(now);// 获得当前时间

        FB_db erp_db = new FB_db(dbase);
        String kind_name = file_path.replaceAll("/", "_");
        String kind = kind_name + "_" + time;
        try {
            String sql = "select * from security_counter where kind_name='" + kind_name + "'";
            ResultSet rs = erp_db.executeQuery(sql);
            if (rs.next()) {
                if (kind.equals(rs.getString("kind"))) {
                    filenum = Integer.parseInt(rs.getString("count_value"));
                } else {
                    String sqla = "update security_counter set kind='" + kind + "', count_value='100001' where kind_name='" + kind_name + "'";
                    erp_db.executeUpdate(sqla);
                    filenum = 100001;
                }
            } else {
                String sqla = "insert into security_counter(kind,count_value,kind_name) values ('" + kind + "','100001','" + kind_name + "')";
                erp_db.executeUpdate(sqla);
                filenum = 100001;
            }
            erp_db.close();
        } catch (Exception ex) {
        }
        return filenum;
    }

    public static void writeTime(String dbase, String file_path) {
        int filenum = 0;
        java.util.Date now = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String time = formatter.format(now);// 获得当前时间

        FB_db erp_db = new FB_db(dbase);
        String kind_name = file_path.replaceAll("/", "_");
        String kind = kind_name + "_" + time;
        try {
            String sql = "select * from security_counter where kind_name='" + kind_name + "'";
            ResultSet rs = erp_db.executeQuery(sql);
            if (rs.next()) {
                filenum = Integer.parseInt(rs.getString("count_value"));
                filenum++;
                String sqla = "update security_counter set count_value='" + filenum + "' where kind_name='" + kind_name + "'";
                erp_db.executeUpdate(sqla);
            }
            erp_db.close();
        } catch (Exception ex) {
        }
    }
}
