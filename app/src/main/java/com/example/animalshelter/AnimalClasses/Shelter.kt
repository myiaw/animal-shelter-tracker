package com.example.animalshelter.AnimalClasses

import io.github.serpro69.kfaker.faker
import kotlin.random.Random

class Shelter(val name: String, val address: String) {

    companion object {
        var animals: MutableList<Animal> = mutableListOf()


        fun generateAnimals(amount: Int) {
            for (i in 0..amount) {
                val faker = faker {}
                if (Random.nextBoolean()) {
                    animals.add(
                        Cat(
                            faker.name.firstName(),
                            Random.nextInt(1, 12),
                            Random.nextBoolean(),
                            faker.cat.breed(),
                            Random.nextBoolean()
                        )
                    )
                } else {
                    animals.add(
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
        
        
        
        var SelectedAnimal : Animal? = null
    }
    fun addAnimal(animal: Animal) {
        animals.add(animal)
    }

     

    fun removeAnimal(animal: Animal) {
        animals.remove(animal)
    }

  

    

    fun outputAnimals() {
        for (animal in animals) {
            println(animal.toString())
        }

    }
}

    
    
    
    

