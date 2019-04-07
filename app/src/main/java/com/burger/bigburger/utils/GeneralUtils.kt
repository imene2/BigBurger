package com.burger.bigburger.utils

import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.widget.Toolbar
import android.widget.TextView
import java.text.NumberFormat
import java.util.*

object GeneralUtils {


    /**
     * convert cents to euro
     */
    fun Int.convertCentsToEuro() : String {

        val nf = NumberFormat.getCurrencyInstance(Locale.FRANCE)
        return  nf.format(this/100.0)
    }


    /**
     * check internet connectivity
     */
    fun isDeviceConnectedToInternet(context: Context) : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return (activeNetwork != null)
    }

    /**
     * apply custom font to toolbar
     */
    fun Toolbar.changeToolbarFont(){
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is TextView && view.text == title) {
                view.typeface = Typeface.createFromAsset(view.context.assets, "fonts/proxima_nova_semi_bold.ttf")
                break
            }
        }
    }



}