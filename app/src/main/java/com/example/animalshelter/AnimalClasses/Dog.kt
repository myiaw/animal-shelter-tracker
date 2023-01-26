package com.example.animalshelter.AnimalClasses

class Dog(name: String, age: Int, isAdopted: Boolean, override val breed: String?, override var isWild: Boolean?): Animal(name, age, isAdopted) {
    override val type: String = AnimalTypes.DOG.type
    override fun sayHello(): String {
        return "Woof"
    }

    override fun toString(): String {
        return "Dog: $name, $age, $isAdopted, $type, $breed, $isWild, ${sayHello()}"
    }
}
