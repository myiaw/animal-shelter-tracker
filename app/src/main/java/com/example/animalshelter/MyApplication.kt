package com.example.animalshelter

import android.app.Application
import com.example.animalshelter.AnimalClasses.Animal
import com.example.animalshelter.AnimalClasses.Shelter

class MyApplication : Application() {
    var mShelters: MutableList<Shelter> = mutableListOf()
    var listContainer = mutableListOf<Animal>()
}