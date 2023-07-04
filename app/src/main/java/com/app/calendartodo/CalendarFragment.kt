package com.app.calendartodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.app.calendartodo.database.TaskDB
import com.app.calendartodo.database.TaskDao
import com.app.calendartodo.databinding.FragmentCalendarBinding
import kotlinx.coroutines.InternalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var dao: TaskDao
    private lateinit var base: TaskDB

    @OptIn(InternalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_calendar,
            container, false )

        binding.buttonShow.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_calendarFragment_to_toDoFragment)
        }

        val currentDate = Calendar.getInstance()
        binding.calendarView.date = currentDate.timeInMillis

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            binding.textView.text = "Wybrana data: ".plus(formattedDate)
        }
        base = TaskDB.getTaskDB(requireContext())!!
        dao = base.taskDao()

        return binding.root
    }

}