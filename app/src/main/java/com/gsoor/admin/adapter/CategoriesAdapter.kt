package com.gsoor.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsoor.R
import com.gsoor.data.remote.model.CategoriesResponse
import com.gsoor.data.remote.model.CountriesResponse
import com.gsoor.databinding.ItemCategoryBinding
import com.gsoor.databinding.ItemCountryBinding

class CategoriesAdapter ( var countriesListner:CountiresListner) :  RecyclerView.Adapter<CategoriesAdapter.DeveloperViewHolder>() {

    var listInformation: MutableList<CategoriesResponse.Data>?= mutableListOf()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemCategoryBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_category, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = listInformation?.get(i)
        mDeveloperViewHolder.mTradersResponse.model = currentStudent
        mDeveloperViewHolder.onBinding(listInformation?.get(i))



    }
    fun onClickCountries(countriesListner:CountiresListner){
        this.countriesListner=countriesListner
    }
    fun setData(list: MutableList<CategoriesResponse.Data>){
        this.listInformation=list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listInformation?.size ?: 0
    }

    inner class DeveloperViewHolder(var mTradersResponse: ItemCategoryBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root){
        fun onBinding(postion: CategoriesResponse.Data?){
            mTradersResponse.imgDelete.setOnClickListener(){
                if (postion != null) {
                    countriesListner.onClickDelete(postion)
                }
            }

            mTradersResponse.imgEdit.setOnClickListener(){
                if (postion != null) {
                    countriesListner.onClickEditCountry(postion)
                }
            }

            itemView.setOnClickListener(){
                if (postion != null) {
                    countriesListner.onClickDetailsCountry(postion)
                }
            }
        }


    }


    interface CountiresListner{
        fun onClickDelete(postion: CategoriesResponse.Data)
        fun onClickEditCountry(postion: CategoriesResponse.Data)
        fun onClickDetailsCountry(postion: CategoriesResponse.Data)

    }
}