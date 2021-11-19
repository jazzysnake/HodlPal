package hu.jazzy.hodlpal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import hu.jazzy.hodlpal.R
import hu.jazzy.hodlpal.databinding.FragmentCoinDetaisBinding
import hu.jazzy.hodlpal.viewmodels.CoinsViewModel
import hu.jazzy.hodlpal.viewmodels.HoldingsViewModel
import java.text.DecimalFormat


class CoinDetails : Fragment() {
    private lateinit var binding: FragmentCoinDetaisBinding
    private val args: CoinDetailsArgs by navArgs()
    private val coinsViewModel: CoinsViewModel by activityViewModels()
    private lateinit var holdingsViewModel: HoldingsViewModel// by activityViewModels()
    private val longFormat: DecimalFormat = DecimalFormat("#.#######")
    private val shortFormat: DecimalFormat = DecimalFormat("#.##")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCoinDetaisBinding.inflate(layoutInflater)
        val index: Int = args.coinRank-1
        holdingsViewModel = ViewModelProvider(this)[HoldingsViewModel::class.java]
        coinsViewModel.getCoinsResponse().observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    val coin = response.body()!!.coins[index]
                    binding.coinNameTv.text = coin.name
                    binding.imageView.load(coin.icon)
                    fillTextView(binding.currentPriceTv,R.string.current_price," "+longFormat.format(coin.price)+"$")
                    fillTextView(binding.availableSupplyTv,R.string.available_supply, " "+shortFormat.format(coin.availableSupply))
                    fillTextView(binding.totalSupplyTv,R.string.total_supply, " "+shortFormat.format(coin.totalSupply))
                    fillTextView(binding.marketCapTv,R.string.market_cap, " "+shortFormat.format(coin.marketCap)+"$")
                    fillTextView(binding.rankTv,R.string.rank, " #"+coin.rank.toString())
                    setClickListener()
                }
            } else {
                Toast.makeText(context, response.code().toString(), Toast.LENGTH_LONG).show()
            }
        })

        return binding.root
    }

    private fun setClickListener(){
        binding.buyButton.setOnClickListener {
            val action = CoinDetailsDirections.actionCoinDetailsToAddCoin(args.coinRank)
            findNavController().navigate(action)
        }
    }

    private fun fillTextView(tv:TextView,strResId:Int,data:String){
        tv.text = (resources.getString(strResId)+data)
    }
}