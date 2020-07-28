package com.library.app.screens.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 */
abstract class BaseViewHolder(private val mItemView: View) : RecyclerView.ViewHolder(mItemView) {

    abstract fun onBindView(localData: Any)

}