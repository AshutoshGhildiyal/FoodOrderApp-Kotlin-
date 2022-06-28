package com.example.fooddeliveryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.adapter.MenuListAdapter
import com.example.fooddeliveryapp.models.Menus
import com.example.fooddeliveryapp.models.RestaurentModels
import kotlinx.android.synthetic.main.activity_resturant_menu.*

class ResturantMenuActivity : AppCompatActivity(), MenuListAdapter.MenuListClickListener {

    private var menuList: List<Menus?>? = null
    private var totalItemInCartCount = 0
    private var menuListAdapter: MenuListAdapter? = null
    private var itemsIntheCartList: MutableList<Menus?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resturant_menu)

        val restaurantModels = intent?.getParcelableExtra<RestaurentModels>("ResturantModel")

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(restaurantModels?.name)
        actionBar?.setSubtitle(restaurantModels?.address)
        actionBar?.setDisplayHomeAsUpEnabled(true)  // enabling back button

        menuList = restaurantModels?.menus

        initRecyclerView(menuList)
        checkOutButton.setOnClickListener {
    if(itemsIntheCartList != null && itemsIntheCartList !!.size <=0){
        Toast.makeText(this@ResturantMenuActivity,"Please add some items in the cart", Toast.LENGTH_SHORT).show()
    }else{
        restaurantModels?.menus = itemsIntheCartList
        val intent = Intent(this@ResturantMenuActivity, PlaceYourOrderActivity::class.java)
        intent.putExtra("ResturantModel", restaurantModels)
        startActivityForResult(intent, 1000)
    }

        }
    }

    private fun initRecyclerView(menus: List<Menus?>?) {
        menuRecyclerView.layoutManager = GridLayoutManager(this, 2)
        menuListAdapter = MenuListAdapter(menus, this)
        menuRecyclerView.adapter = menuListAdapter
    }

    override fun addToCartClickListener(menus: Menus) {
        if (itemsIntheCartList == null) {
            itemsIntheCartList = ArrayList()
        }
        itemsIntheCartList?.add(menus)
        totalItemInCartCount = 0

        for (menu in itemsIntheCartList!!) {
            totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
        }
        checkOutButton.text = "Checkout (" + totalItemInCartCount + ") Items"

    }

    override fun updateCartClickListener(menus: Menus) {
        val index = itemsIntheCartList!!.indexOf(menus)
        itemsIntheCartList?.removeAt(index)
itemsIntheCartList?.add(menus)
        totalItemInCartCount = 0

        for (menu in itemsIntheCartList!!) {
            totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
        }
        checkOutButton.text = "Checkout (" + totalItemInCartCount + ") Items"
    }

    override fun removeFromCartClickListener(menus: Menus) {
        if (itemsIntheCartList!!.contains(menus)) {
            itemsIntheCartList?.remove(menus)

            totalItemInCartCount = 0
            for (menu in itemsIntheCartList!!) {
                totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
            }
            checkOutButton.text = "Checkout (" + totalItemInCartCount + ") Items"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1000 && resultCode == RESULT_OK){
            finish()
        }

    }
}