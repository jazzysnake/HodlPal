package hu.jazzy.hodlpal.database

import androidx.room.*

@Entity(tableName = "coin_holdings_table", indices = [Index(value = ["coinID"], unique = true)])
data class CoinHolding (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @Embedded
    var coin: PersistentCoin,
    var amount:Double,
)