package com.atguigu.gmall0513.logger.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall0513.Constants.GmallConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Slf4j
public class LogController {
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @PostMapping("/log")
    public String log(@RequestParam("logString") String logString){
        System.out.println(logString);
        log.info(logString);
        JSONObject object = JSONObject.parseObject(logString);
        object.put("ts", System.currentTimeMillis());
        if("startup".equals(object.getString("type"))){
           kafkaTemplate.send(GmallConstant.KAFKA_STARTUP, object.toJSONString());
        }else{
            kafkaTemplate.send(GmallConstant.KAFKA_EVENT,object.toJSONString());
        }

        return "success";
    }
}
