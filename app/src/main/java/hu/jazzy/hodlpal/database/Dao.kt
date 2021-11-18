package hu.jazzy.hodlpal.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCoinTx(coinTransaction: CoinTransaction)

    @Query("SELECT * FROM coin_transaction_table ORDER BY txDate DESC")
    fun readAllCoinTxs():LiveData<List<CoinTransaction>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCoinTx(coinTransaction: CoinTransaction)

    @Query("SELECT * FROM coin_transaction_table WHERE coinID = :coinid")
    suspend fun getCoinTxById(coinid:String):List<CoinTransaction>

}