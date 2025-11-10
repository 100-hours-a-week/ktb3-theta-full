const message = "Hello, JavaScript!";

const showMessage = () => {
  console.log(message); // A
  let message = "Hello, ES6!";
  console.log(message); // B
};

showMessage();
// ReferenceError
// A 제거 시 B에서는 Hello, ES6! 정상 출력

const color = "blue";

const firstLevel = () => {
  let color = "red";

  const secondLevel = () => {
    let color = "green";
    console.log(color); // (1)
  };

  secondLevel();
  console.log(color); // (2)
};

firstLevel();
console.log(color); // (3)
// green
// red
// blue
