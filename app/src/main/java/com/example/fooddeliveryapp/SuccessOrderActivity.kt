package com.example.fooddeliveryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.fooddeliveryapp.models.RestaurentModels
import kotlinx.android.synthetic.main.activity_success_order.*

class SuccessOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_order)

        val resturantModel: RestaurentModels? = intent.getParcelableExtra("ResturantModel")
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(resturantModel?.name)
        actionBar?.setSubtitle(resturantModel?.address)
        actionBar?.setDisplayHomeAsUpEnabled(false) // for disabling back button

        buttonDone.setOnClickListener{
            setResult(RESULT_OK)
            finish()
        }

    }
}