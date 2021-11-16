package hu.jazzy.hodlpal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.jazzy.hodlpal.model.Coin
import hu.jazzy.hodlpal.model.CoinList
import hu.jazzy.hodlpal.repository.CoinRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CoinsViewModel :ViewModel() {

    private var coinsResponse: MutableLiveData<Response<CoinList>> = MutableLiveData()
    private var coinsSearch: MutableLiveData<ArrayList<Coin>> = MutableLiveData()
    private val repository:CoinRepository = CoinRepository()


    fun getCoinsResponse():LiveData<Response<CoinList>>{
        viewModelScope.launch {
            val coins = repository.getCoins()
            coinsResponse.value = coins
        }
        return coinsResponse
    }


    fun searchCoinResponse(queryString: String) : LiveData<ArrayList<Coin>> {
        queryCoins(queryString)
        return coinsSearch
    }

    private fun queryCoins(queryString: String){
        if (coinsResponse.value!=null){
            coinsSearch.value = ArrayList()
            if(coinsResponse.value!!.isSuccessful){
                if (!coinsResponse.value!!.body()!!.coins.isNullOrEmpty()){//TODO check null
                        coinsResponse.value?.let {
                            if(queryString.isEmpty()){
                                coinsSearch.value!!.addAll(it.body()!!.coins)
                            }
                            for (coin in it.body()!!.coins) {
                                if (coin.symbol.contains(queryString,ignoreCase = true)){
                                    coinsSearch.value!!.add(coin)
                                }
                                else if(coin.name.contains(queryString,ignoreCase = true)) {
                                    coinsSearch.value!!.add(coin)
                                }
                                else if(coin.id.contains(queryString,ignoreCase = true)) {
                                    coinsSearch.value!!.add(coin)
                                }
                            }
                        }

                }
            }
        }

    }



}