package hu.jazzy.hodlpal.adapter

import hu.jazzy.hodlpal.databinding.CoinHoldingCardLayoutBinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import hu.jazzy.hodlpal.R
import hu.jazzy.hodlpal.database.CoinHolding
import hu.jazzy.hodlpal.database.PersistentCoin
import hu.jazzy.hodlpal.model.Coin
import hu.jazzy.hodlpal.viewmodels.HoldingsViewModel
import java.text.DecimalFormat

class CoinHoldingAdapter(private val holdingsViewModel: HoldingsViewModel) : RecyclerView.Adapter<CoinHoldingAdapter.CoinHoldingViewHolder>() {

    private var list = emptyList<CoinHolding>()
    private val df: DecimalFormat = DecimalFormat("#.####")
    private var coinList = emptyList<Coin>()

    inner class CoinHoldingViewHolder(var binding: CoinHoldingCardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinHoldingViewHolder {

        return CoinHoldingViewHolder(CoinHoldingCardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CoinHoldingViewHolder, position: Int) {
        val coinHolding = list[position]
        holder.binding.cardImage.load(coinHolding.coin.icon){
            placeholder(R.drawable.cryptocurrencies)
        }
        var price  = coinHolding.coin.price
        for (i in coinList){
            if (i.id==coinHolding.coin.id){
                price = i.price
                holdingsViewModel.updateCoinHolding(CoinHolding(coinHolding.id, PersistentCoin(i),0.0))
            }
        }
        holder.binding.coinHoldingAmount.text = df.format(coinHolding.amount).toString()
        holder.binding.coinHoldingPrice.text = (df.format(price*coinHolding.amount).toString()+" $")
        holder.binding.coinHoldingNameTv.text = coinHolding.coin.name
        holder.binding.coinHoldingSymbolTv.text = coinHolding.coin.symbol
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(coinHoldings :List<CoinHolding>){
        this.list = coinHoldings
        notifyDataSetChanged()
    }

    fun setCoinList(coin: List<Coin>){
        this.coinList=coin
        notifyDataSetChanged()
    }
}