package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("aliyun.sms.template_code")
    private String template_code;
    @Value("aliyun.sms.sign_name")
    private String sign_name;

    @RabbitHandler
    public void executeSms(Map<String, Object> params) {
        String mobile = (String) params.get("mobile");
        String checkcode = (String) params.get("checkcode");
        System.out.println("手机号:" + mobile);
        System.out.println("验证码:" + checkcode);
        // TODO 暂时没有阿里云短信(模板申请不下来)
//        try {
//            smsUtil.sendSms(mobile, template_code, sign_name, "{\"checkcode\":\""+checkcode+"\"}");
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
    }
}
