package hu.jazzy.hodlpal.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import hu.jazzy.hodlpal.database.HeldCoin
import hu.jazzy.hodlpal.database.HoldingsDatabase
import hu.jazzy.hodlpal.repository.HoldingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HoldingsViewModel(application: Application) :
    AndroidViewModel(application) {

    val readAllHeldCoins: LiveData<List<HeldCoin>>
    private val repository: HoldingsRepository

    init {
        val dao = HoldingsDatabase.getDataBase(application).dao()
        repository =  HoldingsRepository(dao)
        readAllHeldCoins = repository.readAllHeldCoins
    }

    fun addHeldCoin(heldCoin: HeldCoin){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHeldCoin(heldCoin)
        }
    }

    fun getHeldCoinByCoinId(coinId: String):List<HeldCoin>{
        var list:List<HeldCoin> = emptyList()
        viewModelScope.launch(Dispatchers.IO) {
             list =repository.getHeldCoinByCoinId(coinId)
        }
        return list
    }
}