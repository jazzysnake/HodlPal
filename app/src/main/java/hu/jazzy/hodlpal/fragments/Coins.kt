package hu.jazzy.hodlpal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import hu.jazzy.hodlpal.adapter.CoinAdapter
import hu.jazzy.hodlpal.databinding.FragmentCoinsBinding
import hu.jazzy.hodlpal.viewmodels.CoinsViewModel


class Coins : Fragment(),SearchView.OnQueryTextListener {

    private lateinit var binding : FragmentCoinsBinding
    private lateinit var adapter: CoinAdapter

    private val viewModel: CoinsViewModel by activityViewModels()
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View{
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCoinsBinding.inflate(layoutInflater)
        searchView=binding.searchView
        searchView.setOnQueryTextListener(this)
        initRecyclerView()
        viewModel.getCoinsResponse().observe(viewLifecycleOwner, Observer {
            response -> if (response!=null){
            if (response.isSuccessful){
                if (response.body()!=null){
                    response.body()?.let { adapter.setData(it.coins) }
                }
            }
        } else{
            Toast.makeText(context,"Check network connection!",Toast.LENGTH_LONG).show()
        }
        })
        return binding.root
    }

    private fun initRecyclerView() {
        adapter = CoinAdapter()
        binding.coinRecycler.layoutManager = LinearLayoutManager(context)
        binding.coinRecycler.adapter = adapter
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText!=null){
            searchCoins(newText)
        }
        return true
    }

    private fun searchCoins(query: String){
        viewModel.searchCoinResponse(query).observe(viewLifecycleOwner, Observer {
            list -> adapter.setData(list)
        })
    }
}