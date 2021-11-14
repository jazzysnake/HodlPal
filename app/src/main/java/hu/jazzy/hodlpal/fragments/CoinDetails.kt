package hu.jazzy.hodlpal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import hu.jazzy.hodlpal.databinding.FragmentCoinDetaisBinding
import hu.jazzy.hodlpal.repository.CoinRepository
import hu.jazzy.hodlpal.viewmodels.CoinDetailsViewModel
import hu.jazzy.hodlpal.viewmodels.CoinDetailsViewModelFactory


class CoinDetails : Fragment() {
    private lateinit var binding: FragmentCoinDetaisBinding
    private val args: CoinDetailsArgs by navArgs()
    private lateinit var viewModel: CoinDetailsViewModel
    private lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCoinDetaisBinding.inflate(layoutInflater)
        id = args.coinId

        val repository = CoinRepository()
        val viewModelFactory = CoinDetailsViewModelFactory(repository,id)
        viewModel = ViewModelProvider(this,viewModelFactory).get(CoinDetailsViewModel::class.java)

//        viewModel.getCoinDataResponse().observe(viewLifecycleOwner, Observer {
//                response -> if (response.isExecuted){
//            response.()?.let { binding.textView.text= response.code().toString() }
//        } else{
//            Toast.makeText(context,response.code().toString(), Toast.LENGTH_LONG).show()
//        }
//        })

//        binding.textView.text= viewModel.getCoinDataResponse().value.toString()

        return binding.root
    }
}