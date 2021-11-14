package hu.jazzy.hodlpal.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.jazzy.hodlpal.repository.CoinRepository

class CoinsViewModelFactory(private val repository: CoinRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoinsViewModel(repository) as T
    }
}