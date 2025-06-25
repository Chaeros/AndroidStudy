package com.chaeros.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chaeros.businesscard.ui.theme.BusinessCardTheme
import com.chaeros.businesscard.ui.theme.Navy

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardTheme {
                Scaffold() { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Page(stringResource(R.string.name), stringResource(R.string.my_description))
                    }
                }
            }
        }
    }
}

@Composable
fun NameCard(name: String, description: String, modifier: Modifier = Modifier){
    val image = painterResource(R.drawable.android_logo)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(top=200.dp)
    ) {
        Image(
            painter = image,
            contentDescription = stringResource(R.string.contentdescription_business_card_logo_image),
            modifier = Modifier
                .background(Navy)
                .size(200.dp)
        )
        Text(
            text=name,
            fontSize = 52.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text=description,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ContactCards(){
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(bottom = 60.dp)
    ) {
        ContactCard(painterResource(R.drawable.call_icon), stringResource(R.string.cell_phone_number))
        ContactCard(painterResource(R.drawable.share_icon), stringResource(R.string.nickname))
        ContactCard(painterResource(R.drawable.email_icon), stringResource(R.string.email))
    }
}

@Composable
fun ContactCard(image: Painter, contact: String, modifier: Modifier = Modifier){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 100.dp, bottom = 10.dp, end = 10.dp)
    ) {
        Image(
            painter = image,
            contentDescription = null,
            Modifier.size(23.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = contact,
            fontSize = 18.sp
        )
    }
}

@Composable
fun Page(name: String, description: String, modifier: Modifier = Modifier){
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ){
        NameCard(name,description)
        ContactCards()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BusinessCardTheme {
        Page(stringResource(R.string.name), stringResource(R.string.my_description))
    }
}