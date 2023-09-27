package com.phannpha.android.breacking.news.listeners

import com.phannpha.android.breacking.news.networks.models.ArticleModel

interface OnItemNewsClickListener {
	fun onItemClick(articleModel: ArticleModel)
}