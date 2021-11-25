package hu.jazzy.hodlpal.formatters

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import hu.jazzy.hodlpal.database.Converter
import java.text.SimpleDateFormat


class XAxisDateFormatter: ValueFormatter() {

    private val sdf =  SimpleDateFormat("dd MMM YY")
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val longVal = value.toLong()
        return sdf.format(Converter.fromLongToDate(longVal))
    }
}