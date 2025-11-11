let temperature = 25;
temperature = 30;

const MAX_TEMPERATURE = 35;
// MAX_TEMPERATURE = 40; // Error!
// TypeError : const 변수에는 재할당 불가

if (true) {
  let isRaining = true;
}
console.log("Is it raining? : " + isRaining); // Error!
// ReferenceError : isRaining is not defined

if (true) {
  let isRaining = true;
  console.log("Is it raining? : " + isRaining);
}
// 정상적으로 작동함
