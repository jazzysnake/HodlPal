package hu.jazzy.hodlpal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import hu.jazzy.hodlpal.database.CoinTransaction
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
            val priceInput  = EditText(requireContext())
            priceInput.setText(coin.price.toString())
            binding.addCoinInputPrice.text=priceInput.text
        }
        }

        setClickListener()

        return binding.root
    }

    private fun setClickListener(){
        binding.btnAddToWallet.setOnClickListener {
            val inputPrice = binding.addCoinInputPrice.text
            val price = inputPrice!!.split(" ")[0].toDoubleOrNull()
            val inputAmount = binding.addCoinInputAmount.text
            val amount = inputAmount!!.split(" ")[0].toDoubleOrNull()
            if (amount==null||price==null){
                Toast.makeText(requireContext(),"Please input valid values",Toast.LENGTH_SHORT).show()
            }
            else{
//                val currentList = holdingsViewModel.getCoinTxById(coinId = coin.id)
//                if (currentList.isEmpty()){
                    val transaction = CoinTransaction(0, PersistentCoin(coin),price,Calendar.getInstance().time,amount)
                    holdingsViewModel.addCoinTx(transaction)
                    Toast.makeText(requireContext(),"Coin successfully added",Toast.LENGTH_SHORT).show()
//                }
//                else{
//                    //TODO make new table where coin id is unique, keep th existing one, rename it to history smth,
//                    //TODO adding a coin there which has already been purchased should result in updating the amount with the sum,
//                    //TODO that table is represented on the portfolio fragment
//                }


                val action = AddCoinDirections.actionAddCoinToCoinDetails(coinRank = coin.rank)
                findNavController().navigate(action)
            }
        }
    }

}