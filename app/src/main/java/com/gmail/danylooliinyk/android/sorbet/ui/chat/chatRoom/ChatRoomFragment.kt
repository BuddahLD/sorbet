package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.danylooliinyk.android.base.extension.observe
import com.gmail.danylooliinyk.android.base.extension.scopeViewModel
import com.gmail.danylooliinyk.android.base.view.adapter.BaseViewHolder
import com.gmail.danylooliinyk.android.base.view.adapter.DelegateAdapter
import com.gmail.danylooliinyk.android.base.view.adapter.DelegateAdapterItem
import com.gmail.danylooliinyk.android.base.view.adapter.DiffCallback
import com.gmail.danylooliinyk.android.base.view.fragment.BaseFragment
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.model.Message
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.adapter.MessageItemMe
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.adapter.MessageItemYou
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel.ChatRoomVM
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.adapter.ChatRoomItem
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel.ChatRoomListVM
import com.gmail.danylooliinyk.android.sorbet.util.UiUtils
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

/**
 * ChatRoomFragment
 */
class ChatRoomFragment : BaseFragment(R.layout.fragment_chat_room) {

    private val vm: ChatRoomVM by scopeViewModel()
    private val auth: FirebaseAuth by inject()

    private lateinit var adapter: DelegateAdapter<Message>

    override fun initObjects(context: Context) {
        val messageMeItem = MessageItemMe(auth)
                as DelegateAdapterItem<BaseViewHolder<Message>, Message>
        val messageYouItem = MessageItemYou(auth)
                as DelegateAdapterItem<BaseViewHolder<Message>, Message>

        val diffCallback = DiffCallback<Message>()
        val adapter = DelegateAdapter.Builder<Message>()
            .add(messageMeItem)
            .add(messageYouItem)
            .diffCallback(diffCallback)
            .build()

        this.adapter = adapter
    }

    override fun initObservers() {
        observe(vm.getState(), ::onStateChanged)
    }

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        with(view) {
            val etMessage = findViewById<EditText>(R.id.etMessage)
            val ivSend = findViewById<ImageView>(R.id.ivSend)
            ivSend.setOnClickListener {
                vm.sendMessage(etMessage.text.toString())
            }
            val rvMessages = findViewById<RecyclerView>(R.id.rvMessages)
            rvMessages.adapter = this@ChatRoomFragment.adapter
            rvMessages.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun initData() {
        vm.chatRoom = ChatRoomFragmentArgs.fromBundle(requireArguments()).chatRoom
        vm.getMessages()
    }

    private fun onStateChanged(state: ChatRoomVM.State): Unit = when (state) {
        is ChatRoomVM.State.OnLoading -> Unit // TODO add animation of Loading to content
        is ChatRoomVM.State.OnGetMessagesSuccess -> adapter.swapData(state.messages) // TODO show empty label when no chat rooms
        is ChatRoomVM.State.OnGetMessagesError -> {
            UiUtils.showSnackbar(
                requireView(),
                state.throwable.localizedMessage
                    ?: "Unhandled error. Contact developer, please."
            )
        }
        is ChatRoomVM.State.OnMessageSent -> Unit
    }
}
