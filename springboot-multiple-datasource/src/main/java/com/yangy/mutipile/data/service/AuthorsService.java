package com.yangy.mutipile.data.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title: AuthorsService
 * @Package com.yangy.mutipile.data.service
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/29 11:30
 **/

public interface AuthorsService {

    public void streamDownload(HttpServletResponse httpServletResponse) throws IOException;

    public void traditionDownload(HttpServletResponse httpServletRespons) throws IOException;

    void streamExport(HttpServletResponse response) throws IOException;

    void streamExportNew(HttpServletResponse response) throws IOException;
}
