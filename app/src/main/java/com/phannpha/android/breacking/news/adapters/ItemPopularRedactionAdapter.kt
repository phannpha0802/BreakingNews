package com.phannpha.android.breacking.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phannpha.android.breacking.news.databinding.ItemRedactionLayoutBinding
import com.phannpha.android.breacking.news.networks.models.RedactionModel

class ItemPopularRedactionAdapter(private val logoList: List<RedactionModel>) :
	RecyclerView.Adapter<ItemPopularRedactionAdapter.ViewHolder>() {

	inner class ViewHolder(private val binding: ItemRedactionLayoutBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun onBind(logo: RedactionModel) {
			logo.logo?.let { binding.logo.setImageResource(it) }
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = ItemRedactionLayoutBinding.inflate(layoutInflater, parent, false)

		return ViewHolder(binding)
	}

	override fun getItemCount(): Int = logoList.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.onBind(logoList[position])
	}
}