package com.yangy.mutipile.data.controller;

import com.yangy.mutipile.data.service.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title: AuthorsController
 * @Package com.yangy.mutipile.data.controller
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/29 11:45
 **/
@RestController
@RequestMapping("download")
public class AuthorsController {

    @Autowired
    AuthorsService authorsService;

    @GetMapping("streamDownload")
    public void streamDownload(HttpServletResponse response)
            throws IOException {
        authorsService.streamDownload(response);
    }

    @GetMapping("streamExport")
    public void streamExport(HttpServletResponse response)
            throws IOException {
        authorsService.streamExport(response);
    }

    @GetMapping("streamExportNew")
    public void streamExportNew(HttpServletResponse response)
            throws IOException {
        authorsService.streamExportNew(response);
    }


    @GetMapping("traditionDownload")
    public void traditionDownload(HttpServletResponse response)
            throws IOException {
        authorsService.traditionDownload(response);
    }


}
