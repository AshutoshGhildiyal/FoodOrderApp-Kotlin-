package com.example.fooddeliveryapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.models.Hours
import com.example.fooddeliveryapp.models.RestaurentModels
import java.text.SimpleDateFormat
import java.util.*

class ResturantListAdapter(val resturantList: List<RestaurentModels?>?, val clickListener: ResturantListClickListener) :
    RecyclerView.Adapter<ResturantListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResturantListAdapter.MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_resturant_list_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResturantListAdapter.MyViewHolder, position: Int) {
        holder.bind(resturantList?.get(position))
        holder.itemView.setOnClickListener{
            clickListener.onItemClick(resturantList?.get(position)!!)
        }
    }

    override fun getItemCount(): Int {
        return resturantList?.size!!
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRestaurentName = view.findViewById<TextView>(R.id.tvResturantName)
        val tvResturantAddress = view.findViewById<TextView>(R.id.tvResturantAddress)
        val tvResturantHours = view.findViewById<TextView>(R.id.tvResturantHours)
        val thumbImage = view.findViewById<ImageView>(R.id.thumbImage)

        fun bind(resturantModels: RestaurentModels?) {
            tvRestaurentName.text = resturantModels?.name
            tvResturantAddress.text = "Address: " + resturantModels?.address
            tvResturantHours.text = "Today's Hours: " + getTodaysHours(resturantModels?.hours!!)

            Glide.with(thumbImage).load(resturantModels?.image).into(thumbImage)

        }
    }

    private fun getTodaysHours(hours: Hours): String? {
        val calendar: Calendar = Calendar.getInstance()
        val date: Date = calendar.time
        val day: String = SimpleDateFormat("EEE", Locale.ENGLISH).format(date.time)
        return when (day) {
            "Sunday" -> hours.Sunday
            "Monday" -> hours.Monday
            "Tuesday" -> hours.Tuesday
            "Wednesday"-> hours.Wednesday
            "Thursday"-> hours.Thursday
            "Friday"-> hours.Friday
            "Saturday"-> hours.Saturday
            else -> hours.Sunday
        }
    }

    interface ResturantListClickListener{
        fun onItemClick(resturantModels: RestaurentModels){

        }
    }
}