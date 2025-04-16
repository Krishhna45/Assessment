package com.example.assessment

import org.json.JSONArray
import java.net.URL

object CountryRepository {
    private const val DATA_URL = "https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json"

    fun getCountries(): List<Country> {
        return try {
            val jsonString = URL(DATA_URL).readText()
            val jsonArray = JSONArray(jsonString)
            val countryList = mutableListOf<Country>()

            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                countryList.add(
                    Country(
                        name = item.optString("name", "N/A"),
                        region = item.optString("region", "N/A"),
                        code = item.optString("code", "N/A"),
                        capital = item.optString("capital", "N/A")
                    )
                )
            }

            countryList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
