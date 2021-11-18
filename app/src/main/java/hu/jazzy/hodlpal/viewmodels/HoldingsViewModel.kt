package hu.jazzy.hodlpal.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import hu.jazzy.hodlpal.database.CoinTransaction
import hu.jazzy.hodlpal.database.HoldingsDatabase
import hu.jazzy.hodlpal.repository.HoldingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HoldingsViewModel(application: Application) :
    AndroidViewModel(application) {

    val readAllCoinsTransactions: LiveData<List<CoinTransaction>>
    private val repository: HoldingsRepository

    init {
        val dao = HoldingsDatabase.getDataBase(application).dao()
        repository =  HoldingsRepository(dao)
        readAllCoinsTransactions = repository.readAllCoinsTransaction
    }

    fun addCoinTx(coinTransaction: CoinTransaction){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCoinTx(coinTransaction)
        }
    }

    fun getCoinTxById(coinId: String):List<CoinTransaction>{
        var list:List<CoinTransaction> = emptyList()
        viewModelScope.launch(Dispatchers.IO) {
             list =repository.getCoinTxById(coinId)
        }
        return list
    }
}