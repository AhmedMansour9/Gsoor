package com.gsoor.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsoor.R
import com.gsoor.data.remote.model.CountriesResponse
import com.gsoor.data.remote.model.PackagesResponse
import com.gsoor.databinding.ItemCountryBinding
import com.gsoor.databinding.ItemPackagesBinding

class PackagesAdapter (var context:Context, var countriesListner:CountiresListner) :  RecyclerView.Adapter<PackagesAdapter.DeveloperViewHolder>() {

    var listInformation: MutableList<PackagesResponse.Data>?= mutableListOf()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemPackagesBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_packages, viewGroup, false
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
    fun setData(list: MutableList<PackagesResponse.Data>){
        this.listInformation=list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listInformation?.size ?: 0
    }

    inner class DeveloperViewHolder(var mTradersResponse: ItemPackagesBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root){
        fun onBinding(postion: PackagesResponse.Data?){
            if (postion != null) {
                if(postion.isFree)
                {
                    mTradersResponse.TTypeofservice.text=context.getString(R.string.free)
                }else {
                    mTradersResponse.TTypeofservice.text=context.getString(R.string.paid)
                }

                    mTradersResponse.imgDelete.setOnClickListener(){
                        if (postion != null) {
                            countriesListner.onClickDelete(postion)
                        }
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
        fun onClickDelete(postion: PackagesResponse.Data)
        fun onClickEditCountry(postion: PackagesResponse.Data)
        fun onClickDetailsCountry(postion: PackagesResponse.Data)

    }

}