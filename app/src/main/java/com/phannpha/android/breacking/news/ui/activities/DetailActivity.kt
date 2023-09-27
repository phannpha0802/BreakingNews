package com.phannpha.android.breacking.news.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.phannpha.android.breacking.news.databinding.ActivityDetailBinding
import com.phannpha.android.breacking.news.extensions.Utils
import com.phannpha.android.breacking.news.networks.models.ArticleModel

class DetailActivity : AppCompatActivity() {
	private lateinit var binding: ActivityDetailBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)
		getDetail()
		buttonBack()
	}

	private fun buttonBack() {
		binding.buttonBack.setOnClickListener {
			finish()
		}
	}

	private fun getDetail() {
		val bundle: Bundle? = intent.extras
		val article = bundle!!.getSerializable("article") as ArticleModel
		initView(article)
//		Log.d("DetailActivity", article.toString())
	}

	@SuppressLint("SetTextI18n")
	private fun initView(articleModel: ArticleModel) {
		binding.title.text = articleModel.title
		binding.author.text = "Published at"
		binding.publishedAt.text = Utils.stringToDateFormatted(articleModel.publishedAt)
		binding.description.text = articleModel.description
		binding.content.text = articleModel.content
		Glide.with(this).load(articleModel.urlToImage).into(binding.image)
	}
}