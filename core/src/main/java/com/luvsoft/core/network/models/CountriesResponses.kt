package com.luvsoft.core.network.models

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("tld") val tld: List<String>? = null,
    @SerializedName("cca2") val cca2: String? = null,
    @SerializedName("ccn3") val ccn3: String? = null,
    @SerializedName("cca3") val cca3: String? = null,
    @SerializedName("cioc") val cioc: String? = null,
    @SerializedName("independent") val independent: Boolean? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("unMember") val unMember: Boolean? = null,
    @SerializedName("idd") val idd: IddResponse? = null,
    @SerializedName("capital") val capital: List<String>? = null,
    @SerializedName("altSpellings") val altSpellings: List<String>? = null,
    @SerializedName("region") val region: String? = null,
    @SerializedName("subregion") val subregion: String? = null,
    @SerializedName("landlocked") val landlocked: Boolean? = null,
    @SerializedName("borders") val borders: List<String>? = null,
    @SerializedName("area") val area: Double? = null,
    @SerializedName("maps") val maps: MapsResponse? = null,
    @SerializedName("population") val population: Long? = null,
    @SerializedName("fifa") val fifa: String? = null,
    @SerializedName("car") val car: CarResponse? = null,
    @SerializedName("timezones") val timezones: List<String>? = null,
    @SerializedName("continents") val continents: List<String>? = null,
    @SerializedName("flag") val flag: String? = null,
    @SerializedName("name") val name: CountryNameResponse? = null,
    @SerializedName("currencies") val currencies: Map<String, CurrencyResponse>? = null,
    @SerializedName("languages") val languages: Map<String, String>? = null,
    @SerializedName("latlng") val latlng: List<Double>? = null,
    @SerializedName("demonyms") val demonyms: DemonymsResponse? = null,
    @SerializedName("translations") val translations: Map<String, TranslationResponse>? = null,
    @SerializedName("gini") val gini: Map<String, Double>? = null,
    @SerializedName("flags") val flags: FlagsResponse? = null,
    @SerializedName("coatOfArms") val coatOfArms: CoatOfArmsResponse? = null,
    @SerializedName("startOfWeek") val startOfWeek: String? = null,
    @SerializedName("capitalInfo") val capitalInfo: CapitalInfoResponse? = null,
    @SerializedName("postalCode") val postalCode: PostalCodeResponse? = null
)

data class FlagsResponse(
    @SerializedName("png") val png: String? = null,
    @SerializedName("svg") val svg: String? = null,
    @SerializedName("alt") val alt: String? = null
)

data class CoatOfArmsResponse(
    @SerializedName("png") val png: String? = null,
    @SerializedName("svg") val svg: String? = null
)

data class CountryNameResponse(
    @SerializedName("common") val common: String? = null,
    @SerializedName("official") val official: String? = null,
    @SerializedName("nativeName") val nativeName: Map<String, CountryNativeNameResponse>? = null
)

data class CountryNativeNameResponse(
    @SerializedName("official") val official: String? = null,
    @SerializedName("common") val common: String? = null
)

data class IddResponse(
    @SerializedName("root") val root: String? = null,
    @SerializedName("suffixes") val suffixes: List<String>? = null
)

data class MapsResponse(
    @SerializedName("googleMaps") val googleMaps: String? = null,
    @SerializedName("openStreetMaps") val openStreetMaps: String? = null
)

data class CarResponse(
    @SerializedName("signs") val signs: List<String>? = null,
    @SerializedName("side") val side: String? = null
)

data class CurrencyResponse(
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("name") val name: String? = null
)

data class DemonymsResponse(
    @SerializedName("eng") val eng: DemonymDetailResponse? = null,
    @SerializedName("fra") val fra: DemonymDetailResponse? = null
)

data class DemonymDetailResponse(
    @SerializedName("f") val f: String? = null,
    @SerializedName("m") val m: String? = null
)

data class TranslationResponse(
    @SerializedName("official") val official: String? = null,
    @SerializedName("common") val common: String? = null
)

data class CapitalInfoResponse(
    @SerializedName("latlng") val latlng: List<Double>? = null
)

data class PostalCodeResponse(
    @SerializedName("format") val format: String? = null,
    @SerializedName("regex") val regex: String? = null
)