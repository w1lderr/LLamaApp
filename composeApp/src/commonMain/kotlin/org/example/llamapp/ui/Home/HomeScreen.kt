package org.example.llamapp.ui.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.getKoin

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = getKoin().get()
    val message = viewModel.message.collectAsState("")
    val list = viewModel.listOfMessages.collectAsState(emptyList())
    val listState = rememberLazyListState()

    LaunchedEffect(list.value.size) {
        if (list.value.isNotEmpty()) {
            listState.animateScrollToItem(list.value.size - 1)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "LLama",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0,128, 250)
            )

            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Delete Icon",
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .clickable {
                        viewModel.deleteMessages()
                    },
                tint = Color(0, 128, 250)
            )
        }

        list.value.let {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(list.value) { _, message ->
                    MessageItem(message)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 3.dp,
                        color = Color(0, 128, 250),
                        shape = RoundedCornerShape(90.dp)
                    )
                    .padding(3.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    modifier = Modifier.weight(1f).height(60.dp),
                    value = message.value,
                    onValueChange = { viewModel.setMessage(it) },
                    label = {
                        Text(
                            text = "Ask Llama",
                            color = Color(120, 120, 120)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        cursorColor = Color(0, 128, 250),
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                )

                Button(
                    modifier = Modifier.size(height = 60.dp, width = 60.dp),
                    onClick = {
                        viewModel.addMessageToTheList(message.value)
                        viewModel.TalkToLlama()
                        viewModel.setMessage("")
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0, 128, 250)
                    ),
                    shape = RoundedCornerShape(90.dp),
                    enabled = message.value.isNotEmpty()
                ) {
                    Icon(
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp),
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Send Icon",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun MessageItem(
    message: String
) {

    Row(
        modifier = Modifier
            .width(450.dp)
            .border(
                width = 3.dp,
                color = Color(0, 128, 250),
                shape = RoundedCornerShape(20.dp)
            )
            .background(Color.White)
            .padding(17.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = message,
            color = Color(0, 128, 250),
        )
    }
    Spacer(modifier = Modifier.height(25.dp))
}