package com.weatherApp.extentions

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.weatherApp.R
import com.squareup.picasso.Picasso


fun View.gone() {
    visibility = View.GONE
}
fun View.invisible() {
    visibility = View.INVISIBLE
}
fun View.show() {
    visibility = View.VISIBLE
}

fun Activity.toast(text:String){
    Toast.makeText(applicationContext,text, Toast.LENGTH_SHORT).show()
}


fun ImageView.showImageFromUrl(url:String)
{

    Picasso.get().load(url).placeholder(R.drawable.temp_image).error(R.drawable.temp_image).into(this)
}

fun View.showView(show:Boolean) {
    this.visibility =if(show) View.VISIBLE else View.GONE
}

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

