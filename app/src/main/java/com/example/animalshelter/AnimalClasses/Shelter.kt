package com.example.animalshelter.AnimalClasses

import io.github.serpro69.kfaker.faker
import kotlin.random.Random

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

    fun generateAnimals(amount: Int) {
        for (i in 0..amount) {
            val faker = faker {}
            if (Random.nextBoolean()) {
                addAnimal(
                    Cat(
                        faker.name.name(),
                        Random.nextInt(1, 12),
                        Random.nextBoolean(),
                        faker.cat.breed(),
                        Random.nextBoolean()
                    )
                )
            } else {
                addAnimal(
                    Dog(
                        faker.name.name(),
                        Random.nextInt(1, 12),
                        Random.nextBoolean(),
                        faker.dog.breed(),
                        Random.nextBoolean()
                    )
                )
            }
        }
    }

    fun outputAnimals() {
        for (animal in animals) {
            println(animal.toString())
        }

    }
}

    
    
    
    

