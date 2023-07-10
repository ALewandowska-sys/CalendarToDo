package com.app.calendartodo.fragments

import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.app.calendartodo.R
import com.app.calendartodo.databinding.FragmentCalendarBinding
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
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



        val currentDate = Calendar.getInstance()
        binding.calendarView.date = currentDate.timeInMillis

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            date = selectedDate.time.toString()
            binding.textView.text = "Wybrana data: $date "
        }

        return binding.root
    }

}