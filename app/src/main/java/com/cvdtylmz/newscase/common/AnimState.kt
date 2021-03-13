package com.cvdtylmz.newscase.common

enum class AnimState {
    SLIDE_DOWN,
    SLIDE_UP
}

interface AnimationOnChangeListener {
    fun animationChanged (state : AnimState)
}