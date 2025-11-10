const myFirstPromise = new Promise((resolve, reject) => {
  resolve("Hello, Promise!");
});

myFirstPromise.then((message) => {
  console.log(message);
});

async function waitForMessage() {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve("Hello, Async/Await!");
    }, 1000);
  });
}

// 1. IIFE 사용
(async () => {
  console.log(await waitForMessage());
})();

// 2. then() 사용
waitForMessage().then((msg) => {
  console.log(msg);
});
