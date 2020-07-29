package com.library.app.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.library.app.R
import kotlinx.android.synthetic.main.item_book.view.*
import javax.inject.Inject

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 */
object ImageLoader {

    fun getImage(imageView: ImageView, url: String) {
        Glide.with(CustomApplication.appInstance)
            .load(url)
            .placeholder(R.drawable.ic_book)
            .into(imageView)
    }

}