package com.phannpha.android.breacking.news.listeners

import com.phannpha.android.breacking.news.networks.models.MenuModel

interface OnItemMenuListener {
	fun onSelectedMenuItem(item: MenuModel)
}