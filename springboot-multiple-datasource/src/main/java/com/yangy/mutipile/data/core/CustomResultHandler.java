package com.yangy.mutipile.data.core;

import com.yangy.mutipile.data.dto.Authors;
import com.yangy.mutipile.data.util.DownloadProcessor;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

/**
 * @Title: CustomResultHandler
 * @Package com.yangy.mutipile.data.core
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/29 11:20
 **/
public class CustomResultHandler implements ResultHandler<Authors> {

    private final DownloadProcessor downloadProcessor;


    public CustomResultHandler(DownloadProcessor downloadProcessor) {
        super();
        this.downloadProcessor = downloadProcessor;
    }

    @Override
    public void handleResult(ResultContext resultContext) {
        Authors authors = (Authors) resultContext.getResultObject();
        //System.out.println("----authors:"+authors);
        downloadProcessor.processData(authors);
    }

}
