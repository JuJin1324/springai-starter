package com.starter.springai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2025/5/1
 */

@Service
@RequiredArgsConstructor
public class StreamingQuestionService {
	private final ChatClient chatClient;
	private final MetaDataLogger logger;

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
