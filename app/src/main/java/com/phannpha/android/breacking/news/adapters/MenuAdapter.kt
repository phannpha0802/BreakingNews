package com.phannpha.android.breacking.news.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.phannpha.android.breacking.news.R
import com.phannpha.android.breacking.news.databinding.MenuItemLayoutBinding
import com.phannpha.android.breacking.news.listeners.OnItemMenuListener
import com.phannpha.android.breacking.news.networks.models.MenuModel

class MenuAdapter(
	private val menuItem: ArrayList<MenuModel>,
	private val onClick: OnItemMenuListener
) :
	RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
	private var currentPosition = 0

	inner class ViewHolder(private val binding: MenuItemLayoutBinding) :
		RecyclerView.ViewHolder(binding.root) {
		@SuppressLint("NotifyDataSetChanged")
		fun onBind(menu: MenuModel, position: Int) {
			// menu item.
			with(binding) {
				// set menu title.
				titleMenu.text = menu.title

				// set user itemListener.
				binding.root.setOnClickListener {
					onClick.onSelectedMenuItem(menu)
					// change current position.
					currentPosition = position
					notifyDataSetChanged()
				}

				// set color text when user action.
				if (currentPosition == position) {
					titleMenu.setTextColor(
						ContextCompat.getColor(
							binding.root.context,
							R.color.dark_color
						)
					)
				} else {
					titleMenu.setTextColor(
						ContextCompat.getColor(
							binding.root.context,
							R.color.gray_color
						)
					)
				}
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = MenuItemLayoutBinding.inflate(layoutInflater, parent, false)

		return ViewHolder(binding)
	}

	override fun getItemCount(): Int {
		return menuItem.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.onBind(menuItem[position], position)
	}
}