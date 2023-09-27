package com.phannpha.android.breacking.news.networks.models

import com.phannpha.android.acelida.apitest.networks.models.SourceModel
import java.io.Serializable

data class ArticleModel(
	val author: Any,
	val content: String,
	val description: String,
	val publishedAt: String,
	val source: SourceModel,
	val title: String,
	val url: String,
	val urlToImage: String
) : Serializable
