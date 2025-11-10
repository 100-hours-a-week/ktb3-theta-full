const add = (a, b) => a + b;
const sum = add(2, 3);
console.log(sum);

const arr = [1, 2, 3, 4, 5];
let total = 0;
const sumArray = (arr) => {
  for (let i = 0; i < arr.length; i++) {
    total += arr[i];
  }
};
sumArray(arr);
console.log(total);
