package com.phannpha.android.breacking.news.networks.services

import com.phannpha.android.acelida.apitest.networks.models.NewsModel
import com.phannpha.android.breacking.news.networks.APIRoute
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewServices {

	@GET(APIRoute.EVERY_THINGS)
	fun getNewList(
		@QueryMap queryMap: HashMap<String, Any>
	): Call<NewsModel>
}