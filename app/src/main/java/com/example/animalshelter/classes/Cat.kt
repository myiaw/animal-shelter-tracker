package com.example.animalshelter.classes

class Cat(name: String, age: UInt, isAdopted: Boolean): Animal(name, age, isAdopted) {

    override val type = AnimalTypes.CAT.type
    override val breed: String? = null
    override var isWild: Boolean? = null

    override fun sayHello(): String {
        return "Meow"
    }

    override fun toString(): String {
        return "Cat: $name, $age,$isAdopted, $type, $breed, $isWild, ${sayHello()}"
    }
}
