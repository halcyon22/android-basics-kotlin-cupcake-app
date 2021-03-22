package com.example.cupcake

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class OrderViewModel: ViewModel() {

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private val _pickupDate = MutableLiveData<String>()
    val pickupDate: LiveData<String> = _pickupDate

    private val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
    val pickupDateOptions = getPickupOptions()

    private val _total = MutableLiveData<Double>()
    val total: LiveData<Double> = _total

    init {
        resetOrder()
    }

    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val calendar = Calendar.getInstance()
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    fun hasNoPickupDateSet(): Boolean {
        return _pickupDate.value.isNullOrEmpty()
    }

    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _pickupDate.value = pickupDateOptions[0]
        _total.value = 0.0
    }

    fun setQuantity(numberCupcakes: Int) {
        Log.i("Cupcake", "numberCupcakes=${numberCupcakes}")
        _quantity.value = numberCupcakes
    }

    fun setFlavor(desiredFlavor: String) {
        Log.i("Cupcake", "desiredFlavor=${desiredFlavor}")
        _flavor.value = desiredFlavor
    }

    fun setPickupDate(pickupDate: String) {
        Log.i("Cupcake", "pickupDate=${pickupDate}")
        _pickupDate.value = pickupDate
    }

}