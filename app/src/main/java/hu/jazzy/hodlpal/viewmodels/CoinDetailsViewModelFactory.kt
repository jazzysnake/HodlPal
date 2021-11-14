package hu.jazzy.hodlpal.viewmodels

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.jazzy.hodlpal.repository.CoinRepository

class CoinDetailsViewModelFactory(private val coinRepository: CoinRepository,
                                  private val coinId: String
                                  ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoinDetailsViewModel(coinRepository,coinId) as T
    }
}