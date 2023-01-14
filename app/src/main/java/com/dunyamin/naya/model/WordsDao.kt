package com.dunyamin.naya.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordsDao {

    @Insert
    suspend fun addWord(myWords: MyWords)

    @Query("SELECT * FROM mywords ORDER BY id DESC")
    suspend fun getAllWords() : List<MyWords>

    @Update
    suspend fun updateWords(myWords: MyWords)

    @Delete
    suspend fun deleteWords(myWords: MyWords)
}