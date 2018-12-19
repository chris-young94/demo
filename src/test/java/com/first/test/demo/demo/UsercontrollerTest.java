package com.first.test.demo.demo;

import com.first.test.demo.other.Usercontroller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UsercontrollerTest {
    @Autowired
    private Usercontroller usercontroller;
    @Test
    void getByUsephone() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUserBYUsephoneAndPwd() {
    }

    @Test
    void add() throws Exception {
        usercontroller.add((long) 123123,"test");
    }

    @Test
    void list() {
        usercontroller.list();
    }
}