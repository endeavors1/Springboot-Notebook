package com.xiaofu.confirm.controller;

import com.xiaofu.confirm.sender.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: xiaofu
 * @Description:
 */
@Controller
@RequestMapping
public class TestController {

    @Autowired
    private SendMessage sendMessage;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String test() {

        //sendMessage.sendMessage("", "confirm_test_queue", "0" );

        System.out.println("producer开始产生消息------------------------");
        for (int i = 0; i < 1; i++) {

            sendMessage.sendMessage("", "confirm_test_queue", "发送者消息"+i);
        }

        return "success";
    }
}
