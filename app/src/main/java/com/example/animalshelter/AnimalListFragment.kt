package com.example.animalshelter

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animalshelter.AnimalClasses.Shelter
import com.example.animalshelter.databinding.FragmentListBinding


class AnimalListFragment : Fragment(R.layout.fragment_list) {
    private lateinit var adapter: AnimalAdapterRV
    private lateinit var builder: AlertDialog.Builder
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding!!.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recViewMethods()
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter

        if (Shelter.SelectedAnimal != null) {
            adapter.notifyItemChanged(Shelter.animals.indexOf(Shelter.SelectedAnimal))
            Shelter.SelectedAnimal = null
        }
    }

    private fun recViewMethods() {
        adapter = AnimalAdapterRV(requireContext(), Shelter.animals, onSelect = { animal ->
            Shelter.SelectedAnimal = animal
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer, InputFragment())
                ?.commit()
        }, onHold = { animal ->
            builder = AlertDialog.Builder(requireContext())
            builder.setTitle("DELETE")
            builder.setMessage("Are you sure you want to delete this item?")
            builder.setCancelable(true)
            builder.setPositiveButton("Yes") { _, _ ->
                val pos = Shelter.animals.indexOf(animal)
                Shelter.animals.remove(animal)
                adapter.notifyItemRemoved(pos)
            }
            builder.setNegativeButton("No") { _, _ ->
                builder.setCancelable(true)
            }
            builder.show()
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}