package com.dunyamin.naya.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dunyamin.naya.databinding.WordItemBinding
import com.dunyamin.naya.model.MyWords

class WordAdapter : RecyclerView.Adapter<WordAdapter.ItemViewHolder>() {
    private var wordList = mutableListOf<MyWords>()
    private var actionUpdate: ((MyWords) -> Unit)? = null
    private var actionDelete: ((MyWords) -> Unit)? = null

    class ItemViewHolder(val binding: WordItemBinding) : RecyclerView.ViewHolder(binding.root) {

        //val binding = ListItemBinding.bind(itemView)

        /*
        val myEnglishWord: TextView = itemView.findViewById(R.id.english_word)
        val myTurkishWord: TextView = itemView.findViewById(R.id.turkish_word)
        val actionUpdate: ImageView = itemView.findViewById(R.id.action_update)
        val actionDelete: ImageView = itemView.findViewById(R.id.action_delete
         */
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
        //return ItemViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val newList = wordList[position]
        holder.binding.englishWord.text = newList.engResource
        holder.binding.turkishWord.text = newList.turResource

        //binding.myEnglishWord.text = newList.engResource
        //holder.myTurkishWord.text = newList.turResource

        holder.binding.actionUpdate.setOnClickListener { actionUpdate?.invoke(newList) }
        holder.binding.actionDelete.setOnClickListener { actionDelete?.invoke(newList) }
    }

    fun setData(data : List<MyWords>) {
        wordList.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    fun setOnActionEditListener(callback: (MyWords) -> Unit) {
        this.actionUpdate = callback
    }

    fun setOnActionDeleteListener(callback: (MyWords) -> Unit) {
        this.actionDelete = callback
    }
}