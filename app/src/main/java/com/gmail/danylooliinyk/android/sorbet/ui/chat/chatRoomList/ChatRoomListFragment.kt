package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.api.firestore.FirestoreApi
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * ChatRoomListFragment
 */
class ChatRoomListFragment : Fragment(R.layout.fragment_chat_room_list) {

    private val firestoreApi: FirestoreApi by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            val rvChatRooms = findViewById<ImageView>(R.id.rvChatRooms)
//            val clickListener =
//                Navigation.createNavigateOnClickListener(R.id.action_chatRoomListFragment_to_chatRoomFragment)
//            rvChatRooms.setOnClickListener(clickListener)
            rvChatRooms.setOnClickListener {
                lifecycleScope.launch {
                    firestoreApi.addRandomChatRoom()
                }
            }

            Glide.with(this@ChatRoomListFragment)
                .load("https://eu.ui-avatars.com/api/?name=O+P&rounded=true&background=FF7E7E")
                .into(rvChatRooms)
        }

        lifecycleScope.launch {
            firestoreApi.anonymousSignIn()

//            for (i in 0 until 10)
//                firestoreApi.addRandomChatRoom()

            firestoreApi.getChatRooms().collect {
                processResult(it)
            }
        }
    }

    private suspend fun processResult(querySnapshot: QuerySnapshot) {
        val allChatRooms = querySnapshot.toObjects(ChatRoom::class.java)
        val changedChatRooms = querySnapshot.documentChanges
            .map {
                it.document.toObject(ChatRoom::class.java)
            }

//        result.forEach {
//            for (i in 0 until 6)
//                firestoreApi.addRandomMessage(it.id)
//        }
    }
}
