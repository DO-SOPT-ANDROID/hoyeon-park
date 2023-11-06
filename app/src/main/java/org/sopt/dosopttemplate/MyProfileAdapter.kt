package org.sopt.dosopttemplate

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.databinding.ItemMyProfileBinding

class MyProfileAdapter(context: Context) : RecyclerView.Adapter<MyProfileViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var myProfile: List<MyProfile> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProfileViewHolder {
        val binding = ItemMyProfileBinding.inflate(inflater, parent, false)
        return MyProfileViewHolder(binding)
    }

    override fun getItemCount() = if (myProfile != null) 1 else 0

    override fun onBindViewHolder(holder: MyProfileViewHolder, position: Int) {
        holder.onBind(myProfile[position])
    }

    fun setMyProfile(myProfile: MyProfile) {
        this.myProfile = listOf(myProfile)
        notifyDataSetChanged()
    }
}

