# Spring AI Starter

## 사용 예시 (Google Gemini 사용)

### 의존성 추가 (Gradle)
> Google Gemini 에서는 현재(2025-04-30) 공식적으로 Java, Spring SDK 를 제공하지 않기 때문에 openai 라이브러리를 이용하여 구현한다.
> ```groovy
> dependencies {
>     implementation 'org.springframework.ai:spring-ai-starter-model-openai'
> }
> ```

### application.yml 설정
> Gemini 사용 가능 모델 확인 링크: https://ai.google.dev/gemini-api/docs/models
> ```yaml
> spring:
>   ai:
>       openai:
>           chat:
>               base-url: https://generativelanguage.googleapis.com
>               completions-path: /v1beta/openai/chat/completions
>               options:
>                   model: gemini-2.0-flash
>           api-key: ${YOUR_API_KEY}
> ```

---

## 다양한 AI 기능 지원

> * Chat Completion: 챗봇과 같은 대화형 AI 모델과의 상호작용.
> * Text Embedding: 텍스트를 벡터로 변환하여 의미 기반 검색, 유사도 측정 등에 활용.
> * Image Generation: 텍스트 설명을 기반으로 이미지 생성 (DALL·E 등).
> * (향후 확장 가능성): 음성 인식, 번역 등 다양한 AI 기능으로 확장될 가능성이 있어.

---

## 주요 컴포넌트 및 API

### ChatClient 인터페이스

> * 핵심: 챗봇 형태의 LLM(Large Language Model)과 상호작용하기 위한 기본 인터페이스.
> * 주요 메서드:
> * call(Prompt prompt): 단일 요청-응답 방식. 프롬프트를 보내고 AI 응답(AiResponse)을 받음.
> * stream(Prompt prompt): 스트리밍 방식. AI가 생성하는 응답 토큰을 실시간으로 받아 처리 (Flux<AiResponse>).
> * 구현체: 설정에 따라 OpenAiChatClient, VertexAiGeminiChatClient, OllamaChatClient 등이 자동으로 주입됨.

### EmbeddingClient 인터페이스

> * 텍스트를 벡터 임베딩으로 변환하는 기능을 제공.
> * embed(String text) 또는 embed(List<String> texts) 등의 메서드를 가짐.
> * Vector Database와 함께 RAG(Retrieval-Augmented Generation) 패턴 구현 시 핵심 역할.

### ImageClient 인터페이스

> * 텍스트 프롬프트를 기반으로 이미지를 생성하는 기능을 제공.
> * call(ImagePrompt prompt) 등의 메서드를 가짐.

### Prompt 및 PromptTemplate

> * Prompt: AI 모델에게 전달하는 입력. 사용자 메시지, 시스템 메시지, 이전 대화 기록 등을 포함할 수 있음.
> * PromptTemplate: 동적인 값(변수)을 포함하는 프롬프트 템플릿을 쉽게 만들고 관리할 수 있게 해줌.

### AiResponse 및 Generation

> * AiResponse: AI 모델로부터 받은 응답 전체를 나타냄. 메타데이터(토큰 사용량 등) 포함.
> * Generation: 실제 생성된 콘텐츠(텍스트, 이미지 URL 등)와 추가 정보(예: 종료 이유)를 담고 있음. AiResponse 안에 하나 이상의 Generation이 포함될 수 있음.
> * 설정 (application.properties / application.yml):
> * 사용할 AI 공급자(예: spring.ai.openai.api-key, spring.ai.vertex.ai.gemini.project-id 등), 모델 이름(
    spring.ai.openai.chat.options.model), 온도(temperature), 최대 토큰 수 등의 옵션을 설정.

---

## Spring AI 학습 로드맵
### 기본 개념 및 필요성 이해 (Foundation & Motivation)

- Spring AI란 무엇인가? : 이 프로젝트의 목표(AI 공급자 추상화, 이식성, 스프링 통합)와 왜 필요한지(벤더 종속성 감소, 개발 생산성 향상) 이해하기.
- 생성형 AI 기본 용어 숙지: LLM(Large Language Model), 프롬프트(Prompt), 토큰(Token), 임베딩(Embedding), 챗 컴플리션(Chat Completion), 벡터 데이터베이스(Vector Database) 등의 기본적인 AI 용어에 대한 이해가 선행되면 학습이 훨씬 수월해져. (AI 전문가 수준이 아니어도 괜찮아!)
    - 생성형 AI 의 기본 용어에 대해서 더 자세히 알려줄래?
    - 프롬프트 엔지니어링에 대해서 자세히 알려줄래?
    - 파인 튜닝을 위해서는 임베딩 과정이 선행되어야 하는거야? 임베딩 과정은 파인 튜닝 이전에 개발자가 백터 데이터베이스에 임베딩을 적용한 후에 파인 튜닝할 모델에 적용해야하는거야?
- 핵심 인터페이스 소개: `ChatClient`, `EmbeddingClient` 등 Spring AI가 제공하는 주요 추상화 인터페이스가 어떤 역할을 하는지 간단히 파악하기.

### 핵심 API 심층 학습: `ChatClient` 활용

