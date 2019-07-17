package com.first.test.demo.demo4.resp;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



/**
 * @author chris
 */
@Component
public class FileStatus {
    private static String fileDownloadPath;
    private static String filePath;

    public static String getFileDownloadPath(){
        return fileDownloadPath;
    }

    @Value("http://localhost:9020/app/download/")
    public void setFilePath(String fileDownloadPath){
        FileStatus.fileDownloadPath=fileDownloadPath;
    }

    public static String getFilePath() {
        return filePath;
    }

    @Value("${app.file.path}")
    public void setAppFilePath(String filePath) {
        FileStatus.filePath = filePath;
    }
}
