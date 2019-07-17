package com.first.test.demo.demo4.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author chris
 */

@Data
@Entity
@Table(name = "app_info")
public class AppInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long createTime;

    private String version;

    private String linkUrl;

    //是否是最新版本 0代表否 1代表是
    private Integer isNewest;

    public AppInfo() {
    }

    public AppInfo(String name, Long createTime, String version, String linkUrl, Integer isNewest) {
        this.name = name;
        this.createTime = createTime;
        this.version = version;
        this.linkUrl = linkUrl;
        this.isNewest = isNewest;
    }
}
