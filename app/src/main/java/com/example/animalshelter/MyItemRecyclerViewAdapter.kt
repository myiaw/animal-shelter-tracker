package com.example.animalshelter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.example.animalshelter.classes.placeholder.PlaceholderContent.Animal
import com.example.animalshelter.databinding.FragmentAnimalBinding

/**
 * [RecyclerView.Adapter] that can display a [Animal].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val values: List<Animal>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentAnimalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.nameView.text = item.name
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentAnimalBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val nameView: TextView = binding.nameTV
        private val ageView: TextView = binding.ageTV
        private val isFedView: TextView = binding.isAdoptedTV
        private val isWild: TextView = binding.isWildTV
        private val isAdopted: TextView = binding.isAdoptedTV

        override fun toString(): String {
            return super.toString() + " '" + idView.text + "'" + " '" + nameView.text + "'" + " '" + ageView.text + "'" + " '" + isFedView.text + "'" + " '" + isWild.text + "'" + " '" + isAdopted.text + "'"
        }
    }

}