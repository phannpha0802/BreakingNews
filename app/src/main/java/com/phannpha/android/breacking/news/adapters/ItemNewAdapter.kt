package com.phannpha.android.breacking.news.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phannpha.android.breacking.news.R
import com.phannpha.android.breacking.news.databinding.ItemNewsLayoutBinding
import com.phannpha.android.breacking.news.extensions.Utils
import com.phannpha.android.breacking.news.listeners.OnItemNewsClickListener
import com.phannpha.android.breacking.news.networks.models.ArticleModel

class ItemNewAdapter(
	private val articleModel: List<ArticleModel>,
	private val context: Activity,
	private val onClick: OnItemNewsClickListener
) :
	RecyclerView.Adapter<ItemNewAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = ItemNewsLayoutBinding.inflate(layoutInflater, parent, false)

		return ViewHolder(binding)
	}

	override fun getItemCount(): Int = articleModel.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.onBind(articleModel[position])
	}

	inner class ViewHolder(private val binding: ItemNewsLayoutBinding) :
		RecyclerView.ViewHolder(binding.root) {
		@SuppressLint("SetTextI18n")
		fun onBind(articleModel: ArticleModel) {
			binding.title.text = articleModel.title
			binding.description.text = articleModel.description
			binding.author.text = "Published at"
			binding.publishedAt.text = Utils.stringToDateFormatted(articleModel.publishedAt)

			Glide.with(context).load(articleModel.urlToImage).placeholder(R.drawable.logo_1)
				.into(binding.image)

			binding.root.setOnClickListener {
				onClick.onItemClick(articleModel)
			}
		}
	}
}