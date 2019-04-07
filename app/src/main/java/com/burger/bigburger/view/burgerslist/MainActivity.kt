package com.burger.bigburger.view.burgerslist

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.burger.bigburger.R
import com.burger.bigburger.data.remote.Burger
import com.burger.bigburger.utils.GeneralUtils
import com.burger.bigburger.utils.GeneralUtils.changeToolbarFont
import com.burger.bigburger.view.basket.BasketActivity
import com.burger.bigburger.viewmodel.basket.BasketViewModel
import com.burger.bigburger.viewmodel.burgerslist.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setup toolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.title = resources.getString(R.string.products)

        toolbar.changeToolbarFont();

        //initialize view models
        val basketViewModel = ViewModelProviders.of(this).get(BasketViewModel::class.java)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //initialize adapter
        val adapter = BurgerAdapter(this, basketViewModel)
        //setup recyclerview
        burgersList.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this@MainActivity, 2)
        burgersList.setHasFixedSize(true)
        //set recyclerview adapter
        burgersList.adapter = adapter

        //check network status
        if (!GeneralUtils.isDeviceConnectedToInternet(this)) {

            prgBurgers.visibility = View.INVISIBLE
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
        }

        //reload burgers
        reload_data.setOnClickListener {
            mainViewModel.getBurgers()
        }


        //observe list burgers : if changed notify adapter
        mainViewModel.getBurgers().observe(this, object : Observer<List<Burger>> {
            override fun onChanged(burgers: List<Burger>?) {
                //update recyclerview
                adapter.setBurgers(burgers!!)
            }
        })


        //observe loading status
        mainViewModel.isLoading().observe(this, Observer<Boolean> { aBoolean ->
            if (aBoolean!!) {

                //if is loading display progress bar
                prgBurgers.visibility = View.VISIBLE
                reload_data.visibility = View.INVISIBLE

            } else {
                //else hide progress bar
                prgBurgers.visibility = View.INVISIBLE
            }
        })


        //observe loading result
        mainViewModel.isLoadingFailed().observe(this, Observer<Boolean> { aBoolean ->
            if (aBoolean!!) {
                //if failure display reload button
                reload_data.visibility = View.VISIBLE
                Toast.makeText(this@MainActivity , resources.getString(R.string.server_error),Toast.LENGTH_LONG).show()

            } else {
                //if success hide reload button
                reload_data.visibility = View.GONE
            }
        })


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.mybasket -> {
                val intent = Intent(this@MainActivity, BasketActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
