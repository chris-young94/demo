package com.first.test.demo.demo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
    Connection con;
    public static final String url="jdbc:mysql://localhost:3306/demo?useSSL=false&useUnicode=true&characterEncoding=utf-8";
    public static final String name="com.mysql.jdbc.Driver";
    public static final String user="root";
    public static final String password="123";


    public Connection getConnection(){
        try{
            Class.forName(name);
            con= DriverManager.getConnection(url,user,password);

        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }

}

