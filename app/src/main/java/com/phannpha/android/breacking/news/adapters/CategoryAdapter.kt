package com.phannpha.android.breacking.news.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.phannpha.android.breacking.news.R
import com.phannpha.android.breacking.news.databinding.ItemCategoryLayoutBinding
import com.phannpha.android.breacking.news.listeners.OnItemCategoryListener
import com.phannpha.android.breacking.news.networks.models.CategoryModel

class CategoryAdapter(
	private val category: ArrayList<CategoryModel>,
	private val onClick: OnItemCategoryListener
) :
	RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = ItemCategoryLayoutBinding.inflate(layoutInflater, parent, false)
		return ViewHolder(binding)
	}

	override fun getItemCount(): Int {
		return category.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val currentCategory = category[position]
		holder.onBind(currentCategory, position)
	}

	private var currentPosition: Int = 0

	inner class ViewHolder(private val binding: ItemCategoryLayoutBinding) :
		RecyclerView.ViewHolder(binding.root) {
		@SuppressLint("NotifyDataSetChanged")
		fun onBind(category: CategoryModel, position: Int) {
			binding.title.text = category.title

			binding.categoryLayout.setOnClickListener {
				currentPosition = position
				onClick.onItemClick(category)
				notifyDataSetChanged()
			}
			if (currentPosition == position) {
				binding.title.setTextColor(
					ContextCompat.getColor(
						binding.root.context,
						R.color.black_color
					)
				)
			} else {
				binding.title.setTextColor(
					ContextCompat.getColor(
						binding.root.context,
						R.color.gray_color
					)
				)
			}
		}
	}
}