package com.example.animalshelter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.animalshelter.AnimalClasses.Shelter.Companion.SelectedAnimal
import com.example.animalshelter.AnimalClasses.Shelter.Companion.SelectedShelter
import com.example.animalshelter.databinding.FragmentInputBinding

class InputFragment : Fragment(R.layout.fragment_input) {
    lateinit var dataPasser: OnDataPass
    lateinit var selectedType: String
    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding!!.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }

    fun passData(data: String) {
        dataPasser.onDataPass(data)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animalTypesArray = requireContext().resources.getStringArray(R.array.animal_types_array)
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            animalTypesArray
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding?.spinnerType?.adapter = adapter

        binding?.spinnerType?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedType = animalTypesArray[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }


        if (SelectedAnimal != null) {
            val selectedAnimal = SelectedShelter?.getList()
                ?.get(SelectedShelter?.getList()?.indexOf(SelectedAnimal)!!)
            binding?.inputNameET?.setText(selectedAnimal?.name)
            binding?.inputAgeET?.setText(selectedAnimal?.age.toString())
            binding?.inputBreedET?.setText(selectedAnimal?.breed)
            binding?.spinnerType?.setSelection(
                when (selectedAnimal?.type) {
                    "Cat" -> 0
                    "Dog" -> 1
                    else -> throw IllegalArgumentException("Invalid animal type: ${selectedAnimal?.type}")
                }
            )
            binding?.btnToggleIsWild?.isChecked = selectedAnimal?.isWild == true
            binding?.btnToggleIsAdopted?.isChecked = selectedAnimal?.isAdopted == true
        }





        binding?.btnSubmit?.setOnClickListener {
            if (SelectedShelter == null) {
                Toast.makeText(
                    requireContext(),
                    "Please select a shelter first",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (binding!!.inputNameET.text.isNotEmpty() && binding!!.inputNameET.text.isNotEmpty() && binding!!.inputBreedET.text.isNotEmpty() && selectedType != "Nothing") {

                val isWild = if (binding!!.btnToggleIsWild.isChecked) "true" else "false"
                val isAdopted = if (binding!!.btnToggleIsAdopted.isChecked) "true" else "false"
                passData(binding!!.inputNameET.text.toString() + "," + binding!!.inputAgeET.text.toString() + "," + binding!!.inputBreedET.text.toString() + "," + isAdopted + "," + isWild + "," + selectedType)

                binding!!.inputNameET.text.clear()
                binding!!.inputAgeET.text.clear()
                binding!!.inputBreedET.text.clear()

            } else {
                binding!!.inputNameET.error = "Please enter the name"
                binding!!.inputAgeET.error = "Please enter the age"
                binding!!.inputBreedET.error = "Please enter the breed"
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AnimalListFragment()).commit()


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


