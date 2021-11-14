package hu.jazzy.hodlpal.model

import com.google.gson.annotations.SerializedName

data class CoinList (

    @SerializedName("coins") val coins : List<Coin>
)