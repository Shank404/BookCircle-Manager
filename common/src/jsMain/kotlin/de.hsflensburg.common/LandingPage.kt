package de.hsflensburg.common

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@Composable
actual fun LandingPage(){
    Div({
        style {
            display(DisplayStyle.Grid)
            gridTemplateRows("1fr 1fr")
            gridTemplateAreas("headline", "buttons")
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
                Text("Welcome to BookCircle!")
            }
        }
        Div({
            style {
                gridArea("buttons")
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                rowGap(20.px)
            }
        }) {
            Button(attrs = {
                style {
                    fontSize(2.em)
                    width(250.px)
                }
                onClick {
                    navigateLogin()
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
                    navigateRegister()
                }
            }) {
                Text("Register")
            }
        }
    }
}