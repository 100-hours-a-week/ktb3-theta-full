const arr = [1, 2, 3, 4, 5];
const sumResult = arr.reduce((acc, val) => acc + val, 0);
console.log(sumResult);

const multipleResult = arr.map((x) => x * 2);
console.log(multipleResult);
