package com.hongka.smsretriever.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hongka.smsretriever.databinding.HolderTaskBinding
import com.hongka.smsretriever.data.Task

/**
 * Created by jusung.kim@sk.com on 2019/02/14
 */
class TasksAdapter(private val activity: TasksActivity,
                   private val tasksViewModel: TasksViewModel) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    private var list = emptyList<Task>()

    fun setData(_list: List<Task>) {
        list = _list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HolderTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.listener = object : ItemClickListener {
            override fun onItemClicked(model: Task) {
                tasksViewModel.openTaskEvent.value = model.taskId
                Toast.makeText(parent.context, "${model.taskId}, ${model.scNumber}", Toast.LENGTH_SHORT).show()
            }
        }
        binding.lifecycleOwner = activity
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        binding.model = list[position]
        binding.executePendingBindings()
    }

    override fun getItemCount() = list.size

    class ViewHolder(_binding: HolderTaskBinding) : RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding
    }

    interface ItemClickListener {
        fun onItemClicked(model: Task)
    }
}