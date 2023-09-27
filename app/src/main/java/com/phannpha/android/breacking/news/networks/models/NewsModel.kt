package com.phannpha.android.acelida.apitest.networks.models

import com.phannpha.android.breacking.news.networks.models.ArticleModel

data class NewsModel(
	val articles: List<ArticleModel>,
	val status: String,
	val totalResults: Int
)
