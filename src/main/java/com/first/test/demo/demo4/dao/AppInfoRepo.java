package com.first.test.demo.demo4.dao;

import com.first.test.demo.demo4.entity.AppInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author chris
 */

@Repository
public interface AppInfoRepo extends JpaRepository<AppInfo, Long>, Serializable {

    AppInfo findByIsNewest(Integer isNewest);

    AppInfo findByName(String name);
}
