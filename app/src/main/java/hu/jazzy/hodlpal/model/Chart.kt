package hu.jazzy.hodlpal.model

import com.google.gson.annotations.SerializedName

data class Chart (

    @SerializedName("chart") val chart : List<List<Double>>
)
