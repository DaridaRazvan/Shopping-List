package com.example.shoppinglist.fragment.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoppinglist.R
import com.example.shoppinglist.data.Product
import com.example.shoppinglist.data.ProductViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private lateinit var mProductViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        view.bAddProduct.setOnClickListener{
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase(){
        val productName = productName.text.toString()

        if(inputCheck(productName)){
            val product = Product(0,productName,false)
            mProductViewModel.addProduct(product)
            Toast.makeText(requireContext(),"Successfully added!",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Please add a product",Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(productName: String): Boolean{
        return !(TextUtils.isEmpty(productName))
    }
}