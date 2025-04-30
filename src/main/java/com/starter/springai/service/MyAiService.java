package com.starter.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class MyAiService {
    private final ChatClient chatClient;

    public MyAiService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    // 동기 방식
    public String getAnswer(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    // 스트리밍 방식: 실제 브라우저에 적용하려면 SpringWebFlux 로 구현 필요.
    public void getAnswerStream(String question) {
        chatClient.prompt()
                .user(question)
                .stream()
                .content()
                .subscribe(partialResponse -> System.out.println(partialResponse),
                        error -> System.err.println("Error: " + error),
                        () -> System.out.println("Completed")
                );
    }
}
