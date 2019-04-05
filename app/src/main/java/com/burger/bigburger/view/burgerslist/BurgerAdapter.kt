package com.burger.bigburger.view.burgerslist

import android.content.Context
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.burger.bigburger.R
import com.burger.bigburger.data.remote.Burger
import com.burger.bigburger.utils.AlertUtility
import com.burger.bigburger.utils.GeneralUtils.convertCentsToEuro
import com.burger.bigburger.viewmodel.basket.BasketViewModel
import kotlinx.android.synthetic.main.burger_item.view.*

class BurgerAdapter internal constructor(val context: Context, val basketViewModel: BasketViewModel) :
    RecyclerView.Adapter<BurgerAdapter.BurgerViewHolder>() {


    //init items
    private var items = emptyList<Burger>();


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BurgerViewHolder {
        return BurgerViewHolder(LayoutInflater.from(context).inflate(R.layout.burger_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BurgerViewHolder, position: Int) {
        holder.bind(items[position])
    }


    fun setBurgers(list: List<Burger>) {
        this.items = list
        notifyDataSetChanged()
    }


    inner class BurgerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(burger: Burger) = with(itemView) {

            itemView.burgerTitle.text = burger.title
            itemView.burgerPrice.text = burger.price.convertCentsToEuro()

            //progress bar while loading thumbnail
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(context)
                .load(burger.thumbnail)
                .placeholder(circularProgressDrawable)
                .into(itemView.burgerThumb)

            itemView.burgerMenu.setOnClickListener {
                //show popup menu
                AlertUtility.showPopupMenu(context, it, burger, basketViewModel)
            }

        }


    }


}