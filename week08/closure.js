function createCounter() {
  let count = 0;

  function increment() {
    count++;
  }

  function decreament() {
    count--;
  }

  function getCount() {
    console.log(count);
    return count;
  }

  return { increment, decreament, getCount };
}

const cC = createCounter();
cC.getCount();
cC.increment();
cC.getCount();
cC.decreament();
cC.getCount();

function messageMaker(startString) {
  function makeMessage(additionalString) {
    return startString + additionalString;
  }

  return makeMessage;
}

const mM = messageMaker("시작 문자열");
console.log(mM(" + 추가 문자열"));
