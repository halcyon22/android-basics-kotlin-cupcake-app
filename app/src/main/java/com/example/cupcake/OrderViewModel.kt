package com.example.cupcake

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class OrderViewModel: ViewModel() {

    companion object {
        const val CUPCAKE_PRICE = 2.0
        const val SAME_DAY_PICKUP_PRICE = 3.0
    }

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private val _pickupDate = MutableLiveData<String>()
    val pickupDate: LiveData<String> = _pickupDate

    private val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
    val pickupDateOptions = getPickupOptions()

    private val _total = MutableLiveData<Double>()
    val total: LiveData<String> = Transformations.map(_total) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    init {
        resetOrder()
        calculateTotal()
    }

    private fun calculateTotal() {
        val productTotal = (quantity.value ?: 0) * CUPCAKE_PRICE
        val isSameDayPickup = pickupDate.value == getPickupOptions()[0];
        val pickupTotal = if (isSameDayPickup) { SAME_DAY_PICKUP_PRICE } else { 0.0 }
        _total.value = productTotal + pickupTotal
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

    fun setQuantity(numberCupcakes: Int) {
        Log.i("Cupcake", "numberCupcakes=${numberCupcakes}")
        _quantity.value = numberCupcakes
        calculateTotal()
    }

    fun setFlavor(desiredFlavor: String) {
        Log.i("Cupcake", "desiredFlavor=${desiredFlavor}")
        _flavor.value = desiredFlavor
        calculateTotal()
    }

    fun setPickupDate(pickupDate: String) {
        Log.i("Cupcake", "pickupDate=${pickupDate}")
        _pickupDate.value = pickupDate
        calculateTotal()
    }

    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _pickupDate.value = pickupDateOptions[0]
        _total.value = 0.0
    }

}