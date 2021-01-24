package com.gsoor.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsoor.R
import com.gsoor.data.remote.model.CountriesResponse
import com.gsoor.data.remote.model.RolesResponse
import com.gsoor.databinding.ItemCountryBinding
import com.gsoor.databinding.ItemRoleBinding

class CountriesAdapter ( var countriesListner:CountiresListner) :  RecyclerView.Adapter<CountriesAdapter.DeveloperViewHolder>() {

    var listInformation: MutableList<CountriesResponse.Data>?= mutableListOf()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemCountryBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_country, viewGroup, false
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
    fun setData(list: MutableList<CountriesResponse.Data>){
        this.listInformation=list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listInformation?.size ?: 0
    }

    inner class DeveloperViewHolder(var mTradersResponse: ItemCountryBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root){
        fun onBinding(postion: CountriesResponse.Data?){
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
        fun onClickDelete(postion: CountriesResponse.Data)
        fun onClickEditCountry(postion: CountriesResponse.Data)
        fun onClickDetailsCountry(postion: CountriesResponse.Data)

    }

}