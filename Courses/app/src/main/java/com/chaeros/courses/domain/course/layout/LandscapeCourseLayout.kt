package com.chaeros.courses.domain.course.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chaeros.courses.domain.course.card.CourseCard
import com.chaeros.courses.domain.course.data.DataSource
import com.chaeros.courses.domain.course.model.Topic

@Composable
fun LandScapeCourseLayout(
    courseList: List<Topic>
) {
    val chunkedList = courseList.chunked(2) // list 2개씩 나누기

    LazyColumn {
        items(chunkedList) { pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CourseCard(
                    topic = pair[0],
                    modifier = Modifier
                        .weight(1f)
                )
                if (pair.size > 1) {
                    CourseCard(
                        topic = pair[1],
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LandScapeCourseLayoutPreview(){
    val list = DataSource.topics

    LandScapeCourseLayout(list)
}