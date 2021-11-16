package hu.jazzy.hodlpal.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import hu.jazzy.hodlpal.R
import hu.jazzy.hodlpal.databinding.FragmentCoinDetaisBinding
import hu.jazzy.hodlpal.model.Coin
import hu.jazzy.hodlpal.database.HeldCoin
import hu.jazzy.hodlpal.database.PersistentCoin
import hu.jazzy.hodlpal.viewmodels.CoinsViewModel
import hu.jazzy.hodlpal.viewmodels.HoldingsViewModel
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*


class CoinDetails : Fragment() {
    private lateinit var binding: FragmentCoinDetaisBinding
    private val args: CoinDetailsArgs by navArgs()
    private val coinsViewModel: CoinsViewModel by activityViewModels()
    private lateinit var holdingsViewModel: HoldingsViewModel// by activityViewModels()
    private val longFormat: DecimalFormat = DecimalFormat("#.#######")
    private val shortFormat: DecimalFormat = DecimalFormat("#.##")
    private val calendar: Calendar = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCoinDetaisBinding.inflate(layoutInflater)
        val index: Int = args.coinIndex
        holdingsViewModel = ViewModelProvider(this).get(HoldingsViewModel::class.java)
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
                    setClickListener(coin)
                }
            } else {
                Toast.makeText(context, response.code().toString(), Toast.LENGTH_LONG).show()
            }
        })

        holdingsViewModel.readAllHeldCoins.observe(viewLifecycleOwner,{
            for (item in it){
                Log.d("COINADDED",item.coin.name)
            }
        })
        return binding.root
    }

    private fun setClickListener(coin: Coin){
        binding.buyButton.setOnClickListener {
            LocalDateTime.now()
            val heldCoin = HeldCoin(0, PersistentCoin(coin),coin.price,
                calendar.time,
                1.0)
            holdingsViewModel.addHeldCoin(heldCoin)
        }
    }

    private fun fillTextView(tv:TextView,strResId:Int,data:String){
        tv.text = (resources.getString(strResId)+data)
    }
}