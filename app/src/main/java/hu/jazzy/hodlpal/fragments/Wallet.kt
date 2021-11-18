package hu.jazzy.hodlpal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import hu.jazzy.hodlpal.adapter.CoinTransactionAdapter
import hu.jazzy.hodlpal.databinding.FragmentWalletBinding
import hu.jazzy.hodlpal.viewmodels.HoldingsViewModel

class Wallet : Fragment() {

    private lateinit var binding : FragmentWalletBinding
    private val holdingsViewModel:HoldingsViewModel by activityViewModels()
    private lateinit var transactionAdapter: CoinTransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View{
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentWalletBinding.inflate(layoutInflater)


        initRecyclerView()
        holdingsViewModel.readAllCoinsTransactions.observe(viewLifecycleOwner){
            if (it!=null){
                transactionAdapter.setData(it)
            }
        }

        return binding.root
    }

    private fun initRecyclerView() {
        transactionAdapter = CoinTransactionAdapter()
        binding.walletList.layoutManager = LinearLayoutManager(context)
        binding.walletList.adapter = transactionAdapter
    }
}