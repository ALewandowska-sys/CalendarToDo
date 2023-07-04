package com.app.calendartodo.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.app.calendartodo.R

class ArtAdapter(var lista: List<Task>) : RecyclerView.Adapter<ArtAdapter.ArtViewHolder> () {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.task,
            parent,
            false
        )
        return ArtViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val item = lista[position]
        holder.task.text = item.description
        holder.task.isChecked = item.done
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    class ArtViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var task: CheckBox = view.findViewById(R.id.task)
    }

}