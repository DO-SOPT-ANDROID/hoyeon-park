package org.sopt.dosopttemplate.presentation.follower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import org.sopt.dosopttemplate.data.follower.ResponseFollowerDto
import org.sopt.dosopttemplate.databinding.ItemFollowerBinding
import org.sopt.dosopttemplate.presentation.util.ItemDiffCallback

class FollowerAdapter() :
    ListAdapter<ResponseFollowerDto.FollowerData, FollowerAdapter.FollowerViewHolder>(
        ItemDiffCallback<ResponseFollowerDto.FollowerData>(
            onItemsTheSame = { old, new -> old == new },
            onContentsTheSame = { old, new -> old == new },
        ),
    ) {

    class FollowerViewHolder(private val binding: ItemFollowerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseFollowerDto.FollowerData) {
            binding.ivAvatar.load(data.avatar) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            binding.tvEmail.text = data.email
            binding.tvName.text = data.firstName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding =
            ItemFollowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }
}
