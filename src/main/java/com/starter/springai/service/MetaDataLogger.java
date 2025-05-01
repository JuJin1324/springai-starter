package com.starter.springai.service;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2025/5/1
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class MetaDataLogger {

	public void logMetaData(ChatResponse chatResponse) {
		var responseMetadata = chatResponse.getMetadata();
		var result = chatResponse.getResult();
		if (responseMetadata == null) {
			return;
		}
		var usage = responseMetadata.getUsage();
		if (usage == null) {
			return;
		}
		log.info("--------------- Usage -----------------");
		log.info("Prompt Tokens(질문에 사용된 토큰 수): {}", usage.getPromptTokens());
		log.info("Generation Tokens(답변에 사용된 토큰 수): {}", usage.getCompletionTokens());
		log.info("Total Tokens(질문 + 답변에 사용된 토큰 수): {}", usage.getTotalTokens());

		var rateLimit = responseMetadata.getRateLimit();
		log.info("--------------- RateLimit(제미나이에서는 현재 미제공) -----------------");
		log.info("RequestsRemaining: {}", rateLimit.getRequestsRemaining());
		log.info("RequestsLimit: {}", rateLimit.getRequestsLimit());
		log.info("TokensRemaining: {}", rateLimit.getTokensRemaining());
		log.info("TokensLimit: {}", rateLimit.getTokensLimit());

		var resultMetaData = result.getMetadata();
		log.info("------------ Result.MetaData -----------------");
		log.info("FinishReason(답변이 종료된 이유 - STOP(정상 종료), "
			+ "LENGTH(응답의 길이가 모델이 생성할 수 있는 최대 토큰 길이에 도달하여 중간에 짤린 경우), "
			+ "CONTENT_FILTER(유해성 등으로 가이드라인이 위배되어 필터링된 경우),"
			+ "TOOL_CALLS(텍스트 생성 대신 외부 도구를 호출했을 때(코드 실행 기능등으로), "
			+ "NULL(알 수 없는 이유로 종료된 경우)): {}", resultMetaData.getFinishReason());
	}
}
