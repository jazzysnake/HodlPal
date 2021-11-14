package hu.jazzy.hodlpal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.jazzy.hodlpal.databinding.FragmentPortfolioBinding
import hu.jazzy.hodlpal.databinding.FragmentWalletBinding

class Wallet : Fragment() {

    private lateinit var binding : FragmentWalletBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View{
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentWalletBinding.inflate(layoutInflater)
        return binding.root
    }

}