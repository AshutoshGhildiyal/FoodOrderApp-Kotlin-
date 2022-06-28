package com.example.fooddeliveryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.adapter.ResturantListAdapter
import com.example.fooddeliveryapp.models.RestaurentModels
import com.google.gson.Gson
import java.io.*

class MainActivity : AppCompatActivity() , ResturantListAdapter.ResturantListClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle("Resturant List")

        val resturantModel = getResturantData()
        initRecyclerView(resturantModel)
    }

    private fun initRecyclerView(resturantList: List<RestaurentModels?>?) {
        val recyclerViewResturant =  findViewById<RecyclerView>(R.id.recyclerViewRestaurant)
        recyclerViewResturant.layoutManager = LinearLayoutManager(this)
        val adapter = ResturantListAdapter(resturantList, this)
        recyclerViewResturant.adapter = adapter

    }

private fun getResturantData(): List<RestaurentModels?>?{
    val inputStream: InputStream = resources.openRawResource(R.raw.restaurent)
    val writer: Writer = StringWriter()
    val buffer= CharArray(1024)
    try {
        val reader: Reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
        var n:Int

        while (reader.read(buffer).also { n = it } != -1){
            writer.write(buffer,0,n)

        }
    }catch (e: Exception){}
    val jsonStr: String = writer.toString()
    val gson= Gson()
    val restaurentModels = gson.fromJson<Array<RestaurentModels>>(jsonStr, Array<RestaurentModels>::class.java).toList()
    return restaurentModels

}

    override fun onItemClick(resturantModels: RestaurentModels) {
        val intent = Intent(this, ResturantMenuActivity::class.java)
        intent.putExtra("ResturantModel", resturantModels)
        startActivity(intent)
    }
}