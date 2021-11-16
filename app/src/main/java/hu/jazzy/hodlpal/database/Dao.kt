package hu.jazzy.hodlpal.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHeldCoin(heldCoin: HeldCoin)

    @Query("SELECT * FROM held_coin_table ORDER BY amount DESC")
    fun readAllHeldCoins():LiveData<List<HeldCoin>>
}