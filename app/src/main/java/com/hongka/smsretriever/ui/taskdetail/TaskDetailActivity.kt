package com.hongka.smsretriever.ui.taskdetail

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.hongka.smsretriever.R
import com.hongka.smsretriever.databinding.ActivityTaskDetailBinding

/**
 * Created by jusung.kim@sk.com on 2019/02/15
 */
class TaskDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskDetailBinding
    private var taskId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskId = intent.getStringExtra(EXTRA_TASK_ID)

        setupViews()
    }

    private fun setupViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.addButton.isVisible = taskId.isNullOrEmpty()
        binding.updateButton.isVisible = !taskId.isNullOrEmpty()

        binding.addButton.setOnClickListener {
            setResult(ADD_RESULT_OK)
            finish()
        }

        binding.updateButton.setOnClickListener {
            setResult(EDIT_RESULT_OK)
            finish()
        }

        binding.deleteButton.setOnClickListener {
            setResult(DELETE_RESULT_OK)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
        const val REQUEST_CODE = 1

        const val ADD_RESULT_OK = Activity.RESULT_FIRST_USER + 1
        const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
        const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3
    }
}