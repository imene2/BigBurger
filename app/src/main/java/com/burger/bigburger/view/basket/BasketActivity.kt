package com.burger.bigburger.view.basket

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.burger.bigburger.R
import com.burger.bigburger.utils.AlertUtility
import com.burger.bigburger.utils.GeneralUtils.changeToolbarFont
import com.burger.bigburger.utils.GeneralUtils.convertCentsToEuro
import com.burger.bigburger.viewmodel.basket.BasketViewModel
import kotlinx.android.synthetic.main.activity_basket.*

class BasketActivity : AppCompatActivity() {

    private lateinit var basketViewModel: BasketViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        basketViewModel = ViewModelProviders.of(this).get(BasketViewModel::class.java)

        //set toolbar as actionbar
        setSupportActionBar(toolbar)
        //display back button
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener { onBackPressed() }
        getSupportActionBar()!!.title = resources.getString(R.string.my_cart)
        toolbar.changeToolbarFont();

        //init adapter
        val adapter = BasketAdapter(this@BasketActivity, basketViewModel)
        //recycler view setup
        basketRecycler.layoutManager = LinearLayoutManager(this@BasketActivity)
        basketRecycler.setHasFixedSize(true)
        basketRecycler.adapter = adapter

        //get list of products in backet
        basketViewModel.getBasket().observe(this,
            Observer { burgerEntities ->
                //update recyclerview
                if (burgerEntities != null)
                    adapter.setBurgers(burgerEntities)
            })

        //get Total Price from database
        basketViewModel.getTotal().observe(this, Observer<Int> { integer ->
            var integer = integer

            if (integer == null)
                integer = 0

            totalbasket.text = integer.convertCentsToEuro()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.basket_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {

            R.id.clearAll -> {
                //show dialog to confirm clear basket
                AlertUtility.clearBasketDialog(this, basketViewModel)
                return true
            }

            else -> return super.onContextItemSelected(item)
        }
    }
}
