package com.app.calendartodo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.app.calendartodo.R
import com.app.calendartodo.database.ArtAdapter
import com.app.calendartodo.database.TaskDB
import com.app.calendartodo.database.TaskDao
import com.app.calendartodo.databinding.CustomActionBarBinding
import com.app.calendartodo.databinding.FragmentToDoBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class ToDoFragment : Fragment() {
    private lateinit var binding: FragmentToDoBinding
    private lateinit var base: TaskDB
    private lateinit var recyclerViewAdapter: ArtAdapter
    private lateinit var dao: TaskDao
    private lateinit var includedBindingBar: CustomActionBarBinding

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_to_do,
            container, false
        )

        includedBindingBar = binding.customActionBar
        val pageTitle = getString(R.string.taskTitle)

        GlobalScope.launch(Dispatchers.Main) {
            includedBindingBar.pageTitle.text = pageTitle
            includedBindingBar.back.visibility = View.VISIBLE
            includedBindingBar.back.setOnClickListener { view: View -> goBack(view) }
        }

        val args = ToDoFragmentArgs.fromBundle(requireArguments())
        val date = args.date
        setDatabase(date)

        return binding.root
    }

    private fun goBack(view: View) {
        view.post {
            val action = ToDoFragmentDirections.actionToDoFragmentToCalendarFragment()
            view.findNavController().navigate(action)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun setDatabase(date : String) {
        base = TaskDB.getTaskDB(requireContext())!!
        dao = base.taskDao()

        GlobalScope.launch(Dispatchers.IO) {
            if (dao.getSizeToDo() == 0) {
                binding.textView2.text = getString(R.string.zeroTaskInfo).plus(date)
                binding.recyclerList.visibility = View.GONE
            }
            else {
                binding.recyclerList.visibility = View.VISIBLE
                recyclerViewAdapter = ArtAdapter(dao.getAllByDate(date))
                binding.recyclerList.adapter = recyclerViewAdapter
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }
    }
}