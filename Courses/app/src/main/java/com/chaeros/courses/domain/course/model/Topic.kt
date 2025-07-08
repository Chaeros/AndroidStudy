package com.chaeros.courses.domain.course.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes val stringResourceId: Int,
    val relatedCourseCount: Int,
    @DrawableRes val imageResourceId: Int
)