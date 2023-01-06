package com.example.animalshelter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.animalshelter.AnimalClasses.Cat
import com.example.animalshelter.AnimalClasses.Dog
import com.example.animalshelter.AnimalClasses.Shelter
import com.example.animalshelter.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), OnDataPass {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Shelter.generateAnimals(10)


        setCurrentFragment(AnimalListFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itemInput -> setCurrentFragment(InputFragment())
                R.id.itemList -> setCurrentFragment(AnimalListFragment())
            }
            true
        }


    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }

    override fun onDataPass(data: String) {
        val strings = data.split(",").toTypedArray()
        Log.d("OnDataPass: ", "Clicked :$data")
        val name = strings[0]
        val age = strings[1].toInt()
        val breed = strings[2]
        val isAdopted = strings[3].toBoolean()
        val isWild = strings[4].toBoolean()
        val type = strings[5]


        if (type == "Cat") {
            if (Shelter.SelectedAnimal != null) {
                Shelter.animals[Shelter.animals.indexOf(Shelter.SelectedAnimal)] =
                    Cat(name, age, isAdopted, breed, isWild)
            } else {
                Shelter.animals.add(Cat(name, age, isAdopted, breed, isWild))
            }


        } else if (type == "Dog") {
            if (Shelter.SelectedAnimal != null) {
                Shelter.animals[Shelter.animals.indexOf(Shelter.SelectedAnimal)] =
                    Dog(name, age, isAdopted, breed, isWild)
            } else {
                Shelter.animals.add(Dog(name, age, isAdopted, breed, isWild))
            }
        }
        if (data == "change") {
            setCurrentFragment(InputFragment())
        }


    }


}


