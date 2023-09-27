package com.phannpha.android.breacking.news.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.phannpha.android.acelida.apitest.networks.models.NewsModel
import com.phannpha.android.breacking.news.adapters.ArticleAdapter
import com.phannpha.android.breacking.news.adapters.MenuAdapter
import com.phannpha.android.breacking.news.databinding.ActivitySocialBinding
import com.phannpha.android.breacking.news.listeners.OnItemMenuListener
import com.phannpha.android.breacking.news.listeners.OnItemNewsClickListener
import com.phannpha.android.breacking.news.networks.APIRoute
import com.phannpha.android.breacking.news.networks.helpers.HttpClient
import com.phannpha.android.breacking.news.networks.models.ArticleModel
import com.phannpha.android.breacking.news.networks.models.MenuModel
import com.phannpha.android.breacking.news.networks.services.NewServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocialActivity : AppCompatActivity() {
	private lateinit var binding: ActivitySocialBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivitySocialBinding.inflate(layoutInflater)
		setContentView(binding.root)
		menuCategoryList()

		// query
		val query = HashMap<String, Any>()
		query["q"] = "google"
		query["apiKey"] = APIRoute.API_KEY
		getNews(query)
	}

	private fun menuData(): ArrayList<MenuModel> {
		val list = ArrayList<MenuModel>()
		list.add(MenuModel(1, "", "All News"))
		list.add(MenuModel(2, "", "Tesla"))
		list.add(MenuModel(3, "", "Apple"))
		list.add(MenuModel(4, "", "Bitcoin"))
		list.add(MenuModel(6, "", "More"))

		return list
	}

	private val itemSelectListener = object : OnItemMenuListener {
		override fun onSelectedMenuItem(item: MenuModel) {
			// item selected menu.
			when (item.title) {
				"All News" -> {
					binding.loadingAnimation.visibility = View.VISIBLE
					binding.rcNews.visibility = View.GONE
					// query
					val query = HashMap<String, Any>()
					query["q"] = "google"
					query["apiKey"] = APIRoute.API_KEY
					getNews(query)
				}

				"Tesla" -> {
					binding.loadingAnimation.visibility = View.VISIBLE
					binding.rcNews.visibility = View.GONE
					// query
					val query = HashMap<String, Any>()
					query["q"] = "tesla"
					query["apiKey"] = APIRoute.API_KEY
					getNews(query)
				}

				"Apple" -> {
					binding.loadingAnimation.visibility = View.VISIBLE
					binding.rcNews.visibility = View.GONE
					// query
					val query = HashMap<String, Any>()
					query["q"] = "apple"
					query["apiKey"] = APIRoute.API_KEY
					getNews(query)
				}

				"Bitcoin" -> {
					binding.loadingAnimation.visibility = View.VISIBLE
					binding.rcNews.visibility = View.GONE
					// query
					val query = HashMap<String, Any>()
					query["q"] = "bitcoin"
					query["apiKey"] = APIRoute.API_KEY
					getNews(query)
				}
			}
		}
	}

	private fun menuCategoryList() {
		val menuAdapter = MenuAdapter(menuData(), itemSelectListener)
		val view = binding.rcMenuCategorySocialScreen
		val layout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
		view.layoutManager = layout
		view.adapter = menuAdapter
	}

	private fun getNews(query: HashMap<String, Any>) = CoroutineScope(Dispatchers.IO).launch {
		val api = HttpClient.getInstance().create(NewServices::class.java)
		val news = api.getNewList(query)
		try {
			news.enqueue(object : Callback<NewsModel> {
				override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
					if (response.isSuccessful && response.code() == 200) {
						val data = response.body()

						// update ui.
						articleList(data!!.articles)
						// close animation.
						binding.loadingAnimation.visibility = View.GONE
						binding.rcNews.visibility = View.VISIBLE
					} else {
						Log.d("SocialActivity", "onResponse: ${response.errorBody()}")
					}
				}

				override fun onFailure(call: Call<NewsModel>, t: Throwable) {
					Log.d("SocialActivity", "onFailure: ${t.message}")
				}
			})
		} catch (e: Exception) {
			Log.d("SocialActivity", "getNews: ${e.message}")
		}
	}

	private val newsListener = object : OnItemNewsClickListener {
		override fun onItemClick(articleModel: ArticleModel) {
			// item news selected.
			startActivity(Intent(this@SocialActivity, WebDetailActivity::class.java).apply {
				putExtra("url", articleModel.url)
			})
		}
	}

	private fun articleList(article: List<ArticleModel>) {

		val articleAdapter = ArticleAdapter(this, article, newsListener)
		val view = binding.rcNews
		val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
		view.layoutManager = layout
		view.adapter = articleAdapter
	}

	private fun test() {
		val name = "Por Srenghong"
	}
}