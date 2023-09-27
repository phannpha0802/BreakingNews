package com.phannpha.android.breacking.news.networks.helpers

import com.phannpha.android.breacking.news.networks.APIRoute
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpClient {

	companion object {

		fun getInstance(): Retrofit {

			// API response interceptor
			val loggingInterceptor = HttpLoggingInterceptor()
				.setLevel(HttpLoggingInterceptor.Level.BODY)

			// Client
			val client = OkHttpClient.Builder()
				.addInterceptor(loggingInterceptor)
				.build()

			return Retrofit.Builder()
				.baseUrl(APIRoute.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.client(client)
				.build()
		}
	}
}