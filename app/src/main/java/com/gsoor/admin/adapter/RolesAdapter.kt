package com.gsoor.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gsoor.R
import com.gsoor.data.remote.model.RolesResponse
import com.gsoor.databinding.ItemRoleBinding

class RolesAdapter ( var roleListner:Rolsitemlistner) :  RecyclerView.Adapter<RolesAdapter.DeveloperViewHolder>() {

    var listInformation: MutableList<RolesResponse.Data>?= mutableListOf()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DeveloperViewHolder {
        val mTradersResponse = DataBindingUtil.inflate<ItemRoleBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_role, viewGroup, false
        )

        return DeveloperViewHolder(mTradersResponse)
    }

    override fun onBindViewHolder(mDeveloperViewHolder: DeveloperViewHolder, i: Int) {
        val currentStudent = listInformation?.get(i)
        mDeveloperViewHolder.mTradersResponse.model = currentStudent
        mDeveloperViewHolder.onBinding(listInformation?.get(i))



    }
    fun onClickRole(roleListner:Rolsitemlistner){
        this.roleListner=roleListner
    }
    fun setData(list: MutableList<RolesResponse.Data>){
        this.listInformation=list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listInformation?.size ?: 0
    }

    inner class DeveloperViewHolder(var mTradersResponse: ItemRoleBinding) :
        RecyclerView.ViewHolder(mTradersResponse.root){
        fun onBinding(postion:RolesResponse.Data?){
            mTradersResponse.imgDelete.setOnClickListener(){
                if (postion != null) {
                    roleListner.onClickDelete(postion)
                }
            }

            itemView.setOnClickListener(){
                if (postion != null) {
                    roleListner.onClickPermission(postion)
                }
            }
        }


    }


    interface Rolsitemlistner{
        fun onClickDelete(postion:RolesResponse.Data)
        fun onClickPermission(postion:RolesResponse.Data)
    }

}