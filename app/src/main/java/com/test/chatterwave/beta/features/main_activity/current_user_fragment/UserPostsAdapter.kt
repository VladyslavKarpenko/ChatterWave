package com.test.chatterwave.beta.features.main_activity.current_user_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.domain.model.UserPostDomainModel

class UserPostsAdapter : PagingDataAdapter<UserPostDomainModel, UserPostsAdapter.ViewHolder>(
    UserPostsDiffItemCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img: ImageView = itemView.findViewById(R.id.postImage)

        fun bind(item: UserPostDomainModel) {
            Glide.with(itemView.context).load(item.source[0]).into(img)
        }
    }
}
private object UserPostsDiffItemCallback : DiffUtil.ItemCallback<UserPostDomainModel>() {

    override fun areItemsTheSame(oldItem: UserPostDomainModel, newItem: UserPostDomainModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserPostDomainModel, newItem: UserPostDomainModel): Boolean {
        return oldItem.id == newItem.id
    }
}