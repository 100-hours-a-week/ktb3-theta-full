/*
자바스크립트 코드 실행 과정

1. 생성 및 컴파일
    1-1. 토큰화 - 엔진이 소스 코드를 읽으며 각 요소를 토큰화, 의미 있는 조각 나눔
    1-2. AST 생성 - 구문 분석을 통해 추상 구조 트리 형태로 변환/불필요 연산 제거 및 에러 검출
    1-3. 실행 컨텍스트 생성
    1-4. 렉시컬 환경 결정 및 호이스팅
    1-5. 바이트 코드 생성
2. 실행
    2-1. 전역 실행 컨텍스트 Call Stack에 push
    2-2. 변수 할당
    2-3. 스코프 체인 - 렉시컬 스코프를 따라가며 외부 참조까지 확인
    2-4. 실행
        2-4-1. Call Stack에 있는 동기 코드 먼저 실행
        2-4-2. setTimeout, setInterval, I/O, requestAnimationFrame 등은 HostAPI(WebAPI 등)에서 실행 후 Macrotask Queue에 등록
        2-4-3. Promise 등은 Call Stack에서 동기 실행 후, .then(), await 이후의 코드는 일시정지, Microtask queue에 등록
        2-4-4. Call Stack 내 동기 코드 실행 완료 후, event loop이 microtask queue 먼저 확인, 있는 경우 실행
        2-4-5. 브라우저인 경우 requestAnimationFrame 단계(렌더 전) 실행
        2-4-5. event loop이 macrotask queue 확인, 있는 경우 실행
        2-4-6. 반복
*/

console.log(messageLet);
let messageLet = "Hello with let!";
// Reference Error

console.log(messageConst);
const messageConst = "Hello with const!";
// Reference Error

console.log(greet());
const greet = () => "Hello, Arrow Function!";
// Reference Error
// 호이스팅 후 TDZ가 되기 때문
