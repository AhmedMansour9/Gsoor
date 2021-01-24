package com.gsoor.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsoor.R
import com.gsoor.data.remote.model.CategoriesResponse
import com.gsoor.data.remote.model.MembersAccountResponse
import com.gsoor.databinding.ItemCategoryBinding
import com.gsoor.databinding.ItemMemberaccountBinding

class MembersAccountAdapter ( var context:Context,var countriesListner:MembersListner) :
    RecyclerView.Adapter<MembersAccountAdapter.DeveloperViewHolder>(), Filterable {

    var listInformation: MutableList<MembersAccountResponse.Data>?= mutableListOf()
    var filtered: MutableList<MembersAccountResponse.Data>? = mutableListOf()
    var mArrayList: MutableList<MembersAccountResponse.Data> ?= null


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemMemberaccountBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_memberaccount, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        mDeveloperViewHolder.onBinding(listInformation?.get(i))



    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {

                filtered?.clear()
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    listInformation = mArrayList
                } else {
                    for (androidVersion in mArrayList!!) {
                        if (androidVersion.FullName.contains(charString)) {
                            filtered?.add(androidVersion)
                            break
                        }
                    }
                    listInformation = filtered
                }
                val filterResults = FilterResults()
                filterResults.values = listInformation
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResul: FilterResults) {
                listInformation = filterResul.values as MutableList<MembersAccountResponse.Data>?
                notifyDataSetChanged()
            }
        }
    }


    fun setData(list: MutableList<MembersAccountResponse.Data>){
        this.listInformation=list
        this.mArrayList=list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listInformation?.size ?: 0
    }

    inner class DeveloperViewHolder(var mItem: ItemMemberaccountBinding) :
        RecyclerView.ViewHolder(mItem.root){
        fun onBinding(postion: MembersAccountResponse.Data?){
            if (postion != null) {
                mItem.TFullName.text=postion.FullName
                if(postion.memberCertified){
                    mItem.TMemberCertification.text=context.resources.getString(R.string.trusted_member)
                }else {
                    mItem.TMemberCertification.text=context.resources.getString(R.string.untrusted_member)
                }

                if(postion.memberVerification){
                    mItem.TMemberVertifcation.text=context.resources.getString(R.string.approval_member)
                }else {
                    mItem.TMemberVertifcation.text=context.resources.getString(R.string.refuse_member)
                }

                if(postion.statusType){
                    mItem.TMemberStatus.text=context.resources.getString(R.string.accept)
                }else {
                    mItem.TMemberStatus.text=context.resources.getString(R.string.block)
                }

                    mItem.TMemberCertification.setOnClickListener(){
                            countriesListner.onClickCertification(postion)

                    }

                    mItem.TMemberVertifcation.setOnClickListener(){
                            countriesListner.onClickVerfication(postion)

                    }

                mItem.TMemberStatus.setOnClickListener(){
                            countriesListner.onClickStatus(postion)
                    }

            }
        }


    }


    interface MembersListner{
        fun onClickCertification(postion: MembersAccountResponse.Data)
        fun onClickVerfication(postion: MembersAccountResponse.Data)
        fun onClickStatus(postion: MembersAccountResponse.Data)

    }
}