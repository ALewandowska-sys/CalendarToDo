package com.app.calendartodo.fragments

import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.CalendarView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.app.calendartodo.R
import com.app.calendartodo.databinding.FragmentCalendarBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class CalendarFragment : Fragment() {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close) }
    private val moveToBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.move_to_bottom) }
    private val moveFromBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.move_from_bottom) }
    private lateinit var binding: FragmentCalendarBinding
    private var click = false
    private var date = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_calendar,
            container, false )

        binding.buttonShow.setOnClickListener { view: View ->
            Handler(view.context.mainLooper).post {
                val action = CalendarFragmentDirections.actionCalendarFragmentToToDoFragment()
                action.date = date
                view.findNavController().navigate(action)
            }
        }
        val title = view?.findViewById<TextView>(R.id.pageTitle)
        title?.text = getString(R.string.calendarTitle)

        val currentDate = Calendar.getInstance()
        binding.calendarView.date = currentDate.timeInMillis

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            showChosenDate(view, year, month, dayOfMonth)
        }

        return binding.root
    }

    private fun showChosenDate(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, dayOfMonth)

        date = selectedDate.time.toString()
        binding.textView.text = getString(R.string.chooseDate).plus(date)
    }

}