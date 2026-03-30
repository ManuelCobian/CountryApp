package com.luvsoft.core.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val tld: List<String> = emptyList(),
    val cca2: String? = null,
    val ccn3: String? = null,
    val cca3: String? = null,
    val cioc: String? = null,
    val independent: Boolean? = null,
    val status: String? = null,
    val unMember: Boolean? = null,
    val idd: Idd? = null,
    val capital: List<String> = emptyList(),
    val altSpellings: List<String> = emptyList(),
    val region: String? = null,
    val subregion: String? = null,
    val landlocked: Boolean? = null,
    val borders: List<String> = emptyList(),
    val area: Double? = null,
    val maps: Maps? = null,
    val population: Long? = null,
    val fifa: String? = null,
    val car: Car? = null,
    val timezones: List<String> = emptyList(),
    val continents: List<String> = emptyList(),
    val flag: String? = null,
    val flags: CountryFlags? = null,
    val name: CountryName = CountryName(),
    val currencies: Map<String, Currency> = emptyMap(),
    val languages: Map<String, String> = emptyMap(),
    val latlng: List<Double> = emptyList(),
    val demonyms: Demonyms? = null,
    val translations: Map<String, Translation> = emptyMap(),
    val gini: Map<String, Double> = emptyMap(),
    val coatOfArms: CoatOfArms? = null,
    val startOfWeek: String? = null,
    val capitalInfo: CapitalInfo? = null,
    val postalCode: PostalCode? = null
) : Parcelable

@Parcelize
data class CountryFlags(
    val png: String? = null,
    val svg: String? = null,
    val alt: String? = null
) : Parcelable

@Parcelize
data class CoatOfArms(
    val png: String? = null,
    val svg: String? = null
) : Parcelable

@Parcelize
data class CountryName(
    val common: String? = null,
    val official: String? = null,
    val nativeName: Map<String, CountryNativeName> = emptyMap()
) : Parcelable

@Parcelize
data class CountryNativeName(
    val official: String? = null,
    val common: String? = null
) : Parcelable

@Parcelize
data class Idd(
    val root: String? = null,
    val suffixes: List<String> = emptyList()
) : Parcelable

@Parcelize
data class Maps(
    val googleMaps: String? = null,
    val openStreetMaps: String? = null
) : Parcelable

@Parcelize
data class Car(
    val signs: List<String> = emptyList(),
    val side: String? = null
) : Parcelable

@Parcelize
data class Currency(
    val symbol: String? = null,
    val name: String? = null
) : Parcelable

@Parcelize
data class Demonyms(
    val eng: DemonymDetail? = null,
    val fra: DemonymDetail? = null
) : Parcelable

@Parcelize
data class DemonymDetail(
    val f: String? = null,
    val m: String? = null
) : Parcelable

@Parcelize
data class Translation(
    val official: String? = null,
    val common: String? = null
) : Parcelable

@Parcelize
data class CapitalInfo(
    val latlng: List<Double> = emptyList()
) : Parcelable

@Parcelize
data class PostalCode(
    val format: String? = null,
    val regex: String? = null
) : Parcelable