package com.starter.springai.service;

import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

/** Created by Yoo Ju Jin(jujin1324@daum.net) Created Date : 2025/5/18 */
@Component("weatherService") // 빈 이름을 명시적으로 지정 (LLM이 참조할 이름)
@Description("Provides current weather information for a given location.") // 클래스 레벨 설명 (선택 사항)
public class WeatherService implements Function<WeatherService.WeatherRequest, WeatherService.WeatherResponse> {

    @Override
    public WeatherResponse apply(WeatherRequest request) {
        System.out.println(
            "WeatherService.apply called with location: "
                + request.location()
                + ", unit: "
                + request.unit());

        String location = request.location();
        String unit =
            (request.unit() == null || request.unit().isEmpty()) ? "celsius" : request.unit(); // 기본값 처리

        if ("서울".equalsIgnoreCase(location) || "Seoul, KR".equalsIgnoreCase(location)) {
            return unit.equalsIgnoreCase("fahrenheit")
                ? new WeatherResponse(location, 77, "°F", "맑음")
                : new WeatherResponse(location, 25, "°C", "맑음");
        } else if ("부산".equalsIgnoreCase(location) || "Busan, KR".equalsIgnoreCase(location)) {
            return unit.equalsIgnoreCase("fahrenheit")
                ? new WeatherResponse(location, 80, "°F", "구름 조금")
                : new WeatherResponse(location, 27, "°C", "구름 조금");
        }
        return new WeatherResponse(location, 0, unit, "정보 없음");
    }

    // AI가 호출할 함수의 요청 파라미터를 위한 Record (또는 클래스)
  public record WeatherRequest(
      @JsonProperty(required = true) // 이 필드는 JSON에서 필수임을 명시
          @JsonPropertyDescription(
              "The city and state, e.g. San Francisco, CA or Seoul, KR") // 여기에 파라미터 설명을 작성!
          String location,
      @JsonPropertyDescription(
              "The temperature unit, either 'celsius' or 'fahrenheit'. Defaults to 'celsius'.")
          String unit // 필수 아니므로 @JsonProperty(required = true) 없음, 기본값은 호출부에서 처리 가능
      ) {}

  // 함수의 응답을 위한 Record (또는 클래스)
  public record WeatherResponse(
      @JsonPropertyDescription("The location for the weather report") String location,
      @JsonPropertyDescription("The current temperature") int temperature,
      @JsonPropertyDescription("The unit of the temperature (celsius or fahrenheit)") String unit,
      @JsonPropertyDescription("A brief description of the weather conditions")
          String description) {}
}
