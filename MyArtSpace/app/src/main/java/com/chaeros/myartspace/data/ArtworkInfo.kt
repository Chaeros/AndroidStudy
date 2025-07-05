package com.chaeros.myartspace.data

import com.chaeros.myartspace.R

data class ArtworkInfo(
    val imageResId: Int,
    val imageDescriptionId: Int,
    val titleResId: Int,
    val artistResId: Int,
    val dateResId: Int
)

val imageList = listOf(
    ArtworkInfo(
        imageResId = R.drawable.the_starry_night,
        imageDescriptionId = R.string.the_starry_night_description,
        titleResId = R.string.the_starry_night,
        artistResId = R.string.the_starry_night_artist,
        dateResId = R.string.the_starry_night_date
    ),
    ArtworkInfo(
        imageResId = R.drawable.mona_lisa,
        imageDescriptionId = R.string.mona_lisa_description,
        titleResId = R.string.mona_lisa,
        artistResId = R.string.mona_lisa_artist,
        dateResId = R.string.mona_lisa_date
    ),
    ArtworkInfo(
        imageResId = R.drawable.cafe_terrace_at_night,
        imageDescriptionId = R.string.cafe_terrace_at_night_description,
        titleResId = R.string.cafe_terrace_at_night,
        artistResId = R.string.cafe_terrace_at_night_artist,
        dateResId = R.string.cafe_terrace_at_night_date
    )
)