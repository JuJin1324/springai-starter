package com.starter.springai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyAiServiceTest {
    @Autowired
    private MyAiService myAiService;

    @Test
    void getAnswer() {
        String question = "오늘의 서울 날씨를 알려줄래?";
        String answer = myAiService.getAnswer(question);
        System.out.println("답변: " + answer);
    }
}