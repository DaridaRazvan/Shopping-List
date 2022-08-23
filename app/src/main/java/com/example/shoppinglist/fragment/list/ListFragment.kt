package com.example.shoppinglist.fragment.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.data.Product
import com.example.shoppinglist.data.ProductViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment(), ListAdapter.ClickListener {

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var adapter: ListAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_list, container, false)
        //Recyclerview
        adapter = ListAdapter(this)
        val recyclerView = view.rvMainList
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(recyclerView)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //UserViewModel
        mProductViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        mProductViewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.setData(products)
            showDeleteButton(products)
        }

        view.bAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        view.bDelete.setOnClickListener {
            mProductViewModel.deleteCheckedProducts()
        }

        searchView = view?.findViewById<SearchView>(R.id.svFindItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if(query != null)
                    mProductViewModel.searchQuery.value = query
                return true
            }
        })

        return view
    }

    private fun showDeleteButton(products : List<Product>){
        for (product in products){
            if (product.isChecked) {
                requireView().bDelete.visibility = View.VISIBLE;
                return;
            }
        }
        requireView().bDelete.visibility = View.GONE;
    }

    override fun onProductClick(product: Product, isChecked: Boolean) {
        Toast.makeText(context,"Product checked!", Toast.LENGTH_LONG).show()
        product.isChecked = isChecked
        mProductViewModel.updateProduct(product)
    }

    private val itemTouchHelperCallback =
        object :
            ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val product: Product = adapter.getProductAtPosition(position)
                mProductViewModel.deleteProduct(product)
            }
        }
}