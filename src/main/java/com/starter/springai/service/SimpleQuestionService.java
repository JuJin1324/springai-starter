package com.starter.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SimpleQuestionService {
	private final ChatClient chatClient;
	private final MetaDataLogger logger;

	// 동기 방식
	public String getAnswer(String question) {
		var chatResponse = chatClient.prompt()
			.user(question)
			.call()
			.chatResponse();

		logger.logMetaData(chatResponse);

		return chatResponse
			.getResult()
			.getOutput()
			.getText();
	}
}
