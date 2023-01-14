package com.dunyamin.naya.view

import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.dunyamin.naya.R
import com.dunyamin.naya.databinding.ActivityAddWordBinding
import com.dunyamin.naya.model.AppDatabase
import com.dunyamin.naya.model.MyWords
import kotlinx.coroutines.launch

class AddWordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWordBinding
    private var myWords: MyWords? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myWords = if (VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("Data", MyWords::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<MyWords>("Data")
        }
            //intent.getParcelableExtra("Data") as MyWords?

        if (myWords == null) binding.addOrUpdateButton.setText(R.string.add_word)
        else {
            binding.addOrUpdateButton.setText(R.string.update)
            binding.addEnglish.setText(myWords?.engResource.toString())
            binding.addTurkish.setText(myWords?.turResource.toString())
        }
    }

    fun addNewWord(view: View) {
        val engResource = binding.addEnglish.text.toString()
        val turResource = binding.addTurkish.text.toString()

        lifecycleScope.launch {
            if (myWords == null) {
                val myNewWords = MyWords(engResource = engResource, turResource = turResource)
                AppDatabase(this@AddWordActivity).getMyWordsDao().addWord(myNewWords)
                finish()
            } else {
                val myUpdatedWord = MyWords(engResource, turResource)
                myUpdatedWord.id = myWords?.id?: 0
                AppDatabase(this@AddWordActivity).getMyWordsDao().updateWords(myUpdatedWord)
                finish()
            }
        }
    }
}