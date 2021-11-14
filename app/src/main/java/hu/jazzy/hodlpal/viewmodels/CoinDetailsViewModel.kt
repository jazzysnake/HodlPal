package hu.jazzy.hodlpal.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.jazzy.hodlpal.repository.CoinRepository
import kotlinx.coroutines.launch
import retrofit2.Call

class CoinDetailsViewModel(private val coinRepository: CoinRepository,
                           private val coinId: String
) :ViewModel() {

//    private var coinDetails : MutableLiveData<Call<CoinData>> = MutableLiveData()
//
//    fun getCoinDataResponse(): MutableLiveData<Call<CoinData>>{
//        viewModelScope.launch {
//            val details = coinRepository.getCoinData(coinId)
//            Log.d("NETWORK",details.toString())
//            coinDetails.value=details
//        }
//        return coinDetails
//    }
}