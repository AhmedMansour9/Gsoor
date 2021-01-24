package com.gsoor.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsoor.R
import com.gsoor.data.remote.model.CitiesResponse
import com.gsoor.data.remote.model.CountriesResponse
import com.gsoor.databinding.ItemCityBinding
import com.gsoor.databinding.ItemCountryBinding

class CitiesAdapter ( var countriesListner:CitiesListner) :  RecyclerView.Adapter<CitiesAdapter.DeveloperViewHolder>() {

    var listInformation: MutableList<CitiesResponse.Data>?= mutableListOf()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemCityBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_city, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = listInformation?.get(i)
        mDeveloperViewHolder.mTradersResponse.model = currentStudent
        mDeveloperViewHolder.onBinding(listInformation?.get(i))



    }
    fun onClickCountries(countriesListner:CitiesListner){
        this.countriesListner=countriesListner
    }
    fun setData(list: MutableList<CitiesResponse.Data>){
        this.listInformation=list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listInformation?.size ?: 0
    }

    inner class DeveloperViewHolder(var mTradersResponse: ItemCityBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root){
        fun onBinding(postion: CitiesResponse.Data?){
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

        }


    }


    interface CitiesListner{
        fun onClickDelete(postion: CitiesResponse.Data)
        fun onClickEditCountry(postion: CitiesResponse.Data)
    }
}