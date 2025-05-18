package com.starter.springai.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/18 */
@SpringBootTest
class ToolCalledQuestionServiceTest {
    @Autowired private ToolCalledQuestionService toolCalledQuestionService;

    @Test
    void askWithWeatherFunction() {
        // var answer = toolCalledQuestionService.askWithWeatherFunction("오늘 서울의 날씨 어때?");
        var answer = toolCalledQuestionService.askWithWeatherFunction("오늘 서울의 날씨 어때? 섭씨로 알려줄래?");
        System.out.println("answer = " + answer);
    }
}
