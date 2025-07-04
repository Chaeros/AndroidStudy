package com.chaeros.myartspace.ui.artwork

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chaeros.myartspace.R
import com.chaeros.myartspace.ui.common.ImageCard
import com.chaeros.myartspace.ui.common.PreNextButtonCard
import com.chaeros.myartspace.ui.common.TitleCard

@Composable
fun ArtworkLayout(
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        ImageCard(
            modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
            painterResource(R.drawable.the_starry_night),"")
        TitleCard(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            mainTitle = stringResource(R.string.the_starry_night),
            artist = stringResource(R.string.the_starry_night_artist),
            date = stringResource(R.string.the_starry_night_date)
        )
        PreNextButtonCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}