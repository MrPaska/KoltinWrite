package com.example.writedata

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list.view.*

class Adapter(private val context: Context, private val items:ArrayList<Asmenys>): RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.activity_read_data,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val item = items[position]
        holder.vardas.text = item.vardas
        println("vardas1 ${item.vardas}")
        holder.amzius.text = item.amzius.toString()
        holder.lytis.text = item.lytis
        holder.pomegiai.text = item.pomegiai.toString()
    }
    override fun getItemCount(): Int {
        return items.size
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val vardas = view.vardas
        val amzius = view.amzius
        val lytis = view.lytis
        val pomegiai = view.pomegiai
    }
}