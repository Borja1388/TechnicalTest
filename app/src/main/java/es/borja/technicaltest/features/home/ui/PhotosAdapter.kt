package es.borja.technicaltest.features.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import es.borja.technicaltest.databinding.PhotoListRowBinding
import es.borja.technicaltest.extensions.loadUrl
import es.borja.technicaltest.models.Photo

class PhotosAdapter(
    private val items: MutableList<Photo>,
    private val adapterAction: (Photo) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PhotoListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ) {
            adapterAction(items[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PhotoDiffUtil(
        private val oldList: List<Photo>,
        private val newList: List<Photo>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    fun updateData(newList: List<Photo>) {
        val diffUtil = DiffUtil.calculateDiff(PhotoDiffUtil(items, newList))
        items.clear()
        items.addAll(newList)
        diffUtil.dispatchUpdatesTo(this)
    }

}

class ViewHolder(private val binding: PhotoListRowBinding, callBack: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            callBack(adapterPosition)
        }
    }

    fun bind(photo: Photo) {
        with(binding) {
            title.text = photo.title
            imageView.loadUrl(photo.thumbnail)
        }

    }
}