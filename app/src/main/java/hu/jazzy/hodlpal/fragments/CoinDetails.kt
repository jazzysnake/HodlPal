package hu.jazzy.hodlpal.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import hu.jazzy.hodlpal.R
import hu.jazzy.hodlpal.databinding.FragmentCoinDetaisBinding
import hu.jazzy.hodlpal.formatters.XAxisDateFormatter
import hu.jazzy.hodlpal.formatters.YAxisLogFormatter
import hu.jazzy.hodlpal.model.Chart
import hu.jazzy.hodlpal.model.Fiat
import hu.jazzy.hodlpal.repository.CoinRepository
import hu.jazzy.hodlpal.viewmodels.CoinDetailsViewModel
import hu.jazzy.hodlpal.viewmodels.CoinsViewModel
import hu.jazzy.hodlpal.viewmodels.HoldingsViewModel
import java.lang.StrictMath.log
import java.text.DecimalFormat


class CoinDetails : Fragment() {
    private lateinit var binding: FragmentCoinDetaisBinding
    private val args: CoinDetailsArgs by navArgs()
    private val coinsViewModel: CoinsViewModel by activityViewModels()
    private var coinDetailsViewModel: CoinDetailsViewModel = CoinDetailsViewModel(CoinRepository())
    private lateinit var holdingsViewModel: HoldingsViewModel// by activityViewModels()
    private val longFormat: DecimalFormat = DecimalFormat("#.#######")
    private val shortFormat: DecimalFormat = DecimalFormat("#.##")
    private lateinit var chosenFiat:Fiat
    private var isLogScale = false
    private var timeFrameToggleList:MutableLiveData<ArrayList<Boolean>> = MutableLiveData(arrayListOf(false,false,false,false,false,false,true))
    private var period:MutableLiveData<String> = MutableLiveData("all")
    private lateinit var chartData: Chart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCoinDetaisBinding.inflate(layoutInflater)
        val index: Int = args.coinRank-1
        holdingsViewModel = ViewModelProvider(this)[HoldingsViewModel::class.java]
        initChartStyle()
        setTvBoldness()
        setOnClickListeners()


