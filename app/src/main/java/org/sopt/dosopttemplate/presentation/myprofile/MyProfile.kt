package org.sopt.dosopttemplate.presentation.myprofile

import androidx.annotation.DrawableRes

data class MyProfile(
    @DrawableRes val profileImage: Int,
    val name: String,
    val self_description: String,
)