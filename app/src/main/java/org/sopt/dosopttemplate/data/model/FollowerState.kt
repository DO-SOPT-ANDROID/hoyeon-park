package org.sopt.dosopttemplate.data.model

import org.sopt.dosopttemplate.data.follower.ResponseFollowerDto

sealed class FollowerState {
    object Loading : FollowerState()
    data class Success(val data: ResponseFollowerDto) : FollowerState()
    object Error : FollowerState()
}
