package com.app.calendartodo

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.app.calendartodo.database.ArtAdapter
import com.app.calendartodo.database.TaskDB
import com.app.calendartodo.database.TaskDao
import com.app.calendartodo.databinding.FragmentToDoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@InternalCoroutinesApi
@RequiresApi(Build.VERSION_CODES.O)
class ToDoFragment : Fragment() {
    private lateinit var binding: FragmentToDoBinding
    private lateinit var base: TaskDB
    private lateinit var recyclerViewAdapter: ArtAdapter
    private lateinit var dao: TaskDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_to_do,
            container, false )

        setDatabase()

        return binding.root
    }

    private fun setDatabase() {
        base = TaskDB.getTaskDB(requireContext())!!
        dao = base.taskDao()

        GlobalScope.launch(Dispatchers.IO) {
            if (dao.getSizeToDo() == 0) {
                binding.textView2.text = "You don't have any task to do!"
                binding.recyclerList.visibility = View.GONE
            }
            else {
                binding.recyclerList.visibility = View.VISIBLE
                recyclerViewAdapter = ArtAdapter(dao.getAllByDate(LocalDateTime.now()))
                binding.recyclerList.adapter = recyclerViewAdapter
                recyclerViewAdapter.notifyDataSetChanged()
            }

        }
    }
}