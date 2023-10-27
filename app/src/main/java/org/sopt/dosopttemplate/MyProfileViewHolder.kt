package org.sopt.dosopttemplate

import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.databinding.ItemMyProfileBinding

class MyProfileViewHolder(private val binding: ItemMyProfileBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun onBind(profileData: MyProfile) {
            binding.ivMyProfile.setImageResource(profileData.profileImage)
            binding.tvMyName.text = profileData.name
        }
    }
