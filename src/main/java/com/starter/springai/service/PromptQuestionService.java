package com.starter.springai.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PromptQuestionService {
	private final ChatClient chatClient;
	private final MetaDataLogger logger;

	// 동기 방식
	public String getPromptAnswer(List<Message> conversationHistory, String question) {
		// 1. 시스템 메시지 정의 (AI 역할 부여)
		var systemMessage = new SystemMessage("""
			너는 항상 친절하고 명랑한 톤으로 답변하는 여행 전문가야.
			답변은 한국어로 하고, 이모티콘을 적절히 사용해줘.
			사용자가 요청하는 여행지에 대한 매력적인 추천 코스를 제안해야 해.
			""");

		// 2. 메시지 목록 생성 (시스템 메시지 + 이전 대화 기록 + 현재 사용자 질문)
		var messages = new ArrayList<Message>();
		messages.add(systemMessage); // 역할 부여 메시지를 가장 먼저 추가
		messages.addAll(conversationHistory); // 이전 대화 내용을 추가 (주고받은 UserMessage, AssistantMessage)
		messages.add(new UserMessage(question)); // 현재 사용자의 질문을 추가

		var prompt = new Prompt(messages);

		var chatResponse = chatClient.prompt(prompt).call().chatResponse();
		logger.logMetaData(chatResponse);

		return chatResponse
			.getResult()
			.getOutput()
			.getText();
	}
}
