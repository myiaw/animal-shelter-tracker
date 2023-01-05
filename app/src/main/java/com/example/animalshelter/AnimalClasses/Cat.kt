package com.example.animalshelter.AnimalClasses

class Cat(name: String, age: Int, isAdopted: Boolean, override val breed: String?, override var isWild: Boolean?): Animal(name, age, isAdopted) {
    override val type: String = AnimalTypes.CAT.type
    override fun sayHello(): String {
        return "Meow"
    }

    override fun toString(): String {
        return "Cat: $name, $age,$isAdopted, $type, $breed, $isWild, ${sayHello()}"
    }
}
