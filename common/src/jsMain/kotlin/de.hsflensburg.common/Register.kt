package de.hsflensburg.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import de.hsflensburg.model.User
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
actual fun Register() {
    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var error = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    fun createUser(){
        scope.launch {
            val response : HttpResponse = client.createUser(User(username.value, password.value, "User"))
            if(response.status == HttpStatusCode.Created){
                username.value = ""
                password.value = ""
                error.value = "Erfolgreich registriert!"
            } else {
                error.value = "Existiert bereits!"
            }
        }
    }

    Div({
        style {
            display(DisplayStyle.Grid)
            gridTemplateRows("1fr 1.5fr 1fr")
            gridTemplateAreas("headline", "inputs", "buttons")
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
                Text("Register")
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
                        username.value = it.value
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
                        password.value = it.value
                    }
                    style {
                        width(200.px)
                        height(40.px)
                    }
                    placeholder("Password")
                }
            )
            Text(error.value)
        }
        Div({
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                rowGap(20.px)
                gridArea("buttons")
            }
        }) {
            Button(attrs = {
                style {
                    fontSize(2.em)
                    width(250.px)
                }
                onClick {
                    createUser()
                }
            }) {
                Text("Register")
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