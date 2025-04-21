package org.example.llamapp.ui.SignUp


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.llamapp.model.SignUp
import org.example.llamapp.navigation.SignUpScreenComponent
import org.koin.compose.getKoin

@Composable
fun SignUpScreen(component: SignUpScreenComponent) {
    val viewModel: SignUpViewModel = getKoin().get()
    val uiState = viewModel.uiState.collectAsState(SignUpUiState())
    val signUp = viewModel.signUp.collectAsState(SignUp())

    LaunchedEffect(uiState.value.navigateToHomeScreen) {
        if (uiState.value.navigateToHomeScreen) {
            component.navigateToHomeScreen()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 180.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Welcome to LLama AI",
                fontSize = 30.sp,
                color = Color(0, 128, 250),
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(180.dp))

        OutlinedTextField(
            value = signUp.value.Email,
            onValueChange = { viewModel.setEmail(it) },
            label = {
                Text("Email")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0, 128, 250),
                focusedLabelColor = Color(0, 128, 250),
                cursorColor = Color(0, 128, 250)
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = signUp.value.Password,
            onValueChange = { viewModel.setPassword(it) },
            label = {
                Text("Password")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0, 128, 250),
                focusedLabelColor = Color(0, 128, 250),
                cursorColor = Color(0, 128, 250)
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        ClickableText(
            text = AnnotatedString("Already have an account?"),
            onClick = {
                component.navigateToLoginScreen()
            }
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            modifier = Modifier.size(width = 277.dp, height = 50.dp),
            onClick = {
                viewModel.signUp()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0, 128, 250),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(90.dp)
        ) {
            Text("Sign Up")
        }
    }
}