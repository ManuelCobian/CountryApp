package com.luvsoft.core.repositories.mappers

import com.luvsoft.core.network.models.CapitalInfoResponse
import com.luvsoft.core.network.models.CarResponse
import com.luvsoft.core.network.models.CoatOfArmsResponse
import com.luvsoft.core.network.models.CountryNameResponse
import com.luvsoft.core.network.models.CountryNativeNameResponse
import com.luvsoft.core.network.models.CountryResponse
import com.luvsoft.core.network.models.CurrencyResponse
import com.luvsoft.core.network.models.DemonymDetailResponse
import com.luvsoft.core.network.models.DemonymsResponse
import com.luvsoft.core.network.models.FlagsResponse
import com.luvsoft.core.network.models.IddResponse
import com.luvsoft.core.network.models.MapsResponse
import com.luvsoft.core.network.models.PostalCodeResponse
import com.luvsoft.core.network.models.TranslationResponse
import com.luvsoft.core.ui.CapitalInfo
import com.luvsoft.core.ui.Car
import com.luvsoft.core.ui.CoatOfArms
import com.luvsoft.core.ui.Country
import com.luvsoft.core.ui.CountryFlags
import com.luvsoft.core.ui.CountryName
import com.luvsoft.core.ui.CountryNativeName
import com.luvsoft.core.ui.Currency
import com.luvsoft.core.ui.DemonymDetail
import com.luvsoft.core.ui.Demonyms
import com.luvsoft.core.ui.Idd
import com.luvsoft.core.ui.Maps
import com.luvsoft.core.ui.PostalCode
import com.luvsoft.core.ui.Translation

fun CountryResponse.toDomain(): Country =
    Country(
        tld = tld.orEmpty(),
        cca2 = cca2,
        ccn3 = ccn3,
        cca3 = cca3,
        cioc = cioc,
        independent = independent,
        status = status,
        unMember = unMember,
        idd = idd?.toDomain(),
        capital = capital.orEmpty(),
        altSpellings = altSpellings.orEmpty(),
        region = region,
        subregion = subregion,
        landlocked = landlocked,
        borders = borders.orEmpty(),
        area = area,
        maps = maps?.toDomain(),
        population = population,
        fifa = fifa,
        car = car?.toDomain(),
        timezones = timezones.orEmpty(),
        continents = continents.orEmpty(),
        flag = flag,
        name = name?.toDomain() ?: CountryName(),
        currencies = currencies?.mapValues { it.value.toDomain() }.orEmpty(),
        languages = languages.orEmpty(),
        latlng = latlng.orEmpty(),
        demonyms = demonyms?.toDomain(),
        translations = translations?.mapValues { it.value.toDomain() }.orEmpty(),
        gini = gini.orEmpty(),
        flags = flags?.toDomain(),
        coatOfArms = coatOfArms?.toDomain(),
        startOfWeek = startOfWeek,
        capitalInfo = capitalInfo?.toDomain(),
        postalCode = postalCode?.toDomain()
    )

fun FlagsResponse.toDomain(): CountryFlags =
    CountryFlags(
        png = png,
        svg = svg,
        alt = alt
    )

fun CountryNameResponse.toDomain(): CountryName =
    CountryName(
        common = common,
        official = official,
        nativeName = nativeName?.mapValues { it.value.toDomain() }.orEmpty()
    )

fun CountryNativeNameResponse.toDomain(): CountryNativeName =
    CountryNativeName(
        official = official,
        common = common
    )

fun IddResponse.toDomain(): Idd =
    Idd(
        root = root,
        suffixes = suffixes.orEmpty()
    )

fun MapsResponse.toDomain(): Maps =
    Maps(
        googleMaps = googleMaps,
        openStreetMaps = openStreetMaps
    )

fun CarResponse.toDomain(): Car =
    Car(
        signs = signs.orEmpty(),
        side = side
    )

fun CurrencyResponse.toDomain(): Currency =
    Currency(
        symbol = symbol,
        name = name
    )

fun DemonymsResponse.toDomain(): Demonyms =
    Demonyms(
        eng = eng?.toDomain(),
        fra = fra?.toDomain()
    )

fun DemonymDetailResponse.toDomain(): DemonymDetail =
    DemonymDetail(
        f = f,
        m = m
    )

fun TranslationResponse.toDomain(): Translation =
    Translation(
        official = official,
        common = common
    )

fun CoatOfArmsResponse.toDomain(): CoatOfArms =
    CoatOfArms(
        png = png,
        svg = svg
    )

fun CapitalInfoResponse.toDomain(): CapitalInfo =
    CapitalInfo(
        latlng = latlng.orEmpty()
    )

fun PostalCodeResponse.toDomain(): PostalCode =
    PostalCode(
        format = format,
        regex = regex
    )