package de.hsflensburg.common

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
actual fun Overview(){
    Div({
        style {
            display(DisplayStyle.Grid)
            gridTemplateRows(".1fr 1fr .1fr")
            gridTemplateColumns("1fr 1fr 1fr")
            gridTemplateAreas(". . .", ". xxx .", ". . .")
            minHeight("100vh")
            justifyItems("center")
            alignItems("center")
        }
    }) {
        Div({
            style {
                gridArea("xxx")
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                alignContent(AlignContent.Center)
                rowGap(20.px)
                width(90.percent)
            }
        }) {
            Div {
                Button(attrs = {
                    style {
                        fontSize(3.em)
                        minWidth(100.percent)
                    }

                    onClick {
                        navigateCreateBook()
                    }
                }) {
                    Text("Create Book")
                }
            }
            Div {
                Button(attrs = {
                    style {
                        fontSize(3.em)
                        minWidth(100.percent)
                    }
                    onClick {
                        navigateMyBooks()
                    }
                }) {
                    Text("My Books")
                }
            }
            Div {
                Button(attrs = {
                    style {
                        fontSize(3.em)
                        minWidth(100.percent)
                    }
                    onClick {
                        navigateAllBooks()
                    }
                }) {
                    Text("All Books")
                }
            }
            Div {
                Button(attrs = {
                    style {
                        fontSize(3.em)
                        minWidth(100.percent)
                    }
                    onClick {
                        navigateLendedBooks()
                    }
                }) {
                    Text("Lended Books")
                }
            }
            Div {
                Button(attrs = {
                    style {
                        fontSize(3.em)
                        minWidth(100.percent)
                    }
                    onClick {
                        navigateLandingPage()
                    }
                }) {
                    Text("Logout")
                }
            }
            if(userRole.value == "Admin"){
                Div {
                    Button(attrs = {
                        style {
                            fontSize(3.em)
                            minWidth(100.percent)
                        }
                        onClick {
                            navigateAdmin()
                        }
                    }) {
                        Text("Account Management for Admin-User")
                    }
                }
            }

        }
    }


}