- `Prompt` 객체 이해: 단순 문자열뿐만 아니라 `UserMessage`, `SystemMessage`,  `AssistantMessage` 등을 포함하는 `Prompt` 객체를 구성하는 방법 학습. (시스템 메시지로 역할 부여, 이전 대화 내용 포함 등)
    - Prompt 옵션에는 무엇들이 올 수 있고 각 옵션은 무엇을 뜻하는지 설명해줄래?
- `AiResponse` 및 `Generation`: 모델 응답 객체 구조 이해. 실제 생성된 텍스트(`content`), 메타데이터(토큰 사용량, 종료 이유 등) 확인 방법 학습.
- 동기 vs 스트리밍: `call()` (단일 응답)과 `stream()` (실시간 토큰 스트리밍, `Flux` 사용) 메서드의 차이점 이해 및 사용법 익히기.
- Chat Options 설정: 모델(model), 온도(temperature), 최대 토큰(maxTokens), Top-P, Top-K 등 다양한 옵션을 설정하여 응답을 제어하는 방법 학습 (`ChatClient.Options` 또는 설정 파일).

### 프롬프트 엔지니어링 기초 및 `PromptTemplate` 활용

- 프롬프트의 중요성: 원하는 결과를 얻기 위해 효과적인 프롬프트를 작성하는 기본 원칙 이해.
- `PromptTemplate` 사용: 동적인 값(변수)을 프롬프트에 쉽게 삽입하고 재사용 가능한 템플릿을 만드는 방법 학습. (예: 사용자 입력, 조회된 데이터 등을 프롬프트에 포함)

### 다양한 AI 공급자 연동 및 전환

- 설정 기반 전환: OpenAI 설정에서 Google Gemini 설정으로 (또는 Ollama 등 로컬 모델로) 변경해보고, 동일한 `ChatClient` 코드가 어떻게 다른 백엔드에서 작동하는지 경험하기. 이를 통해 Spring AI의 추상화 및 이식성 장점 체감.
- 각 공급자별 옵션 차이 인지: 공급자마다 지원하는 모델이나 세부 옵션이 다를 수 있음을 인지하고 관련 설정 확인.

### 출력 파싱 (Output Parsing)

- AI 모델의 텍스트 응답을 **구조화된 객체(Java Bean, Record, List, Map 등)**로 자동 변환하는 방법 학습.
- `BeanOutputParser`, `MapOutputParser`, `ListOutputParser` 등 Spring AI에서 제공하는 파서 활용법 익히기. (매우 유용!)

### 핵심 API 심층 학습: `EmbeddingClient` 활용

- 임베딩의 개념: 텍스트를 의미론적 벡터로 변환하는 것의 의미와 활용 분야(유사도 검색, 분류, 클러스터링 등) 이해.
- `EmbeddingClient` 사용: 텍스트를 입력하여 벡터 임베딩 값을 얻어오는 방법 실습.
- RAG 패턴의 기초: 임베딩이 어떻게 RAG(Retrieval-Augmented Generation) 구현의 핵심 요소가 되는지 이해.

### RAG (Retrieval-Augmented Generation) 구현

- RAG 패턴 이해: 외부 지식(문서, DB 등)을 검색하여 관련 정보를 프롬프트에 포함시켜 LLM의 답변 정확도와 최신성을 높이는 패턴 학습.
- Vector Database 연동:
    - 벡터 데이터베이스의 역할 이해 (임베딩 저장 및 유사도 검색).
    - Spring AI의 `VectorStore` 인터페이스 및 지원 구현체(Chroma, Pinecone, Redis, PostgreSQL/pgvector 등) 살펴보기.
    - 문서를 임베딩하여 벡터 DB에 저장하고, 사용자 질문과 유사한 문서를 검색하여 프롬프트에 추가하는 RAG 파이프라인 구현 실습. (Spring AI에서 가장 강력하고 실용적인 기능 중 하나!)

### Function Calling / Tools 활용

- LLM이 응답 생성 과정에서 **외부 함수나 API를 호출**하도록 지시하는 기능 학습. (예: "오늘 서울 날씨 알려줘" -> 날씨 API 호출 -> 결과 바탕으로 답변 생성)
- Spring AI에서 `@Bean`으로 등록된 메서드를 AI가 호출할 수 있도록 설정하고 활용하는 방법 익히기.

### 이미지 생성 (`ImageClient`) (선택 사항)

- 텍스트-이미지 변환 모델(DALL·E 등)을 사용해야 하는 경우, `ImageClient` 인터페이스 사용법 학습.

### 테스트 전략

- AI API 호출 부분을 어떻게 Mocking 하여 단위/통합 테스트를 작성할 수 있는지 학습. (비용 절감 및 테스트 안정성 확보)

### 고급 주제 및 실전 적용

- 비용 최적화 전략 (모델 선택, 프롬프트 길이 조절, 캐싱 등).
- 보안 고려 사항 (API 키 관리, 프롬프트 인젝션 방어).
- 성능 튜닝 및 모니터링.
- 실제 애플리케이션(웹 서비스, 배치 등)에 통합하는 베스트 프랙티스 탐색.

---
