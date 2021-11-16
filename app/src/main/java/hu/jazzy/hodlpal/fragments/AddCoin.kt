package hu.jazzy.hodlpal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import hu.jazzy.hodlpal.database.HeldCoin
import hu.jazzy.hodlpal.database.PersistentCoin
import hu.jazzy.hodlpal.databinding.FragmentAddCoinBinding
import hu.jazzy.hodlpal.model.Coin
import hu.jazzy.hodlpal.viewmodels.CoinsViewModel
import hu.jazzy.hodlpal.viewmodels.HoldingsViewModel
import java.util.*


class AddCoin : Fragment() {

    private lateinit var binding: FragmentAddCoinBinding
    private val args:AddCoinArgs by navArgs()
    private val coinsViewModel: CoinsViewModel by activityViewModels()
    private val holdingsViewModel: HoldingsViewModel by activityViewModels()
    private lateinit var coin:Coin
    private var heldCoinsSize :Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCoinBinding.inflate(layoutInflater)
        val index = args.coinRank-1
        coinsViewModel.getCoinsResponse().observe(viewLifecycleOwner){
            response -> if (response.isSuccessful){
            coin=response.body()!!.coins[index]
            binding.coinNameTv.text=coin.name
            binding.imageView.load(coin.icon)
            var priceInput  = EditText(requireContext())
            priceInput.setText(coin.price.toString())
            binding.addCoinInputPrice.text=priceInput.text
        }
        }

        holdingsViewModel.readAllHeldCoins.observe(viewLifecycleOwner){
            if (it!=null){
                var tmp = heldCoinsSize
                heldCoinsSize=it.size
                if (tmp<heldCoinsSize)
                    Toast.makeText(requireContext(),"Coin successfully added",Toast.LENGTH_SHORT).show()

            }
        }
        setClickListener()

        return binding.root
    }

    private fun setClickListener(){
        binding.btnAddToWallet.setOnClickListener {
            val inputPrice = binding.addCoinInputPrice.text
            val price = inputPrice!!.split(" ")[0].toDouble()
            val inputAmount = binding.addCoinInputAmount.text
            val amount = inputAmount!!.split(" ")[0].toDouble()
            val heldCoin = HeldCoin(0, PersistentCoin(coin),price,Calendar.getInstance().time,amount)
            holdingsViewModel.addHeldCoin(heldCoin)
        }
    }

}