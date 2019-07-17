package com.first.test.demo.demo4;

import com.first.test.demo.demo4.entity.FileVO;
import com.first.test.demo.demo4.resp.RestResp;

import com.first.test.demo.demo4.service.FileService;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * @author chris
 */

@RequestMapping("/file")
@RestController
public class FileController {

    @Resource
    FileService fileService;

    @PostMapping("/upload")
    public RestResp upload(@ModelAttribute FileVO fileVO) {
        if (fileVO.getMultipartFile().isEmpty()) {
            return RestResp.fail("file do not exist");
        }
        return fileService.fileUpload(fileVO);
    }

    @PostMapping("/uploadApp/{version}")
    public RestResp uploadApp(@ModelAttribute FileVO fileVO){
        if (fileVO.getMultipartFile().isEmpty()) {
            return RestResp.fail("file do not exist");
        }
        return fileService.AppUpload(fileVO,fileVO.getVersion());
    }

    @GetMapping("/down/{name}")
    public void downFile(@PathVariable @NotNull String name, HttpServletResponse response) throws Exception {
        fileService.download(name,response);
    }
}
