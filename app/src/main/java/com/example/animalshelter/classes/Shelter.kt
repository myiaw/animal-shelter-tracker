package com.example.animalshelter.classes

class Shelter(val name: String, val address: String) {

    companion object {
        var animals: MutableList<Animal> = mutableListOf()
    }

    fun addAnimal(animal: Animal) {
        animals.add(animal)
    }

    fun removeAnimal(animal: Animal) {
        animals.remove(animal)
    }

    fun getAnimals(): List<Animal> {
        return animals
    }
}
