package com.starter.springai.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PromptQuestionServiceTest {
	@Autowired
	private PromptQuestionService promptQuestionService;

	@Test
	void getPromptAnswer() {
		// 첫 번째 질문
		List<Message> history = new ArrayList<>();
		String userQuery1 = "제주도 2박 3일 가족 여행 코스 추천해줘!";
		String response1 = promptQuestionService.getPromptAnswer(history, userQuery1);
		System.out.println("AI 응답 1: " + response1);

		// 대화 기록 업데이트 (사용자 질문 + AI 답변)
		history.add(new UserMessage(userQuery1));
		history.add(new AssistantMessage(response1)); // 실제 응답 내용을 넣어야 함

		// 두 번째 질문 (이전 대화 내용을 포함하여 질문)
		String userQuery2 = "그럼 첫날 저녁 식사로 맛집 추천해줄래? 해산물 위주로!";
		String response2 = promptQuestionService.getPromptAnswer(history, userQuery2);
		System.out.println("AI 응답 2: " + response2);
	}
}