package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.api.FirestoreApi
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
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

        lifecycleScope.launch {
            firestoreApi.getChatRooms().collect {
                val result = it.toObjects(ChatRoom::class.java)
                Log.d("ChatRoomListFragment", "Current chat room size: $result.size")
            }
        }
    }
}
