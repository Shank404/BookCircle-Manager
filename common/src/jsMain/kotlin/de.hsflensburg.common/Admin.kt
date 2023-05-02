package de.hsflensburg.common

import androidx.compose.runtime.*
import de.hsflensburg.model.User
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text


@Composable
actual fun Admin() {
    var listOfUsers = mutableStateListOf<User>()
    val scope = rememberCoroutineScope()

    fun getAllUsers() {
        listOfUsers.clear()
        scope.launch {
            listOfUsers.addAll(client.getAllUsers())
        }
    }

    fun checkRoleAndEdit(user : User) {
        scope.launch{
            if (user.role == "Admin") {
                client.editUserRole(user, "User")
            } else {
                client.editUserRole(user, "Admin")
            }
        }
    }
    fun deleteUser(user : User) {
        scope.launch {
            client.deleteUser(user)
            listOfUsers.remove(user)
        }
    }

    getAllUsers()
    Div({
        style {
            display(DisplayStyle.Grid)
            gridTemplateRows(".2fr 1fr .2fr")
            gridTemplateAreas(". headline .", ". content .", ". buttons .")
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
                Text("Admin-Interface")
            }
        }

        Div({
            style {
                gridArea("content")
            }
        }) {
            for (user in listOfUsers) {
                Div({
                    style {
                        display(DisplayStyle.Flex)
                        flexDirection(FlexDirection.Column)
                        border(3.px, LineStyle.Solid, Color.black)
                        borderRadius(3.em)
                        padding(2.em)
                    }
                }) {
                    Text("Username: " + user.username)
                    Text("Role: " + user.role)
                    Button(attrs = {
                        style {
                            fontSize(1.em)
                        }
                        onClick {
                            checkRoleAndEdit(user)
                        }
                    }) {
                        Text("Change role")
                    }

                    Button(attrs = {
                        style {
                            fontSize(1.em)
                        }
                        onClick {
                            deleteUser(user)
                        }
                    }) {
                        Text("Delete User")
                    }
                }
            }
        }

        Div({
            style {
                gridArea("buttons")
            }
        }) {
            Button(attrs = {
                style {
                    fontSize(2.em)
                    width(250.px)
                }
                onClick {
                    navigateOverview()
                }
            }) {
                Text("Back")
            }
        }

    }
}