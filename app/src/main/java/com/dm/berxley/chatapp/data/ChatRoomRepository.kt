package com.dm.berxley.chatapp.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ChatRoomRepository(private val firestore: FirebaseFirestore) {
    suspend fun createChatRoom(name: String): Result<Unit> = try {
        val chatRoom = ChatRoom(name = name)
        firestore.collection("rooms").add(chatRoom).await()
        Result.Success(Unit)

    } catch (e: Exception) {
        Result.Error(e)
    }


    suspend fun getRooms(): Result<List<ChatRoom>> = try {
        val querySnapshot = firestore.collection("rooms").get().await()
        val chatRooms  = querySnapshot.map {
            queryDocumentSnapshot ->
            queryDocumentSnapshot.toObject(ChatRoom::class.java).copy(id = queryDocumentSnapshot.id)
        }
        Result.Success(chatRooms)
    }catch (e:Exception){
        Result.Error(e)
    }
}