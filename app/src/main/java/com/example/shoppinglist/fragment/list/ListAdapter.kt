package com.example.shoppinglist.fragment.list

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.data.Product
import kotlinx.android.synthetic.main.product.view.*

class ListAdapter(private var clickListener: ClickListener): RecyclerView.Adapter<ListAdapter.ProductViewHolder>() {

    private var productList = emptyList<Product>()

    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private fun TextView.showStrikeThrough(show: Boolean) {
        paintFlags =
            if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product,parent,false))
    }

    private fun ConstraintLayout.applyBackgroundForItem(isChecked: Boolean){
        background =
            if (isChecked) ContextCompat.getDrawable(context, R.drawable.product_bg_selected)
            else ContextCompat.getDrawable(context, R.drawable.product_bg)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]

        holder.itemView.apply {
            tw_productName.text = currentItem.name
            cb_is_checked.isChecked = currentItem.isChecked

            tw_productName.showStrikeThrough(currentItem.isChecked)
            cl_item.applyBackgroundForItem(currentItem.isChecked)

            cb_is_checked.setOnCheckedChangeListener{ _, isChecked ->
                clickListener.onProductClick(currentItem, isChecked)
                Log.v("onCheckedChangeListener", "item clicked")
            }
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setData(products: List<Product>){
        this.productList = products
        notifyDataSetChanged()
    }

    fun getProductAtPosition(position : Int): Product{
        return this.productList[position]
    }

    interface ClickListener{
        fun onProductClick(product: Product, isChecked: Boolean)
    }
}