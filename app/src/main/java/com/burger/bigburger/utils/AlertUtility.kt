package com.burger.bigburger.utils

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.burger.bigburger.R
import com.burger.bigburger.data.local.BurgerEntity
import com.burger.bigburger.data.remote.Burger
import com.burger.bigburger.utils.GeneralUtils.convertCentsToEuro
import com.burger.bigburger.viewmodel.basket.BasketViewModel
import kotlinx.android.synthetic.main.add_to_basket_layout.view.*
import kotlinx.android.synthetic.main.burger_details.view.*

object AlertUtility {


    /**
     * Alert dialog with custom view
     * to add burger to basket
     */
    private fun addToBasketDialog(context: Context, burger: Burger, viewModel: BasketViewModel) {

        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        //inflate layout
        val dialogView = inflater.inflate(R.layout.add_to_basket_layout, null)
        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()
        val numberPicker = dialogView.numberPicker

        dialogView.totalprice.text = burger.price.convertCentsToEuro()

        numberPicker.setListener { _ , newValue ->
            dialogView.totalprice.text = (newValue * burger.price).convertCentsToEuro()
        }



        dialogView.add.setOnClickListener {

            //prepare entity
            val burgerEntity = BurgerEntity(
                burger.ref,
                burger.title,
                burger.description,
                burger.thumbnail,
                burger.price,
                numberPicker.value
            )

            //update current entity if exists, else insert
            viewModel.upsert(burgerEntity)
            Toast.makeText(context, context.resources.getString(R.string.burger_added), Toast.LENGTH_LONG).show()

            alertDialog.dismiss()
        }

        dialogView.cancel.setOnClickListener {
            alertDialog.dismiss()
        }


        alertDialog.show()
    }


    /**
     * Alert dialog with custom
     * to show burger details
     */
    private fun showDetailsDialog(context: Context, burger: Burger) {

        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val dialogView = inflater.inflate(R.layout.burger_details, null)
        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()

        Glide.with(context)
            .load(burger.thumbnail)
            .into(dialogView.imageView)

        dialogView.burgerTitle.text = burger.title
        dialogView.burgerDescription.text = burger.description
        dialogView.burgerPrice.text = burger.price.convertCentsToEuro()

        dialogView.close.setOnClickListener { alertDialog.dismiss() }

        alertDialog.show()

    }

    /**
     * Alert dialog to confirm delete entity
     */
    fun deleteAlertDialog(context: Context, basketViewModel: BasketViewModel, burgerEntity: BurgerEntity) {

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(context.getString(R.string.delete_product))
        alertDialogBuilder.setMessage(context.resources.getString(R.string.confirm_delete_product))

        //positive button to confirm delete
        alertDialogBuilder.setPositiveButton(
            context.resources.getString(R.string.yes)
        ) { _, _ -> basketViewModel.delete(burgerEntity)
            Toast.makeText(context,context.getString(R.string.product_removed),Toast.LENGTH_LONG).show()
        }


        //negative button to cancel delete
        alertDialogBuilder.setNegativeButton(
            context.resources.getString(R.string.cancel)
        ) { dialog, _ -> dialog.dismiss() }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }


    /**
     * show alert dialog to confirm clear backet
     *
     */
    fun clearBasketDialog(context: Context, basketViewModel: BasketViewModel) {

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(context.resources.getString(R.string.clear_basket_title))
        alertDialogBuilder.setMessage(context.resources.getString(R.string.clear_basket))

        alertDialogBuilder.setPositiveButton(
            context.resources.getString(R.string.yes)
        ) { _, _ ->
            basketViewModel.deleteBasket()
            Toast.makeText(context, context.getString(R.string.basket_removed), Toast.LENGTH_LONG).show()
        }

        alertDialogBuilder.setNegativeButton(
            context.resources.getString(R.string.cancel)
        ) { dialog, _ -> dialog.dismiss() }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    /**
     * show popup menu attached to burger item
     */
    fun showPopupMenu(context: Context, view: View, burger: Burger, viewModel: BasketViewModel) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.burger_menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.show ->
                    showDetailsDialog(context, burger)

                R.id.addtobasket ->
                    addToBasketDialog(context, burger, viewModel)
            }

            false
        }

        popupMenu.show()
    }


}