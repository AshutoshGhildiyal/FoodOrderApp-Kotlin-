package com.example.fooddeliveryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.adapter.PlaceYourOrderAdapter
import com.example.fooddeliveryapp.models.RestaurentModels
import kotlinx.android.synthetic.main.activity_place_your_order.*

class PlaceYourOrderActivity : AppCompatActivity() {

    var placeYourOrderAdapter: PlaceYourOrderAdapter? = null
    var isDeliveryOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_your_order)

        val resturantModel: RestaurentModels? = intent.getParcelableExtra("ResturantModel")
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(resturantModel?.name)
        actionBar?.setSubtitle(resturantModel?.address)
        actionBar?.setDisplayHomeAsUpEnabled(true) // for enabling back button

        buttonPlaceYourOrder.setOnClickListener{
            onPlaceOrderButtonclick(resturantModel)
        }
        switchDelivery?.setOnCheckedChangeListener{buttonView, isChecked ->
            if(isChecked){
                inputAddress.visibility = View.VISIBLE
                inputCity.visibility = View.VISIBLE
                inputState.visibility = View.VISIBLE
                inputZip.visibility = View.VISIBLE
                tvDeliveryCharge.visibility = View.VISIBLE
                 tvDeliveryChargeAmount.visibility = View.VISIBLE
                isDeliveryOn = true
                calculateTheTotalAmount(resturantModel)


            }else{
                inputAddress.visibility = View.GONE
                inputCity.visibility = View.GONE
                inputState.visibility = View.GONE
                inputZip.visibility = View.GONE
                tvDeliveryCharge.visibility = View.GONE
                tvDeliveryChargeAmount.visibility = View.GONE
                isDeliveryOn = false
                calculateTheTotalAmount(resturantModel)

            }
        }
        initRecyclerView(resturantModel)
        calculateTheTotalAmount(resturantModel)
    }

    private fun initRecyclerView(resturantModel: RestaurentModels?) {
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        placeYourOrderAdapter = PlaceYourOrderAdapter(resturantModel?.menus)
        cartItemsRecyclerView.adapter = placeYourOrderAdapter
    }
    private fun  calculateTheTotalAmount(resturantModel: RestaurentModels?){
        var subTotalAmount =0f
        for(menu in resturantModel?.menus!!){
            subTotalAmount += menu?.price!! + menu?.totalInCart!!
        }
        tvSubtotalAmount.text = "$"+ String.format("%.2f", subTotalAmount)
        if (isDeliveryOn){
            tvDeliveryChargeAmount.text = "$"+String.format("%.2f", resturantModel.delivery_charge?.toFloat())
            subTotalAmount += resturantModel?.delivery_charge?.toFloat()!!
        }

        tvTotalAmount.text = "$"+String.format("%.2f", subTotalAmount)
    }

    private fun onPlaceOrderButtonclick(resturantModel: RestaurentModels?){
        if (TextUtils.isEmpty(inputName.text.toString())){
            inputName.error = "Enter your name"
            return
        }else if (isDeliveryOn && TextUtils.isEmpty(inputAddress.text.toString())){
            inputAddress.error = "Enter your address"
            return
        }else if (isDeliveryOn && TextUtils.isEmpty(inputCity.text.toString())){
            inputCity.error = "Enter your City Name"
            return
        }else if (isDeliveryOn && TextUtils.isEmpty(inputZip.text.toString())){
            inputZip.error = "Enter your Pin Code"
            return
        }else if (isDeliveryOn && TextUtils.isEmpty(inputCardNumber.text.toString())){
            inputCardNumber.error = "Enter your credit card number"
            return
        }else if (isDeliveryOn && TextUtils.isEmpty(inputCardExpiry.text.toString())){
            inputCardExpiry.error = "Enter your credit card expiry"
            return
        }else if (isDeliveryOn && TextUtils.isEmpty(inputCardPin.text.toString())){
            inputCardPin.error = "Enter your card pin/cvv"
            return
        }
        val intent  = Intent(this@PlaceYourOrderActivity, SuccessOrderActivity::class.java)
        intent.putExtra("ResturantModel", resturantModel)
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1000){
            setResult(RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }

}