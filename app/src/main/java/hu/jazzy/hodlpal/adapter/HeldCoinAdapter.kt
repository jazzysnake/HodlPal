package hu.jazzy.hodlpal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import hu.jazzy.hodlpal.R
import hu.jazzy.hodlpal.database.HeldCoin
import hu.jazzy.hodlpal.databinding.CoinCardLayoutBinding
import hu.jazzy.hodlpal.databinding.HeldcoinCardLayoutBinding
import hu.jazzy.hodlpal.fragments.CoinsDirections
import hu.jazzy.hodlpal.model.Coin
import java.text.DecimalFormat
import java.text.Format

class HeldCoinAdapter : RecyclerView.Adapter<HeldCoinAdapter.HeldCoinViewHolder>() {

    private var list = emptyList<HeldCoin>()
    private val df: DecimalFormat = DecimalFormat("#.####")

    inner class HeldCoinViewHolder(var binding: HeldcoinCardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeldCoinViewHolder {

        return HeldCoinViewHolder(HeldcoinCardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HeldCoinViewHolder, position: Int) {
        val heldCoin = list[position]
        holder.binding.cardImage.load(heldCoin.coin.icon){
                        placeholder(R.drawable.cryptocurrencies)
        }
        holder.binding.heldCoinAmount.text = df.format(heldCoin.amount).toString()
        holder.binding.heldCoinPrice.text = (df.format(heldCoin.purchasePrice).toString()+" $")
        holder.binding.heldCoinNameTv.text = heldCoin.coin.name
        holder.binding.heldCoinSymbolTv.text = heldCoin.coin.symbol
//        holder.binding.cardTitle.text = list[position].name
//        holder.binding.rankText.text = ("#"+list[position].rank.toString())
//        holder.binding.cardImage.load(list[position].icon){
//            placeholder(R.drawable.cryptocurrencies)
//        }
//        holder.binding.coinCardView.setOnClickListener {
//            val action = CoinsDirections.actionCoinsToCoinDetails(list[position].rank)
//            holder.binding.coinCardView.findNavController().navigate(action)
//        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(coins :List<HeldCoin>){
        this.list = coins
        notifyDataSetChanged()
    }
}