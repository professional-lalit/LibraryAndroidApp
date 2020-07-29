package com.library.app.screens.main.fragments.books

import android.view.View
import android.widget.TextView
import com.library.app.R
import com.library.app.models.Category
import com.library.app.screens.common.BaseViewHolder

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 */
class CategoryViewHolder(
    private val mItemView: View,
    private val mItemClickListener: (Category) -> Unit
) : BaseViewHolder(mItemView) {

    private var txtCategoryName = mItemView.findViewById<TextView>(R.id.txt_category_name)

    override fun onBindView(localData: Any) {
        val category = localData as Category
        txtCategoryName.text = category.name
        mItemView.setOnClickListener {
            mItemClickListener.invoke(category)
        }
    }


}