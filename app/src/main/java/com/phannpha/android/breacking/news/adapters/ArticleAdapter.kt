package com.phannpha.android.breacking.news.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phannpha.android.breacking.news.R
import com.phannpha.android.breacking.news.databinding.NewItemLayoutBinding
import com.phannpha.android.breacking.news.extensions.Utils
import com.phannpha.android.breacking.news.listeners.OnItemNewsClickListener
import com.phannpha.android.breacking.news.networks.models.ArticleModel

class ArticleAdapter(
	private val context: Activity,
	private val article: List<ArticleModel>,
	private val onClick: OnItemNewsClickListener
) :
	RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

	inner class ViewHolder(private val binding: NewItemLayoutBinding) :
		RecyclerView.ViewHolder(binding.root) {
		@SuppressLint("CheckResult")
		fun onBinding(article: ArticleModel) {
			// article list.
			with(binding) {
				titleNew.text = article.title
				authorNew.text = "Published at"
				publishedNew.text = Utils.stringToDateFormatted(article.publishedAt)
				Glide.with(context).load(article.urlToImage).placeholder(R.drawable.no_image)
					.into(binding.imageNews)

				root.setOnClickListener {
					onClick.onItemClick(article)
				}
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val view = NewItemLayoutBinding.inflate(layoutInflater, parent, false)

		return ViewHolder(view)
	}

	override fun getItemCount(): Int {
		return article.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.onBinding(article[position])
	}
}