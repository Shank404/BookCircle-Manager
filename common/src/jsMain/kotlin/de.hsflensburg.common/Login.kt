package de.hsflensburg.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
actual fun Login() {
    var usernameLogin = remember { mutableStateOf("") }
    var passwordLogin = remember { mutableStateOf("") }
    var error = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    fun login(){
        scope.launch {
            val response = client.login(usernameLogin.value, passwordLogin.value)
            if(response.status == HttpStatusCode.Accepted){
                val token : String = response.body()
                val splittedToken = token.split(".")
                val tokenGlued = splittedToken[0]+"."+splittedToken[1]+"."+splittedToken[2]
                jwt.value = tokenGlued
                username.value = splittedToken[3]
                userUUID.value = splittedToken[4]
                userRole.value = client.getUserByUUID(userUUID.value).role
                navigateOverview()
            } else {
                error.value = "Falsche Anmeldedaten, bitte versuchen Sie es erneut!"
            }
        }
    }
    Div({
        style {
            display(DisplayStyle.Grid)
            gridTemplateRows("1fr 1.5fr 1fr")
            gridTemplateAreas( "headline", "inputs", "buttons")
            minHeight("100vh")
            justifyItems("center")
            alignItems("center")
        }
    }) {
        Div({
            style {
                gridArea("headline")
                fontSize(3.em)
                textAlign("center")
            }
        }) {
            H1 {
                Text("Login")
            }
        }
        Div(attrs = {
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                rowGap(20.px)
                gridArea("inputs")
                fontSize(2.em)
                textAlign("center")
            }
        }) {
            Text("Username")
            Input(
                type = InputType.Text,
                attrs = {
                    id("Username")
                    onInput {
                        usernameLogin.value = it.value
                    }
                    style {
                        width(200.px)
                        height(40.px)
                    }
                    placeholder("Username")
                }
            )
            Text("Password")
            Input(
                type = InputType.Text,
                attrs = {
                    id("Password")
                    onInput {
                        passwordLogin.value = it.value
                    }
                    style {
                        width(200.px)
                        height(40.px)
                    }
                    placeholder("Password")
                }
            )
        }
        Div({
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                rowGap(20.px)
                gridArea("buttons")
            }
        }) {
            Text(error.value)
            Button(attrs = {
                style {
                    fontSize(2.em)
                    width(250.px)
                }
                onClick {
                    login()
                }
            }) {
                Text("Login")
            }
            Button(attrs = {
                style {
                    fontSize(2.em)
                    width(250.px)
                }
                onClick {
                    navigateLandingPage()
                }
            }) {
                Text("Back")
            }
        }
    }

}