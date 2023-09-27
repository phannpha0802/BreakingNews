package com.phannpha.android.breacking.news

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.phannpha.android.acelida.apitest.networks.models.NewsModel
import com.phannpha.android.breacking.news.adapters.CategoryAdapter
import com.phannpha.android.breacking.news.adapters.ItemBrowsByAdapter
import com.phannpha.android.breacking.news.adapters.ItemNewAdapter
import com.phannpha.android.breacking.news.adapters.ItemPopularRedactionAdapter
import com.phannpha.android.breacking.news.databinding.ActivityMainBinding
import com.phannpha.android.breacking.news.extensions.Utils
import com.phannpha.android.breacking.news.listeners.OnItemCategoryListener
import com.phannpha.android.breacking.news.listeners.OnItemNewsClickListener
import com.phannpha.android.breacking.news.networks.APIRoute
import com.phannpha.android.breacking.news.networks.helpers.HttpClient
import com.phannpha.android.breacking.news.networks.models.ArticleModel
import com.phannpha.android.breacking.news.networks.models.CategoryModel
import com.phannpha.android.breacking.news.networks.models.RedactionModel
import com.phannpha.android.breacking.news.networks.services.NewServices
import com.phannpha.android.breacking.news.ui.activities.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private var screenWidth by Delegates.notNull<Int>()
	private var screenHeight by Delegates.notNull<Int>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		// fun called.
		initScreenSize()
		listCategory()
		redactionList()
		listBrowsBy()
		getNewsList()
	}

	private fun initScreenSize() {
		screenWidth = this.resources.displayMetrics.widthPixels
		screenHeight = this.resources.displayMetrics.heightPixels
	}

	private fun dataCategory(): ArrayList<CategoryModel> {
		val list = ArrayList<CategoryModel>()
		list.add(CategoryModel(1, "All news"))
		list.add(CategoryModel(2, "Business"))
		list.add(CategoryModel(3, "Politics"))
		list.add(CategoryModel(4, "Tech"))
		list.add(CategoryModel(5, "Healthy"))

		return list
	}

	private val listener = object : OnItemCategoryListener {
		override fun onItemClick(category: CategoryModel) {
			Toast.makeText(this@MainActivity, category.title, Toast.LENGTH_SHORT).show()
		}
	}

	private fun listCategory() {
		val adapter = CategoryAdapter(dataCategory(), listener)
		val rcView = binding.rcCategory
		val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
		rcView.layoutManager = layoutManager
		rcView.adapter = adapter
	}

	private fun redactionData(): List<RedactionModel> {
		return listOf(
			RedactionModel(1, R.drawable.logo_1),
			RedactionModel(2, R.drawable.logo_2),
			RedactionModel(3, R.drawable.logo_3),
			RedactionModel(4, R.drawable.logo_1),
			RedactionModel(5, R.drawable.logo_3)
		)
	}

	private fun redactionList() {
		val adapter = ItemPopularRedactionAdapter(redactionData())
		val rcRedaction = binding.rcPopularRedaction
		val layoutManager =
			LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
		rcRedaction.layoutManager = layoutManager
		rcRedaction.adapter = adapter
	}

	private fun dataBrowsBy(): ArrayList<CategoryModel> {
		val list = ArrayList<CategoryModel>()
		list.add(CategoryModel(1, "Trending"))
		list.add(CategoryModel(2, "Recommendation"))
		list.add(CategoryModel(3, "Newest"))
		list.add(CategoryModel(4, "Latest"))
		list.add(CategoryModel(5, "Healthy"))

		return list
	}

	private val listenerBrowsBy = object : OnItemCategoryListener {
		override fun onItemClick(category: CategoryModel) {
			Toast.makeText(this@MainActivity, category.title, Toast.LENGTH_SHORT).show()
		}
	}

	private fun listBrowsBy() {
		val adapter = ItemBrowsByAdapter(dataBrowsBy(), listenerBrowsBy)
		val rcView = binding.rcBrowseBy
		val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
		rcView.layoutManager = layoutManager
		rcView.adapter = adapter
	}


	private fun getNewsList() {
		// query
		val query = HashMap<String, Any>()
		query["q"] = "tesla"
		query["apiKey"] = APIRoute.API_KEY

		val api by lazy {
			HttpClient.getInstance().create(NewServices::class.java)
		}

		val news = api.getNewList(query)
		news.enqueue(object : Callback<NewsModel> {
			override fun onResponse(
				call: Call<NewsModel>,
				response: Response<NewsModel>
			) {
				if (response.isSuccessful && response.code() == 200) {
					val responseData = response.body()
					//Log.d("MainActivity", "onResponse: $responseData")

					// update ui.
					updateUI(responseData)
					highlightedNews(responseData!!.articles)

				} else {
					Log.d("MainActivity", "error Message: ${response.message()}")
				}
			}

			override fun onFailure(call: Call<NewsModel>, t: Throwable) {
				Log.d("MainActivity", "onResponse: onFailure")
			}
		})
	}

	// update ui.
	private fun updateUI(data: NewsModel?) {
		if (data != null) {
			initAdapter(data.articles)
		} else {
			Toast.makeText(this@MainActivity, "No record.", Toast.LENGTH_SHORT).show()
		}
	}

	private val listenerOnItemNew = object : OnItemNewsClickListener {
		override fun onItemClick(articleModel: ArticleModel) {
			startActivity(Intent(this@MainActivity, DetailActivity::class.java).apply {
				putExtra("article", articleModel)
			})
		}
	}

	private fun initAdapter(articles: List<ArticleModel>) {
		val adapter = ItemNewAdapter(articles, this, listenerOnItemNew)
		val rcNews = binding.rcNews
		val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
		rcNews.layoutManager = layoutManager
		rcNews.adapter = adapter
	}

	@SuppressLint("SetTextI18n")
	private fun highlightedNews(articles: List<ArticleModel>) {

		for (data in articles) {
			binding.title.text = data.title
			binding.author.text = "Published at"
			binding.publishedAt.text = Utils.stringToDateFormatted(data.publishedAt)
			Glide.with(this).load(data.urlToImage).into(binding.image1)
			Glide.with(this).load(data.urlToImage).into(binding.image2)
			Glide.with(this).load(data.urlToImage).into(binding.image3)
		}
	}
}