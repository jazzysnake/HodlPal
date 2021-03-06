package hu.jazzy.hodlpal.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.jazzy.hodlpal.model.Chart
import hu.jazzy.hodlpal.repository.CoinRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CoinDetailsViewModel(private val coinRepository: CoinRepository
) :ViewModel() {

    private var coinChart :MutableLiveData<Response<Chart>> = MutableLiveData()

    fun getCoinChart(period:String,coinId: String):MutableLiveData<Response<Chart>>{
        viewModelScope.launch {
            coinChart.value = coinRepository.getChart(period, coinId)
        }
        return coinChart
    }
}