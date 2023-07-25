package com.app.calendartodo.fragments

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.app.calendartodo.R
import com.app.calendartodo.databinding.MultipleActionButtonBinding
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(InternalCoroutinesApi::class)
class FloatingActionButtonHandler (private val fragment: CalendarFragment, private val binding: MultipleActionButtonBinding) {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.rotate_open) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.rotate_close) }
    private val moveToBottom: Animation by lazy { AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.move_to_bottom) }
    private val moveFromBottom: Animation by lazy { AnimationUtils.loadAnimation(fragment.requireContext(), R.anim.move_from_bottom) }
    private var click = false


    fun onAddButtonClicked() {
        setVisibility()
        setAnimation()
        click = !click
    }

    private fun setVisibility() {
        if(click){
            binding.floatingActionButtonNote.visibility = View.VISIBLE
            binding.floatingActionButtonEvent.visibility = View.VISIBLE
            binding.floatingActionButtonTask.visibility = View.VISIBLE
            binding.floatingActionButtonNote.isClickable = true
            binding.floatingActionButtonEvent.isClickable = true
            binding.floatingActionButtonTask.isClickable = true
        } else {
            binding.floatingActionButtonNote.visibility = View.INVISIBLE
            binding.floatingActionButtonEvent.visibility = View.INVISIBLE
            binding.floatingActionButtonTask.visibility = View.INVISIBLE
            binding.floatingActionButtonNote.isClickable = false
            binding.floatingActionButtonEvent.isClickable = false
            binding.floatingActionButtonTask.isClickable = false
        }
    }
    private fun setAnimation() {
        if(click){
            binding.floatingActionButtonNote.startAnimation(moveFromBottom)
            binding.floatingActionButtonEvent.startAnimation(moveFromBottom)
            binding.floatingActionButtonTask.startAnimation(moveFromBottom)
            binding.floatingActionButtonAdd.startAnimation(rotateOpen)
        } else {
            binding.floatingActionButtonNote.startAnimation(moveToBottom)
            binding.floatingActionButtonEvent.startAnimation(moveToBottom)
            binding.floatingActionButtonTask.startAnimation(moveToBottom)
            binding.floatingActionButtonAdd.startAnimation(rotateClose)
        }
    }
}