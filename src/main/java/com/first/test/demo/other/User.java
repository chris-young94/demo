package com.first.test.demo.other;

import com.sun.javafx.beans.IDProperty;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import java.util.Objects;
import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private Long usephone;
    private String pwd;
    private String name;
    private Integer age;
    private String mail;


    @Basic
    @Column(name = "id")
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){ this.id = id;}


    @Basic
    @Column(name = "usephone")
    public Long getUsephone(){
        return usephone;
    }

    public void setUsephone(Long usephone){
        this.usephone = usephone;
    }

    @Basic
    @Column(name = "pwd")
    public String getPwd(){
        return pwd;
    }

    public void setPwd(String pwd){
        this.pwd = pwd;
    }

    @Basic
    @Column(name = "name")
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


    @Basic
    @Column(name = "age")
    public Integer getAge(){
        return age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    @Basic
    @Column(name = "mail")
    public String getMail(){
        return mail;
    }

    public void setMail(String mail){
        this.mail = mail;
    }



}