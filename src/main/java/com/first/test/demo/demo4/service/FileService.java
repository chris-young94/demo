package com.first.test.demo.demo4.service;

import com.alibaba.fastjson.util.IOUtils;
import com.first.test.demo.demo4.dao.AppInfoRepo;
import com.first.test.demo.demo4.entity.AppInfo;
import com.first.test.demo.demo4.entity.FileVO;
import com.first.test.demo.demo4.resp.FileStatus;
import com.first.test.demo.demo4.resp.GlobalConstant;
import com.first.test.demo.demo4.resp.RestResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;


/**
 * @author chris
 */

@Slf4j
@Service
public class FileService {

    @Resource
    AppInfoRepo appInfoRepo;

    @Transactional(rollbackFor = Exception.class)
    public RestResp fileUpload(FileVO fileVO) {
        try {
            File file = new File(FileStatus.getFilePath() + fileVO.getName());
            if (file.exists()) {
                return RestResp.fail("The name is already exist,please change a name");
            }

            // TODO: 2019/7/17 可以增加判断文件是否相同

            //transferTo 是文件上传简明方法之一
            fileVO.getMultipartFile().transferTo(file);
            return RestResp.success("upload success");
        } catch (IOException e) {
            log.info("upload file fail");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return RestResp.fail();
    }

    @Transactional(rollbackFor = Exception.class)
    public RestResp AppUpload(FileVO fileVO, String version) {
        AppInfo appInfo = appInfoRepo.findByIsNewest(GlobalConstant.IsNewestEnum.NEW_VERSION.getCode());
        if (null == appInfo) {
            return RestResp.fail("query newest app fail");
        }
        appInfo.setIsNewest(GlobalConstant.IsNewestEnum.OLD_VERSION.getCode());
        appInfoRepo.save(appInfo);

        AppInfo newestApp = new AppInfo(
                fileVO.getName(),
                System.currentTimeMillis(),
                version,
                FileStatus.getFilePath() + "App_name" + version + ".apk",
                1
        );
        appInfoRepo.save(newestApp);
        return null;
    }

    public void download(String name, HttpServletResponse response) throws Exception {
        if (null == appInfoRepo.findByName(name)){
            throw new Exception("app is not exist");
        }
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            File file = new File(FileStatus.getFilePath() + name);

            if (!file.exists()) {
                throw new Exception("file do not exist");
            }

            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Encoding", "gzip");
            response.setHeader("Content-Length", "" + file.length());
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));

            byte[] buffer = new byte[4096];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }

            log.info("Android apk file download successful");
        } catch (Exception e) {
            log.error("Download file failed: {}", e);
        } finally {
            IOUtils.close(fis);
            IOUtils.close(bis);
            IOUtils.close(os);
        }
    }
}
