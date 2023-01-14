package com.dunyamin.naya.view

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dunyamin.naya.databinding.ActivityMainBinding
import com.dunyamin.naya.model.AppDatabase
import com.dunyamin.naya.model.MyWords
import com.dunyamin.naya.view.adapter.WordAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var recyclerAdapter: WordAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //wordList = ArrayList()

        //recyclerView = findViewById(R.id.item_recycler)
        //recyclerAdapter = ItemAdapter(this, wordList)

        //recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter = recyclerAdapter
    }

    private fun setAdapter(list: List<MyWords>) {
        recyclerAdapter?.setData(list)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val wordList = AppDatabase(this@MainActivity).getMyWordsDao().getAllWords()

            recyclerAdapter = WordAdapter()
            binding.itemRecycler.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = recyclerAdapter
                setAdapter(wordList)

                recyclerAdapter?.setOnActionEditListener {
                    val intent = Intent(this@MainActivity, AddWordActivity::class.java)
                    intent.putExtra("Data", it as Parcelable)
                    startActivity(intent)
                }

                recyclerAdapter?.setOnActionDeleteListener {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setMessage("Are you sure you want to delete?")
                    builder.setPositiveButton("Yes") { p0, _ ->
                        lifecycleScope.launch {
                            AppDatabase(this@MainActivity).getMyWordsDao().deleteWords(it)
                            val list = AppDatabase(this@MainActivity).getMyWordsDao().getAllWords()
                            setAdapter(list)
                        }
                        p0.dismiss()
                    }
                    builder.setNegativeButton("No") { p0, _ ->
                        p0.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }
    }

    fun gotoAddNewWord(view: View) {
        val intent = Intent(this, AddWordActivity::class.java)
        startActivity(intent)
    }

    fun gotoAddNewUnit(view: View) {
        val intent = Intent(this, AddUnitActivity::class.java)
        startActivity(intent)
    }
}