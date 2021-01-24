package com.gsoor.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsoor.R
import com.gsoor.data.remote.model.PackageDetailsResponse
import com.gsoor.data.remote.model.PackagesResponse
import com.gsoor.databinding.ItemPackagedetailsBinding
import com.gsoor.databinding.ItemPackagesBinding

class PackageDetailsAdapter (var context: Context, var countriesListner:CountiresListner) :  RecyclerView.Adapter<PackageDetailsAdapter.DeveloperViewHolder>() {

    var listInformation: MutableList<PackageDetailsResponse.Data>?= mutableListOf()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemPackagedetailsBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_packagedetails, viewGroup, false
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
    fun setData(list: MutableList<PackageDetailsResponse.Data>){
        this.listInformation=list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listInformation?.size ?: 0
    }

    inner class DeveloperViewHolder(var mTradersResponse: ItemPackagedetailsBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root){
        fun onBinding(postion: PackageDetailsResponse.Data?){
            mTradersResponse.TPrice.text=postion?.price.toString()

            if (postion != null) {
                if(postion.timeType.equals("Day"))
                {
                    mTradersResponse.TTime.text=postion.timeValue.toString()+" "+context.getString(R.string.day)
                }else  if(postion.timeType.equals("Month"))
                {
                    mTradersResponse.TTime.text=postion.timeValue.toString()+" "+context.getString(R.string.month)
                }
                else  if(postion.timeType.equals("Year"))
                {
                    mTradersResponse.TTime.text=postion.timeValue.toString()+" "+context.getString(R.string.year)
                }

                mTradersResponse.imgDelete.setOnClickListener(){
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


    interface CountiresListner{
        fun onClickDelete(postion: PackageDetailsResponse.Data)
        fun onClickEditCountry(postion: PackageDetailsResponse.Data)

    }

}