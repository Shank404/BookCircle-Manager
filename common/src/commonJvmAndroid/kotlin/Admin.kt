import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hsflensburg.common.client
import de.hsflensburg.common.navigateOverview
import de.hsflensburg.model.User
import kotlinx.coroutines.launch

var listOfUsers = mutableStateListOf<User>()
@Composable
fun AdminPage(){
    var scope = rememberCoroutineScope()

    fun getAllUsers(){
        listOfUsers.clear()
        scope.launch {
            listOfUsers.addAll(client.getAllUsers())
        }
    }
    getAllUsers()


    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Admin Interface", fontSize = 30.sp)
        Box(Modifier.weight(1F)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text("Username")
                Text("Rolle")
                Text("Bearbeiten")
            }
            UserList(
                user = listOfUsers
            )
        }
        Button(onClick = ::navigateOverview){
            Text("Zurück zur Overview")
        }
    }
}
@Composable
private fun UserList(
    user: List<User>
) {
    Box {
        val listState = rememberLazyListState()
        LazyColumn(state = listState) {
            items(user) {
                User(
                    user = it
                )
                Divider()
            }
        }
    }
}
@Composable
private fun User(
    user: User
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = AnnotatedString(user.username),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = AnnotatedString(user.role),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(8.dp))

        AlertDialogSampleUser(user)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlertDialogSampleUser(user : User) {
    Column {
        val openDialog = remember { mutableStateOf(false)  }
        var scope = rememberCoroutineScope()
        var message = remember { mutableStateOf("") }

        Button(onClick = {
            openDialog.value = true
        }) {
            Text("verwalten")
        }

        if (openDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Admin Interface")
                },
                text = {
                    Text("Hier kann der gewählte User befördert oder gelöscht werden.")
                },
                confirmButton = {
                    Button(

                        onClick = {
                            openDialog.value = false
                            scope.launch {
                                client.deleteUser(user)
                                listOfUsers.remove(user)
                            }

                        }) {
                        Text(user.username + " löschen?")
                    }
                },
                dismissButton = {
                    Button(

                        onClick = {
                            openDialog.value = false
                            scope.launch {
                                if(user.role == "Admin"){
                                    client.editUserRole(user,"User")
                                } else {
                                    client.editUserRole(user,"Admin")
                                }
                            }
                        }) {
                        if(user.role == "Admin"){
                            message.value = user.username + " zum einfachen User degradieren?"
                        } else {
                            message.value = user.username + " zum Admin befördern?"
                        }
                        Text(message.value)
                    }
                }
            )
        }
    }
}