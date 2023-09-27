package com.phannpha.android.breacking.news.listeners

import com.phannpha.android.breacking.news.networks.models.CategoryModel

interface OnItemCategoryListener {
	fun onItemClick(category: CategoryModel)
}