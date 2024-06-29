package com.dm.berxley.chatapp.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MessageRepository(private val firestore: FirebaseFirestore) {
    suspend fun sendMessage(roomId: String, message: ChatMessage): Result<Unit> =
        try {
            firestore.collection("rooms")
                .document(roomId)
                .collection("messages")
                .add(message).await()
            Result.Success(Unit)

        } catch (e: Exception) {
            Result.Error(e)
        }

    suspend fun getChatMesages(roomId: String): Flow<List<ChatMessage>> =
        callbackFlow {
            val subscription = firestore.collection("rooms")
                .document(roomId)
                .collection("messages")
                .orderBy("timestamp")
                .addSnapshotListener { querySnapshot, _ ->
                    querySnapshot?.let {
                        trySend(it.documents.map { doc ->
                            doc.toObject(ChatMessage::class.java)!!.copy()
                        }).isSuccess
                    }
                }

            awaitClose { subscription.remove() }
        }
}