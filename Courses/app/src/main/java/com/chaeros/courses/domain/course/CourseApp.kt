package com.chaeros.courses.domain.course

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.chaeros.courses.domain.course.data.DataSource
import com.chaeros.courses.domain.course.layout.LandScapeCourseLayout
import com.chaeros.courses.domain.course.model.Topic

@Composable
fun CourseApp(){
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val courseList :List<Topic> = DataSource.topics

    if(!isLandscape){
        LandScapeCourseLayout(courseList)
    }
}