        timeFrameToggleList.observe(viewLifecycleOwner,{
            setTvBoldness()
        })
        coinsViewModel.getChosenFiat().observe(viewLifecycleOwner,{
            chosenFiat=it
        })
        period.observe(viewLifecycleOwner,{
            coinsViewModel.getCoinsResponse().observe(viewLifecycleOwner, { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        val coin = response.body()!!.coins[index]
                        binding.coinNameTv.text = coin.name
                        binding.imageView.load(coin.icon)
                        fillTextView(binding.currentPriceTv,R.string.current_price," "+longFormat.format(coin.price*chosenFiat.rate)+chosenFiat.symbol)
                        fillTextView(binding.availableSupplyTv,R.string.available_supply, " "+shortFormat.format(coin.availableSupply))
                        fillTextView(binding.totalSupplyTv,R.string.total_supply, " "+shortFormat.format(coin.totalSupply))
                        fillTextView(binding.marketCapTv,R.string.market_cap, " "+shortFormat.format(coin.marketCap*chosenFiat.rate)+chosenFiat.symbol)
                        fillTextView(binding.rankTv,R.string.rank, " #"+coin.rank.toString())
                        setClickListener()

                        coinDetailsViewModel.getCoinChart(period.value!!,coin.id).observe(viewLifecycleOwner,{ chartResponse ->
                            if (chartResponse.isSuccessful){
                                chartResponse.body()?.let { chart->
                                    initChartData(chart)
                                    chartData=chart
                                }
                            }

                        })

                    }
                } else {
                    Toast.makeText(context, response.code().toString(), Toast.LENGTH_LONG).show()
                }
            })
        })

        return binding.root
    }

    private fun setClickListener(){
        binding.buyButton.setOnClickListener {
            val action = CoinDetailsDirections.actionCoinDetailsToAddCoin(args.coinRank,chosenFiat)
            findNavController().navigate(action)
        }
    }

    private fun fillTextView(tv:TextView,strResId:Int,data:String){
        tv.text = (resources.getString(strResId)+data)
    }
    private fun initChartStyle(){
        val chart = binding.lineChart
        chart.setTouchEnabled(true)
        chart.setPinchZoom(true)
        chart.legend.isEnabled = false
        chart.axisLeft.isEnabled = false
        chart.axisRight.textColor = binding.availableSupplyTv.currentTextColor
        chart.xAxis.textColor = binding.availableSupplyTv.currentTextColor
        chart.description.isEnabled = false
        chart.axisRight.setDrawGridLines(true)
        chart.axisRight.gridLineWidth =0.5f
        chart.xAxis.setDrawGridLines(true)
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.labelCount = 5
        chart.xAxis.valueFormatter = XAxisDateFormatter()

        chart.invalidate()
    }

    private fun initChartData(chart: Chart){
        val lineChart = binding.lineChart

        val entries =arrayListOf<Entry>()
        for (it in chart.chart){
            val x = it[0]
            var y = it[1]*chosenFiat.rate
            if (isLogScale)
                y= log(y+1)
            val entry = Entry(x.toFloat(),y.toFloat())
            entries.add(entry)
        }

        val dataset= LineDataSet(entries,"price")
        if (isLogScale){
            lineChart.axisRight.valueFormatter = YAxisLogFormatter()
        }
        else{
            lineChart.axisRight.valueFormatter=LineChart(requireContext()).axisRight.valueFormatter
        }
        dataset.setDrawCircles(false)
        val data = LineData(dataset)
        lineChart.data = data
        lineChart.invalidate()
        lineChart.notifyDataSetChanged()
    }
    private fun setTvBoldness(){
        if (timeFrameToggleList.value!![0])binding.timeFrame24hTv.typeface = Typeface.DEFAULT_BOLD
        else binding.timeFrame24hTv.typeface = Typeface.DEFAULT
        if (timeFrameToggleList.value!![1])binding.timeFrame1WTv.typeface = Typeface.DEFAULT_BOLD
        else binding.timeFrame1WTv.typeface = Typeface.DEFAULT
        if (timeFrameToggleList.value!![2])binding.timeFrame1MTv.typeface = Typeface.DEFAULT_BOLD
        else binding.timeFrame1MTv.typeface = Typeface.DEFAULT
        if (timeFrameToggleList.value!![3])binding.timeFrame3MTv.typeface = Typeface.DEFAULT_BOLD
        else binding.timeFrame3MTv.typeface = Typeface.DEFAULT
        if (timeFrameToggleList.value!![4])binding.timeFrame6MTv.typeface = Typeface.DEFAULT_BOLD
        else binding.timeFrame6MTv.typeface = Typeface.DEFAULT
        if (timeFrameToggleList.value!![5])binding.timeFrame1YTv.typeface = Typeface.DEFAULT_BOLD
        else binding.timeFrame1YTv.typeface = Typeface.DEFAULT
        if (timeFrameToggleList.value!![6])binding.timeFrameAllTv.typeface = Typeface.DEFAULT_BOLD
        else binding.timeFrameAllTv.typeface = Typeface.DEFAULT
        if (isLogScale)binding.logScaleTV.typeface = Typeface.DEFAULT_BOLD
        else binding.logScaleTV.typeface = Typeface.DEFAULT
    }

    private fun toggleOthersOff(index:Int){
        val newList = ArrayList<Boolean>()
        for ((i, _) in timeFrameToggleList.value!!.withIndex()){
            if (i!=index)
                newList.add(false)
            else newList.add(true)
        }
        timeFrameToggleList.value=newList
    }

    private fun setOnClickListeners(){
        binding.timeFrame24hTv.setOnClickListener {
            toggleOthersOff(0)
            period.value = "24h"
        }
        binding.timeFrame1WTv.setOnClickListener {
            toggleOthersOff(1)
            period.value = "1w"
        }
        binding.timeFrame1MTv.setOnClickListener {
            toggleOthersOff(2)
            period.value = "1m"
        }
        binding.timeFrame3MTv.setOnClickListener {
            toggleOthersOff(3)
            period.value = "3m"
        }
        binding.timeFrame6MTv.setOnClickListener {
            toggleOthersOff(4)
            period.value = "6m"
        }
        binding.timeFrame1YTv.setOnClickListener {
            toggleOthersOff(5)
            period.value = "1y"
        }
        binding.timeFrameAllTv.setOnClickListener {
            toggleOthersOff(6)
            period.value = "all"
        }
        binding.logScaleTV.setOnClickListener {
            isLogScale = !isLogScale
            setTvBoldness()
            initChartData(chartData)
        }
    }
}