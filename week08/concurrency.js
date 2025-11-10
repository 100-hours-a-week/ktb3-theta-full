// 1번 퀘스트
console.log("Start");
console.log("Processing");
console.log("End");

// 결과 :
// Start
// Processing
// End

// 2번 퀘스트
console.log("Start");
setTimeout(() => {
  console.log("Async Operation Complete");
}, 1000);
console.log("End");

// 결과 :
// Start - 즉시 CallStack에서 실행, 출력됨
// End - 마찬가지로 즉시 CallStack에서 실행, 출력됨
// Async Operation Complete - WebAPI에서 1초 후에 콜백 함수를 태스크 큐로 보내고, CallStack이 비워진 후에 실행되어 출력됨
