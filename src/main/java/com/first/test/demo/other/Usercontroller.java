package com.first.test.demo.other;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Transactional


@RestController
public class Usercontroller {
    @Resource
    private UserDao userDao;
    @RequestMapping("/findByUsephone")
    public String getByUsephone(Long usephone) {
        String name;
        Integer age;
        String mail;
        User user = userDao.findByUserphone(usephone);
        if (user != null) {
            name = String.valueOf(user.getName());
            age = Integer.valueOf(user.getAge());
            mail = String.valueOf(user.getMail());
            return "The userInfo is: " + name + age +mail;
        }
        return "user " + usephone + " is not exist.";
    }

    @RequestMapping("/updateUserInfo")
    public String updateUser(String name , Integer age , String mail) {

        User user2 = userDao.updateUserInfo(name,age,mail);
        if (true) {
            return "The NewUserInfo is: " + user2.getName() + user2.getAge() + user2.getMail();
        }
        return "user id is not exist.";
    }

    @RequestMapping("/deleteUserBYUsephoneAndPwd")
    public boolean deleteUserByUsephoneAndPwd(Long usephone, String pwd) {
     userDao.deleteUserByUsephoneAndPwd(usephone,pwd);
//        if (user3 != null){
//            return "The user " + user3.getUsephone() +"is delete";
//        }
//        return "user " + user3.getUsephone() + " is not exist or pwd is not right";
        return  true;
    }

    @RequestMapping("/addUser")
    public String add(Long usephone,String pwd) {
        User user4 = userDao.addUser(usephone,pwd);
        if (user4 != null) {
            return "user " + user4.getUsephone() + " is exist.";
        }
        return "addUser success.";
    }
    @RequestMapping("/list")
    public List<User> list() {
        return  userDao.findAll();
    }


}

