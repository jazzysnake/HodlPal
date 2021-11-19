package hu.jazzy.hodlpal.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hu.jazzy.hodlpal.database.CoinHolding
import hu.jazzy.hodlpal.database.CoinTransaction
import hu.jazzy.hodlpal.database.HoldingsDatabase
import hu.jazzy.hodlpal.database.PersistentCoin
import hu.jazzy.hodlpal.model.Coin
import hu.jazzy.hodlpal.repository.HoldingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HoldingsViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository: HoldingsRepository

    init {
        val dao = HoldingsDatabase.getDataBase(application).dao()
        repository =  HoldingsRepository(dao)
    }
    fun readAllCoinsTransactions(): LiveData<List<CoinTransaction>>{
            return repository.readAllCoinsTransaction()
    }

    fun addCoinTx(coinTransaction: CoinTransaction){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCoinTx(coinTransaction)
        }
    }

    fun getCoinTxById(coinId: String):List<CoinTransaction>{
         return repository.getCoinTxById(coinId)
    }

    fun getCoinHoldingByCoinID(coinId: String): LiveData<CoinHolding> {
        var res :LiveData<CoinHolding> = MutableLiveData()
        viewModelScope.launch(Dispatchers.IO) {
            res = repository.getCoinHoldingByCoinID(coinId)
        }
        return res
    }

    fun addCoinHolding(coinHolding: CoinHolding):Long{
        var idk :Long = -100
        viewModelScope.launch(Dispatchers.IO) {
            idk = repository.addCoinHolding(coinHolding)
        }
        return idk
    }

    fun readAllCoinHoldings():LiveData<List<CoinHolding>>{
        var list :LiveData<List<CoinHolding>> = MutableLiveData()
            list = repository.readAllCoinHoldings()

        return list
    }

    fun updateCoinHolding(coinHolding: CoinHolding):Int {
        var res:Int = -1
        viewModelScope.launch(Dispatchers.IO) {
            res = repository.updateCoinHolding(coinHolding)
        }
        return res
    }
}