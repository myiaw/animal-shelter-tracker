package com.example.animalshelter.classes

class Dog(name: String, age: UInt, isAdopted: Boolean) : Animal(name, age, isAdopted) {

    override val type = AnimalTypes.DOG.type
    override val breed: String? = null
    override var isWild: Boolean? = null

    override fun sayHello(): String {
        return "Woof"
    }

    override fun toString(): String {
        return "Dog: $name, $age, $isAdopted, $type, $breed, $isWild, ${sayHello()}"
    }
}
