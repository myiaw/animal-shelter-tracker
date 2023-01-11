package com.example.animalshelter.AnimalClasses

import io.github.serpro69.kfaker.faker
import kotlin.random.Random

class Shelter(
    val name: String,
) {
    var animals: MutableList<Animal> = mutableListOf()

    private fun generateAnimals(amount: Int, list: MutableList<Animal> = animals) {
        for (i in 1..amount) {
            val faker = faker {}
            if (Random.nextBoolean()) {
                list.add(
                    Cat(
                        faker.name.firstName(),
                        Random.nextInt(1, 12),
                        Random.nextBoolean(),
                        faker.cat.breed(),
                        Random.nextBoolean()
                    )
                )
            } else {
                list.add(
                    Dog(
                        faker.name.firstName(),
                        Random.nextInt(1, 12),
                        Random.nextBoolean(),
                        faker.dog.breed(),
                        Random.nextBoolean()
                    )
                )
            }
        }
    }


    init {
        generateAnimals(5)
    }


    fun getList(): MutableList<Animal> {
        return animals
    }

    companion object {
        var SelectedAnimal: Animal? = null
        var SelectedShelter: Shelter? = null
    }


}


    
    
    
    

