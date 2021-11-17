package hu.jazzy.hodlpal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import hu.jazzy.hodlpal.adapter.HeldCoinAdapter
import hu.jazzy.hodlpal.databinding.FragmentWalletBinding
import hu.jazzy.hodlpal.viewmodels.HoldingsViewModel

class Wallet : Fragment() {

    private lateinit var binding : FragmentWalletBinding
    private val holdingsViewModel:HoldingsViewModel by activityViewModels()
    private lateinit var adapter: HeldCoinAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View{
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentWalletBinding.inflate(layoutInflater)


        initRecyclerView()
        holdingsViewModel.readAllHeldCoins.observe(viewLifecycleOwner){
            if (it!=null){
                adapter.setData(it)
            }
        }

        return binding.root
    }

    private fun initRecyclerView() {
        adapter = HeldCoinAdapter()
        binding.walletList.layoutManager = LinearLayoutManager(context)
        binding.walletList.adapter = adapter
    }
}