package com.app.calendartodo.fragments

import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.app.calendartodo.R
import com.app.calendartodo.databinding.CustomActionBarBinding
import com.app.calendartodo.databinding.FragmentCalendarBinding
import com.app.calendartodo.databinding.MultipleActionButtonBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var includedBindingButtons: MultipleActionButtonBinding
    private lateinit var includedBindingBar: CustomActionBarBinding
    private var date = ""

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_calendar,
            container, false )

        includedBindingBar = binding.customActionBar
        includedBindingBar.pageTitle.text = getString(R.string.calendarTitle)
        handleFloatingActionButton()
        GlobalScope.launch(Dispatchers.Main) {
            includedBindingButtons.floatingActionButtonTask.setOnClickListener { view: View -> addNote(view) }
        }

        val currentDate = Calendar.getInstance()
        binding.calendarView.date = currentDate.timeInMillis

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            showChosenDate(year, month, dayOfMonth)
        }

        return binding.root
    }

    private fun handleFloatingActionButton() {
        includedBindingButtons = binding.multipleActionButton
        val handler = FloatingActionButtonHandler(this, includedBindingButtons)
        includedBindingButtons.floatingActionButtonAdd.setOnClickListener { handler.onAddButtonClicked() }
    }

    private fun showChosenDate(year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, dayOfMonth)

        date = selectedDate.time.toString()
        binding.textView.text = getString(R.string.chooseDate).plus(date)
    }

    private fun addNote(view: View){
        Handler(view.context.mainLooper).post {
            val action = CalendarFragmentDirections.actionCalendarFragmentToToDoFragment()
            action.date = date
            view.findNavController().navigate(action)
        }
    }

}