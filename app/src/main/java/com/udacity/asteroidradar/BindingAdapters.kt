package com.udacity.asteroidradar

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.domain.DomainAsteriod
import com.udacity.asteroidradar.domain.PictureOfDay

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        Log.i("model", isHazardous.toString())
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}


@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("picTitle")
fun bindTextViewTitle(textView: TextView, picObj: PictureOfDay?) {
    val context = textView.context
    textView.text = picObj?.title ?: String.format(context.getString(R.string.no_image))
}

@BindingAdapter("imgUrl")
fun bindImage(imageView: ImageView, picObj: PictureOfDay?) {

    if (picObj != null) {
        if (picObj.media_type == "image") {
            Picasso.with(imageView.context).load(picObj.url).into(imageView);
        } else {
            Picasso.with(imageView.context).load(
                "https://apod.nasa.gov/apod/image/2001/STSCI-H-p2006a-h-1024x614.jpg\n"
            ).into(imageView);

        }
    }


}