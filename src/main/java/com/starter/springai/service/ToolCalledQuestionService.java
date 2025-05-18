package com.starter.springai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/18 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ToolCalledQuestionService {
    private final ChatClient chatClient;
    private final MetaDataLogger logger;

    public String askWithWeatherFunction(String userQuery) {
        UserMessage userMessage = new UserMessage(userQuery);

        // 호출 가능한 함수(빈)의 이름을 지정합니다.
        // Spring AI는 이 이름을 사용하여 ApplicationContext 에서 해당 빈을 찾고,
        // 빈의 메서드 정보(특히 @Description 어노테이션)를 바탕으로 함수 명세를 구성합니다.
        OpenAiChatOptions options =
                OpenAiChatOptions.builder().toolNames(Set.of("weatherService")).build();

        List<Message> messages = new ArrayList<>(List.of(userMessage));
        Prompt prompt = new Prompt(messages, options);

        log.info("Sending initial prompt to LLM with function definitions...");
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        AssistantMessage assistantMessage = response.getResult().getOutput();
        messages.add(assistantMessage); // LLM의 첫 응답(함수 호출 요청 또는 일반 답변)을 대화 기록에 추가

        logger.logMetaData(response);

        return assistantMessage.getText();
    }
}
