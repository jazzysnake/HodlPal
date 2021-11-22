package hu.jazzy.hodlpal.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import hu.jazzy.hodlpal.R
import hu.jazzy.hodlpal.adapter.CoinHoldingAdapter
import hu.jazzy.hodlpal.database.CoinHolding
import hu.jazzy.hodlpal.database.PersistentCoin
import hu.jazzy.hodlpal.databinding.FragmentPortfolioBinding
import hu.jazzy.hodlpal.model.Fiat
import hu.jazzy.hodlpal.viewmodels.CoinsViewModel
import hu.jazzy.hodlpal.viewmodels.HoldingsViewModel
import java.text.DecimalFormat

class Portfolio : Fragment() {

    private lateinit var binding : FragmentPortfolioBinding
    private lateinit var adapter: CoinHoldingAdapter
    private lateinit var chosenFiat: Fiat
    private val coinsViewModel: CoinsViewModel by activityViewModels()
    private val holdingsViewModel:HoldingsViewModel by activityViewModels()
    private val df: DecimalFormat = DecimalFormat("#.##")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View{
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentPortfolioBinding.inflate(layoutInflater)
        initPieChartStyle()
        initRecyclerView()


        coinsViewModel.getChosenFiat().observe(viewLifecycleOwner,{
            chosenFiat=it
            adapter.setFiat(chosenFiat)
        })

        holdingsViewModel.readAllCoinHoldings().observe(viewLifecycleOwner,{
            adapter.setData(it)
            initPieChartData(it)
            var evaluation = 0.0
            for (i in it){
                val iValue =i.amount*i.coin.price*chosenFiat.rate
                evaluation+=iValue
            }

            binding.evaluationTv.text = (resources.getString(R.string.total_evaluation)+" " +df.format(evaluation).toString() + chosenFiat.symbol)
        })
        coinsViewModel.getCoinsResponse().observe(viewLifecycleOwner,{
            response -> if (response!=null){
            if (response.isSuccessful){
                if (response.body()!=null){
                    response.body()?.let {
                        val adapterdata =adapter.getData()
                        if (adapterdata.isNotEmpty()){
                            for (heldCoin in adapterdata){
                                for (coin in it.coins){
                                    if (heldCoin.coin.name==coin.name){
                                        holdingsViewModel.updateCoinHolding(CoinHolding(heldCoin.id,
                                            PersistentCoin(coin),0.0
                                        ))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else{
            Toast.makeText(context,"Check network connection!", Toast.LENGTH_LONG).show()
        }
        })
        return binding.root
    }

    private fun initPieChartData(coinList:List<CoinHolding>){
        val pieEntries = ArrayList<PieEntry>()
        for (i in coinList){
            val iValue =i.amount*i.coin.price
            val pieEntry = PieEntry(iValue.toFloat(),i.coin.symbol)
            pieEntries.add(pieEntry)
        }
        val dataSet = PieDataSet(pieEntries,"")
        dataSet.valueTextSize = 20f
        val percentFormatter = PercentFormatter(binding.portfolioPie)
        percentFormatter.mFormat = df
        val textColors = arrayListOf<Int>()
        for (i in coinList)
            textColors.add(binding.evaluationTv.currentTextColor)

        dataSet.setValueTextColors(textColors)
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        val data = PieData(dataSet)
        data.setValueFormatter(percentFormatter)
        binding.portfolioPie.data = data
        binding.portfolioPie.notifyDataSetChanged()
        binding.portfolioPie.invalidate()
    }

    private fun initPieChartStyle(){
        binding.portfolioPie.isDrawHoleEnabled = false
        binding.portfolioPie.setEntryLabelColor(binding.evaluationTv.currentTextColor)
        binding.portfolioPie.setUsePercentValues(true)
        binding.portfolioPie.minAngleForSlices = 9.0f
        binding.portfolioPie.setDrawRoundedSlices(true)
        binding.portfolioPie.setEntryLabelTextSize(15.0f)
        binding.portfolioPie.description.isEnabled = false
        binding.portfolioPie.legend.isEnabled = false
        binding.portfolioPie.animateY(1500,Easing.EaseInOutQuad)
        binding.portfolioPie.notifyDataSetChanged()
        binding.portfolioPie.invalidate()

    }


    private fun initRecyclerView() {
        adapter = CoinHoldingAdapter()
        binding.holdingsRecycler.layoutManager = LinearLayoutManager(context)
        binding.holdingsRecycler.adapter = adapter
    }
}