package com.example.animalshelter

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animalshelter.AnimalClasses.Animal
import com.example.animalshelter.AnimalClasses.Shelter.Companion.SelectedAnimal
import com.example.animalshelter.AnimalClasses.Shelter.Companion.SelectedShelter
import com.example.animalshelter.databinding.FragmentListBinding


class AnimalListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var adapter: AnimalAdapterRV
    private lateinit var app: MyApplication
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        app = (requireActivity().application as MyApplication)
        return binding!!.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recViewMethods()
        binding?.recyclerView?.adapter = adapter
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        updateRecycleView()

    }


    private fun recViewMethods() {
        adapter = AnimalAdapterRV(requireContext(), app.listContainer, onSelect = { animal ->
            SelectedAnimal = animal
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, InputFragment()).commit()
        }, onHold = { animal ->
            AlertDialog.Builder(requireContext())
                .setTitle("DELETE")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes") { _, _ ->
                    val pos = SelectedShelter?.getList()?.indexOf(animal)
                    SelectedShelter?.getList()?.remove(animal)
                    if (pos != null) {
                        adapter.notifyItemRemoved(pos)
                    }
                }
                .setNegativeButton("No", null)
                .show()
        })
    }

    fun updateRecycleView() {
        if (SelectedShelter != null) {
            app.listContainer.clear()

            for (animal in SelectedShelter!!.animals) {
                app.listContainer.add(animal)
            }
        }
        adapter.notifyDataSetChanged()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}