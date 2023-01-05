package com.example.animalshelter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.example.animalshelter.AnimalClasses.Animal
import com.example.animalshelter.AnimalClasses.Cat
import com.example.animalshelter.AnimalClasses.Dog
import com.example.animalshelter.AnimalClasses.Shelter
import com.example.animalshelter.databinding.ActivityMainBinding
import kotlin.math.log


class MainActivity : AppCompatActivity(), OnDataPass {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mshelter: Shelter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        
        mshelter = Shelter("Animal Shelter", "1234 Main Street");
        mshelter.generateAnimals(10)
        mshelter.outputAnimals()

        val inputFragment = InputFragment()
        val listFragment = ListFragment()
        setCurrentFragment(inputFragment)
        
        binding.btnInput.setOnClickListener {
            setCurrentFragment(inputFragment)
        }
        binding.btnList.setOnClickListener {
            setCurrentFragment(listFragment)
        }
     
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentInput, fragment)
            commit()
        }
    }

    override fun onDataPass(data: String) {
        val strings = data.split(",").toTypedArray()
        val name = strings[0]
        val age = strings[1].toInt()
        val breed = strings[2]
        val isAdopted = strings[3].toBoolean()
        val isWild = strings[4].toBoolean()
        val type = strings[5]
        if(type == "Cat") {
            mshelter.addAnimal(Cat(name, age, isAdopted, breed, isWild))
            Log.d("MainActivity", "Cat added" + mshelter.getAnimals().last().toString())
        } else {
            mshelter.addAnimal(Dog(name, age, isAdopted, breed, isWild))
            Log.d("MainActivity", "Dog added: " + mshelter.getAnimals().last().toString())
        }
       
    }


}


