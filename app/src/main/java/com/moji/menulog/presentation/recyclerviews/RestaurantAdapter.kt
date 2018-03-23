package com.moji.menulog.presentation.recyclerviews

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.moji.menulog.R
import com.moji.menulog.domain.entities.RestaurantView

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_holder_restaurant.view.*

class RestaurantAdapter(private var restaurantList: List<RestaurantView>?) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    var data: List<RestaurantView>? = null
        set(_data) {
            restaurantList = _data
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RestaurantAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.view_holder_restaurant, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurantList?.get(position)
        if (restaurant != null) {
            holder.txtName.text = restaurant.name
            holder.txtCousinType.text = restaurant.cuisineTypesInString
            holder.ratingBar.rating = restaurant?.ratingStars ?: 0F
            Picasso.with(holder.layout.context).load(restaurant.logo?.get(0)?.standardResolutionURL).into(holder.imgLogo)
        }
    }

    override fun getItemCount(): Int {
        return restaurantList?.size ?: 0
    }

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        val txtName: TextView = layout.txtName
        val txtCousinType: TextView = layout.txtCousinType
        val ratingBar: RatingBar = layout.ratingBar
        val imgLogo: ImageView = layout.imgLogo
    }

}
