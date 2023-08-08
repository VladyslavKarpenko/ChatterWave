package com.test.chatterwave.beta.features.auth_activity.sign_up.avatar_create_fragment.create_with_gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chi.interngram.echo.R
import com.chi.interngram.echo.databinding.GalleryItemBinding
import com.test.chatterwave.beta.utils.bindUrlImage

class GalleryAdapter ( private var onItemClicked: ((photo: String) -> Unit), private val lifecycleOwner: LifecycleOwner)
    : RecyclerView.Adapter<GalleryAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return  oldItem.hashCode() == newItem.hashCode()
        }


        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)

    inner class ViewHolder(private val bindingAdapter: GalleryItemBinding) : RecyclerView.ViewHolder(bindingAdapter.root){
        init {
            bindingAdapter.galleryImage.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClicked(differ.currentList[position])
                }
            }
        }

        fun bind(item : String){
            bindingAdapter.galleryImage.bindUrlImage(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<GalleryItemBinding>(inflater, R.layout.gallery_item, parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}