package com.numq.template.presentation.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.numq.template.R
import com.numq.template.core.model.Card

class CardsAdapter : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    private var collection = mutableListOf<Card>()

    var onClick: ((View, Card) -> Unit)? = null
    var onLongClick: ((View, Card) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_card, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(collection[position]) {
                holder.cardFront.text = frontText
                holder.cardBack.text = backText

                itemView.setOnClickListener {
                    onClick?.invoke(it, collection[position])
                }

                itemView.setOnLongClickListener {
                    onLongClick?.invoke(it, collection[position])
                    return@setOnLongClickListener true
                }
            }
        }
    }

    override fun getItemCount() = collection.size

    fun updateData(data: List<Card>) {
        collection = data as MutableList<Card>
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val cardFront = itemView.findViewById<TextView>(R.id.cardFront)
        internal val cardBack = itemView.findViewById<TextView>(R.id.cardBack)
    }

}