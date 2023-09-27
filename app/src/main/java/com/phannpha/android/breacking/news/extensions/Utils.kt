package com.phannpha.android.breacking.news.extensions

import java.text.SimpleDateFormat
import java.util.Locale

class Utils {
	companion object {
		fun stringToDateFormatted(
			dateAsString: String,
			inputPattern: String = "yyyy-MM-dd'T'HH:mm:ss",
			outputPattern: String = "dd MMM, yyyy"
		): String {
			val locale =  Locale.getDefault()

			val inputFormatter = SimpleDateFormat(inputPattern, locale)
			val date = inputFormatter.parse(dateAsString) ?: return ""

			val outPutFormatter = SimpleDateFormat(outputPattern, locale)
			return outPutFormatter.format(date)
		}
	}
}