package com.example.animalshelter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animalshelter.AnimalClasses.Animal
import com.example.animalshelter.databinding.FragmentRecycleViewRowBinding
import com.squareup.picasso.Picasso


class AnimalAdapterRV(
    private val context: Context,
    private var animalList: MutableList<Animal>,
    private val onSelect: (Animal?) -> Unit,
    private val onHold: (Animal?) -> Unit
) :
    RecyclerView.Adapter<AnimalAdapterRV.AnimalItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalItemViewHolder {
        val binding =
            FragmentRecycleViewRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return AnimalItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalItemViewHolder, position: Int) {
        val catItem = animalList[position]
        holder.bind(catItem, onSelect, onHold)
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

    class AnimalItemViewHolder(RecycleViewRowBinding: FragmentRecycleViewRowBinding) :
        RecyclerView.ViewHolder(RecycleViewRowBinding.root) {
        private val binding = RecycleViewRowBinding
        fun bind(animal: Animal?, onSelect: (Animal?) -> Unit, onHold: (Animal?) -> Unit) {
            binding.TVName.text = animal!!.name
            binding.TVAge.text = animal.age.toString()
            binding.TVIsWild.text = animal.isWild.toString()
            binding.TVType.text = animal.type
            binding.TVBreed.text = animal.breed
            Picasso.get()
                .load("https://media.istockphoto.com/id/931785704/vector/paw_print.jpg?s=612x612&w=0&k=20&c=CXBPHlf7XHdJiiOULJrI9nGZjVNAj7cqnkM_eDyDdCU=")
                .into(binding.imageView2)
            binding.root.setOnClickListener {
                onSelect(animal)
            }
            binding.root.setOnLongClickListener {
                onHold(animal)
                true
            }
        }
    }
}