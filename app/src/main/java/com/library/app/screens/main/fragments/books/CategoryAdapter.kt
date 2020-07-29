package com.library.app.screens.main.fragments.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.library.app.R
import com.library.app.models.Category
import com.library.app.screens.common.BaseViewHolder

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 */
class CategoryAdapter(
    private val mList: ArrayList<Category>,
    private val mItemClickListener: (Category) -> Unit
) :
    RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false),
            mItemClickListener
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBindView(mList[position])
    }

}