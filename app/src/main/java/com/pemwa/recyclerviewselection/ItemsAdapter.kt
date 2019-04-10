package com.pemwa.recyclerviewselection

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    var list: List<Int> = arrayListOf()
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: ItemsAdapter.ViewHolder, position: Int) {
        val number = list[position]
        tracker?.let {
            holder.bind(number, it.isSelected(position.toLong()))
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var text: TextView = view.findViewById(R.id.text)

        fun bind(value: Int, isActivated: Boolean = false) {
            text.text = value.toString()
            itemView.isActivated = isActivated
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }

    }
}