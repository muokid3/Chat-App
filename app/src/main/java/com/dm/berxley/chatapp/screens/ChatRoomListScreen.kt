package com.dm.berxley.chatapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dm.berxley.chatapp.data.ChatRoom
import com.dm.berxley.chatapp.viewmodels.ChatRoomsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomListScreen(
    roomViewModel: ChatRoomsViewModel = viewModel(),
    onJoinClicked: (ChatRoom) -> Unit
) {
    val chatRooms by roomViewModel.rooms.observeAsState(emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Chat Rooms", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))



        LazyColumn {
            items(chatRooms) { room ->
                RoomItem(chatRoom = room, onJoinClicked = { onJoinClicked(room) })
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                showDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Create Room")
        }

        if (showDialog) {
            AlertDialog(onDismissRequest = { showDialog = false }) {
                Surface(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Create a new room", fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            label = {
                                Text(text = "ChatRoom Name")
                            },
                            value = name,
                            onValueChange = {
                                name = it
                            },
                            singleLine = true,
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedButton(
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(end = 4.dp),
                                onClick = {
                                    name = ""
                                    showDialog = false
                                }) {
                                Text(text = "Cancel")
                            }

                            Button(
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(start = 4.dp),
                                onClick = {
                                    if (name.isNotBlank()) {
                                        //create a chatroom here
                                        roomViewModel.createRoom(name)
                                        name = ""
                                        showDialog = false
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Please enter the ChatRoom name",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }) {
                                Text(text = "Add")
                            }
                        }
                    }
                }

            }
        }
    }

}


@Composable
fun RoomItem(
    chatRoom: ChatRoom,
    onJoinClicked: (ChatRoom) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = chatRoom.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )

        OutlinedButton(onClick = {
            onJoinClicked(chatRoom)
        }) {
            Text(text = "Join")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatRoomsScreenPrev() {
    ChatRoomListScreen(){}
}