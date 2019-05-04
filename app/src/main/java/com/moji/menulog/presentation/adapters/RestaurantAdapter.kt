package com.moji.menulog.presentation.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.moji.menulog.R
import com.moji.menulog.model.Restaurant

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantAdapter(private var restaurants: List<Restaurant>) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RestaurantAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_restaurant, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]
        with(holder) {
            txtName.text = restaurant.Name
            txtCousinType.text = restaurant.cuisineTypesInString
            ratingBar.rating = restaurant.RatingStars ?: 0F
            Picasso.with(layout.context).load(restaurant.Logo?.get(0)?.StandardResolutionURL).into(imgLogo)
        }
    }

    fun updateRestaurants(_restaurants: List<Restaurant>) {
        this.restaurants = _restaurants
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        val txtName: TextView = layout.txtName
        val txtCousinType: TextView = layout.txtCousinType
        val ratingBar: RatingBar = layout.ratingBar
        val imgLogo: ImageView = layout.imgLogo
    }

}
