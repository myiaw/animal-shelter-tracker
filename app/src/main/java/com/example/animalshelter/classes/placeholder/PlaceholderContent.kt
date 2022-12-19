package com.example.animalshelter.classes.placeholder

import com.example.animalshelter.classes.Cat
import com.example.animalshelter.classes.Dog
import io.github.serpro69.kfaker.faker
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<Animal> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, Animal> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createPlaceholderItem(i) as Animal)
        }
    }

    private fun addItem(item: Animal) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    private fun createPlaceholderItem(position: Int): Any {
        val faker =  faker {  }
        faker.unique.configuration {
            enable(faker::animal)
        }
        val rand = faker.random.nextInt(1..2)
        return if(rand == 1){
            Dog(faker.name.firstName(), faker.random.nextInt(1..12).toUInt(), faker.random.nextBoolean())
        } else{
            Cat(faker.name.firstName(), faker.random.nextInt(1..15).toUInt(), faker.random.nextBoolean())
        }
    }

//    private fun makeDetails(position: Int): String {
//        val builder = StringBuilder()
//        builder.append("Details about Item: ").append(position)
//        for (i in 0 until position) {
//            builder.append("\nMore details information here.")
//        }
//        return builder.toString()
//    }

    /**
     * A placeholder item representing a piece of content.
     */
    data class Animal(val id: String, val name: String, val age: UInt, val isAdopted: Boolean, val isWild: Boolean, val type: String, val breed: String) {
        override fun toString(): String = "$name $age $isAdopted $isWild $type $breed"
    }
}