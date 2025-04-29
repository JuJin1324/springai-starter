# Spring AI Starter

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
> * 사용할 AI 공급자(예: spring.ai.openai.api-key, spring.ai.vertex.ai.gemini.project-id 등), 모델 이름(spring.ai.openai.chat.options.model), 온도(temperature), 최대 토큰 수 등의 옵션을 설정.

---

