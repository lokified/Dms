package com.dmssystem.dms.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dmssystem.dms.databinding.NotificationItemLayoutBinding

class NotificationsAdapter(
    private val notificationList: List<NotificationsModel>
): RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = NotificationItemLayoutBinding.inflate(inflater, parent, false)

        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {

        val notification = notificationList[position]

        holder.bind(notification)
    }

    override fun getItemCount(): Int = notificationList.size

    inner class NotificationViewHolder(val binding: NotificationItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(notificationsModel: NotificationsModel) {

            binding.apply {

                notificationTypeTxt.text = notificationsModel.type
                notificatiomTimeText.text = notificationsModel.time
                noticationTypeImg.setImageResource(notificationsModel.imageType)
                notificationDescriptionTxt.text = notificationsModel.description
            }
        }
    }
}