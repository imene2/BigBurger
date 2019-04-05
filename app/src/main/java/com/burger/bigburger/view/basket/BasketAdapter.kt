package com.burger.bigburger.view.basket

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.burger.bigburger.R
import com.burger.bigburger.data.local.BurgerEntity
import com.burger.bigburger.utils.AlertUtility
import com.burger.bigburger.utils.GeneralUtils.convertCentsToEuro
import com.burger.bigburger.view.basket.BasketAdapter.BasketViewHolder
import com.burger.bigburger.viewmodel.basket.BasketViewModel
import kotlinx.android.synthetic.main.basket_item.view.*

class BasketAdapter internal constructor(val context: Context, val basketViewModel: BasketViewModel) :
    RecyclerView.Adapter<BasketViewHolder>() {


    //init list of burgers in backet
    private var list = emptyList<BurgerEntity>()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        //bind view
        holder.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        return BasketViewHolder(LayoutInflater.from(context).inflate(R.layout.basket_item, parent, false))
    }


    fun setBurgers(list: List<BurgerEntity>) {
        //update list and notify
        this.list = list
        notifyDataSetChanged()
    }

    inner class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        //bind view
        fun bind(burgerEntity: BurgerEntity) = with(itemView) {

            itemView.itemTitle.text = burgerEntity.title
            itemView.itemPrice.text = burgerEntity.price.convertCentsToEuro()
            itemView.itemQuantity.text = String.format(burgerEntity.quantity.toString().plus(" x"))

            itemView.itemDelete.setOnClickListener {
                //confirm delete product from basket
                AlertUtility.deleteAlertDialog(context, basketViewModel, burgerEntity);
            }

        }


    }
}