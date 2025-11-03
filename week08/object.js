const myPet = {
  name: "Momo",
  type: "Cat",
};

console.log(myPet.name, myPet.type);

class Person {
  constructor(name, age) {
    this.name = name;
    this.age = age;
  }

  greet() {
    console.log("Hello, name is " + this.name + " and I am " + this.age + " years old.");
  }
}

new Person("Jane Doe", 25).greet();
