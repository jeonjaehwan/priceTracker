package com.pricewatcher.price_alert_service.util;

import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SmsUtil {

    @Value("${coolsms.api.key}")
    private String apiKey;
    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    private DefaultMessageService messageService;

    @PostConstruct
    private void init(){
        messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    public SingleMessageSentResponse sendOne(String to, String productName, BigDecimal targetPrice, BigDecimal currentPrice) {
        Message message = new Message();
        message.setFrom("01077696585");
        message.setTo(to);
        message.setText("가격 알림: " + productName + "가 목표 가격인 " + targetPrice + "원 이하로 내려갔습니다! 현재 가격: " + currentPrice + "원입니다. 지금 확인해 보세요.");

        return messageService.sendOne(new SingleMessageSendingRequest(message));
    }

}