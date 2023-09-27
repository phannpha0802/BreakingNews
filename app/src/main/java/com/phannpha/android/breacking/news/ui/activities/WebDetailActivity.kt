package com.phannpha.android.breacking.news.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.phannpha.android.breacking.news.databinding.ActivityWebDetailBinding

class WebDetailActivity : AppCompatActivity() {
	private lateinit var binding: ActivityWebDetailBinding

	@SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityWebDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.loading.text = "loading..."
		val bundle: Bundle? = intent.extras
		val url = bundle!!.getString("url").toString()

		binding.webView.settings.javaScriptEnabled = true
		binding.webView.webViewClient = object : WebViewClient() {
			@Deprecated("Deprecated in Java")
			override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
				if (url != null) {
					view?.loadUrl(url)
				}
				return true
			}
		}
		binding.webView.loadUrl(url)
		Handler().postDelayed({
			binding.loading.text = ""
		}, 4000)
	}
}