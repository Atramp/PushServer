package com.teradata.common;

import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Configuration {
    private static Properties properties = new Properties();
    private static MultiValueMap configurations = MultiValueMap.multiValueMap(new LinkedHashMap());
    @Autowired
    private static SqlSessionFactory sqlSessionFactory;

    static {
        load();
        loadFromDB();
    }

    public static void reload() {
        properties.clear();
        configurations.clear();
        load();
        loadFromDB();
    }

    private static void load() {
        String path = Configuration.class.getClassLoader().getResource("/application.properties").getPath();
        if (path != null && !path.isEmpty())
            try (InputStream is = new FileInputStream(new File(path))) {
                properties.load(new InputStreamReader(is, "UTF-8"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private static void loadFromDB() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            Connection conn = sqlSession.getConnection();
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT ID,TYPE,VALUE FROM CONFIGURATION WHERE STATUS = '0'");
                if (rs != null) {
                    while (rs.next()) {
                        Map item = new HashMap();
                        item.put("ID", rs.getString("ID"));
                        item.put("TYPE", rs.getString("TYPE"));
                        item.put("VALUE", rs.getString("VALUE"));
                        configurations.put(item.get("TYPE"), item);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null)
                        rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if (stmt != null)
                        stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static int getInt(String key) {
        String value = get(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long getLong(String key) {
        try {
            return Long.parseLong(get(key));
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public static long getLong(String key, Long defaultValue) {
        try {
            return Long.parseLong(get(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static String get(String key) {
        if (properties == null)
            return null;
        return properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value == null ? defaultValue : value;
    }

    public static List getListFromDB(String key) {
        return (List) configurations.getCollection(key);
    }

    public static Map getOneFromDB(String key) {
        List list = getListFromDB(key);
        if (list != null && !list.isEmpty())
            return (Map) list.get(0);
        return null;
    }

}

