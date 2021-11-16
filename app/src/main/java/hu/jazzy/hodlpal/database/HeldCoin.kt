package hu.jazzy.hodlpal.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "held_coin_table")
data class HeldCoin(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @Embedded
    var coin: PersistentCoin,
    val purchasePrice: Double,
    var purchaseDate: Date,
    var amount:Double,
)