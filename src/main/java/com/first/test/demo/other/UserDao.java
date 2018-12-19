package com.first.test.demo.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;



@Repository
public interface UserDao extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User>, Serializable {

    @Autowired
    ThreadLocal<UserDao> userDao = new ThreadLocal<UserDao>();
    @Query("select s from User s where s.usephone=?1 and s.pwd=?2")
    public User findByUserphoneAndPwd(Long usephone,String pwd);

    @Query("select s from User s where s.usephone=?1")
    public User findByUserphone(Long usephone);

//    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from User where usephone=?1 and pwd=?2", nativeQuery = true)
    void deleteUserByUsephoneAndPwd(@Param("usephone")Long usephone, @Param("pwd")String pwd);

    @Query(value = "update User set name=?1 and age=?2 and mail=?3", nativeQuery = true)
    @Modifying
    public User updateUserInfo(String name , Integer age , String mail);

    @Query(value = "insert into User(usephone,pwd) value(?1,?2)", nativeQuery = true)
    @Modifying
    public User addUser(Long usephone,String pwd);



}
