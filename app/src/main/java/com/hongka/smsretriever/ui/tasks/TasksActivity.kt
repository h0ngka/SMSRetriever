package com.hongka.smsretriever.ui.tasks

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.hongka.smsretriever.R
import com.hongka.smsretriever.data.source.local.ToDoDatabase
import com.hongka.smsretriever.databinding.ActivityTasksBinding
import com.hongka.smsretriever.ui.taskdetail.TaskDetailActivity
import com.hongka.smsretriever.util.PermissionUtil

class TasksActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSIONS_REQUEST_SMS = 0
        private const val TAG = "TasksActivity"
    }

    private val SMSPermissions = arrayOf(
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_SMS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_PHONE_STATE
    )

    private val hasSMSPermission get() = PermissionUtil.hasSelfPermission(this, SMSPermissions)


    private lateinit var binding: ActivityTasksBinding
    private val tasksAdapter: TasksAdapter by lazy {
        TasksAdapter(this, viewModel)
    }
    private lateinit var viewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!hasSMSPermission) {
            ActivityCompat.requestPermissions(
                this, SMSPermissions,
                PERMISSIONS_REQUEST_SMS
            )
        }

        viewModel = ViewModelProviders.of(this).get(TasksViewModel::class.java)

        setupViews()
        setupObservers()

        viewModel.setDatabase(application)
        viewModel.loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        ToDoDatabase.destroyInstance()
    }

    private fun setupViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tasks)
        binding.recyclerView.adapter = tasksAdapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply {
                ContextCompat.getDrawable(this@TasksActivity, R.drawable.decoration_divider)?.let { drawable ->
                    setDrawable(drawable)
                }
            }
        )
        binding.addButton.setOnClickListener {
            openTaskDetail(null)
        }
    }

    private fun setupObservers() {
        viewModel.tasks.observe(this, Observer {
            tasksAdapter.setData(it)

            binding.recyclerView.isVisible = !it.isEmpty()
            binding.emptyTextView.isVisible = it.isEmpty()
        })

        viewModel.openTaskEvent.observe(this, Observer { taskId ->
            openTaskDetail(taskId)
        })

        viewModel.snackbarMessage.observe(this, Observer { resId ->
            viewModel.loadData()
            Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
        })
    }

    private fun openTaskDetail(taskId: String?) {
        val intent = Intent(this, TaskDetailActivity::class.java).apply {
            putExtra(TaskDetailActivity.EXTRA_TASK_ID, taskId)
        }
        startActivityForResult(intent, TaskDetailActivity.REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.handleActivityResult(requestCode, resultCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_SMS -> {
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    Log.i(TAG, "권한 허용")
                } else {
                    Log.i(TAG, "권한 거절")
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
