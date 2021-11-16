package hu.jazzy.hodlpal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import hu.jazzy.hodlpal.R
import hu.jazzy.hodlpal.databinding.CardLayoutBinding
import hu.jazzy.hodlpal.fragments.CoinsDirections
import hu.jazzy.hodlpal.model.Coin

class CoinAdapter : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {

    private var list = emptyList<Coin>()

    inner class CoinViewHolder(var binding: CardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {

        return CoinViewHolder(CardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.binding.cardTitle.text = list[position].name
        holder.binding.rankText.text = ("#"+list[position].rank.toString())
        holder.binding.cardImage.load(list[position].icon){
            placeholder(R.drawable.cryptocurrencies)
        }
        holder.binding.cardView.setOnClickListener {
            val action = CoinsDirections.actionCoinsToCoinDetails(list[position].rank)
            holder.binding.cardView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(coins :List<Coin>){
        this.list = coins
        notifyDataSetChanged()
    }
}