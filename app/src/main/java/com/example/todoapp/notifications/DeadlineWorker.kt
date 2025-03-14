package com.example.todoapp.notifications

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.todoapp.data.local.ToDo

class DeadlineWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun doWork(): Result {
        val title = inputData.getString("task_title") ?: return Result.failure()
        val notificationHelper = NotificationHelper(applicationContext)
        notificationHelper.showNotification("Дедлайн приближается!", "Задача: $title")
        return Result.success()
    }
}
