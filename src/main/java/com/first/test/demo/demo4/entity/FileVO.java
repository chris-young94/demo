package com.first.test.demo.demo4.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chris
 */

@Data
public class FileVO {
    MultipartFile multipartFile;
    String name;
    String version;
}
