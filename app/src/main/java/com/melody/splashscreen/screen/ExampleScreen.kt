package com.melody.splashscreen.screen

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.melody.splashscreen.R

@Composable
fun ExampleScreen(killApp: () -> Unit) {
    Column {
        Text(
            color = contentColorFor(backgroundColor = MaterialTheme.colors.background),
            text = String.format(
                stringResource(id = R.string.example_content),
                Build.VERSION.RELEASE
            ), fontSize = 60.sp, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 200.dp), textAlign = TextAlign.Center
        )
        Button(
            onClick = {
                killApp()
            }, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 50.dp)
        ) {
            Text(text = stringResource(id = R.string.example_kill_app))
        }
    }
}