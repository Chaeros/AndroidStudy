package com.chaeros.courses.domain.course.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chaeros.courses.R
import com.chaeros.courses.domain.course.data.DataSource
import com.chaeros.courses.domain.course.model.Topic
import com.chaeros.courses.ui.theme.Purple80

@Composable
fun CourseCard(
    topic: Topic,
    modifier: Modifier = Modifier
){
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(Purple80)
        ) {
            Image(
                painter = painterResource(topic.imageResourceId),
                contentDescription = stringResource(topic.stringResourceId),
                modifier = Modifier
                    .width(68.dp)
                    .height(68.dp)
            )
            Column(
                modifier = Modifier
                    .background(Purple80)
                    .fillMaxWidth()
                    .height(68.dp)
            ) {
                Text(
                    text = stringResource(topic.stringResourceId),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_grain),
                        contentDescription = stringResource(R.string.ic_grain),
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .height(20.dp)
                            .width(20.dp)
                    )
                    Text(
                        text = topic.relatedCourseCount.toString(),
                        modifier = Modifier
                            .padding(start = 8.dp),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CourseCardPreview(){
    val list = DataSource.topics

    LazyColumn {
        items(list) { course ->
            CourseCard(course)
        }
    }
}