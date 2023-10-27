package org.sopt.dosopttemplate

import androidx.annotation.DrawableRes

data class MyProfile(
    @DrawableRes val profileImage: Int,
    val name: String,
    val self_description: String,
)