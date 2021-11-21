package hu.jazzy.hodlpal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import hu.jazzy.hodlpal.R
import hu.jazzy.hodlpal.databinding.FragmentChooseCurrencyDialogBinding
import hu.jazzy.hodlpal.model.Fiat
import hu.jazzy.hodlpal.viewmodels.CoinsViewModel


class ChooseCurrencyDialog : DialogFragment() {
    private lateinit var binding: FragmentChooseCurrencyDialogBinding
    private val coinsViewModel:CoinsViewModel by activityViewModels()
    private var fiatList:List<Fiat> = emptyList()
    private var fiatStringList:ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseCurrencyDialogBinding.inflate(layoutInflater)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_menu_item,fiatStringList)
        binding.currencyAcTv.setAdapter(arrayAdapter)
        initButtons()
        coinsViewModel.getFiatsResponse().observe(viewLifecycleOwner,{
            if (it.isSuccessful){
                if (it.body()!=null)
                {
                    fiatList= it.body()!!
                    for (fiat in fiatList){
                        fiatStringList.add(fiat.name)
                    }
                    arrayAdapter.notifyDataSetChanged()

                }
            }
        })
        return binding.root
    }

    private fun initButtons(){
        val action = ChooseCurrencyDialogDirections.actionChooseCurrencyDialogToWallet()
        binding.currencyChooseBtn.setOnClickListener {
            findNavController().navigate(action)
        }
        binding.currencyCancelBtn.setOnClickListener {
            findNavController().navigate(action)
        }
    }

}