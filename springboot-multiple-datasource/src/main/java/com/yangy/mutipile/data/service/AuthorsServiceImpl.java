package com.yangy.mutipile.data.service;

import com.yangy.mutipile.data.core.CustomResultHandler;
import com.yangy.mutipile.data.dto.Authors;
import com.yangy.mutipile.data.mapper.AuthorsMapper;
import com.yangy.mutipile.data.mapper.NewAuthorsMapper;
import com.yangy.mutipile.data.util.DownloadProcessor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @Title: AuthorsServiceImpl
 * @Package com.yangy.mutipile.data.service
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/29 11:56
 **/
@Service
public class AuthorsServiceImpl implements AuthorsService {

    @Autowired
    @Lazy
    SqlSessionTemplate sqlSessionTemplate;

    @Lazy
    @Autowired
    AuthorsMapper authorsMapper;


    @Lazy
    @Autowired
    NewAuthorsMapper newAuthorsMapper;

/*    public AuthorsServiceImpl(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }*/

    /**
     * stream读数据写文件方式
     *
     * @param httpServletResponse
     * @throws IOException
     */
    public void streamDownload(HttpServletResponse httpServletResponse)
            throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        HashMap<String, Object> param = new HashMap<>();
        CustomResultHandler customResultHandler = new CustomResultHandler(new DownloadProcessor(httpServletResponse));
        sqlSessionTemplate.select(
                "com.yangy.mutipile.data.mapper.AuthorsMapper.streamByExample", param, customResultHandler);
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    /**
     * 传统下载方式
     *
     * @param httpServletResponse
     * @throws IOException
     */
    public void traditionDownload(HttpServletResponse httpServletResponse)
            throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Authors authorsExample = new Authors();
        List<Authors> authors = authorsMapper.selectByExample(authorsExample);
        DownloadProcessor downloadProcessor = new DownloadProcessor(httpServletResponse);
        authors.forEach(downloadProcessor::processData);
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    public void streamExport(HttpServletResponse httpServletResponse)
            throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Authors authorsExample = new Authors();
        HashMap<String, Object> param = new HashMap<>();
        CustomResultHandler customResultHandler = new CustomResultHandler(new DownloadProcessor(httpServletResponse));
        newAuthorsMapper.streamExport(authorsExample, customResultHandler);
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
        stopWatch.stop();
        System.out.println("-------- mapper 传入 resultHandler---------");
        System.out.println(stopWatch.prettyPrint());
    }

    public void streamExportNew(HttpServletResponse httpServletResponse)
            throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Authors authorsExample = new Authors();
        CustomResultHandler customResultHandler = new CustomResultHandler(new DownloadProcessor(httpServletResponse));
        newAuthorsMapper.streamExportNew(authorsExample, customResultHandler);
/*        DownloadProcessor downloadProcessor = new DownloadProcessor(httpServletResponse);
        newAuthorsMapper.streamExportNew(authorsExample,resultContext -> {
            Authors vo = resultContext.getResultObject();
            System.out.println("############:"+vo);
            downloadProcessor.processData(vo);
        });*/
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
        stopWatch.stop();
        System.out.println("-------- mapper 传入 resultHandler new ---------");
        System.out.println(stopWatch.prettyPrint());
    }

}
