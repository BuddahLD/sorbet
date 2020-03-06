package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
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
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.adapter.ChatRoomItem
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel.ChatRoomListVM
import com.gmail.danylooliinyk.android.sorbet.util.UiUtils

/**
 * ChatRoomListFragment
 */
class ChatRoomListFragment : BaseFragment(R.layout.fragment_chat_room_list) {

    private val vm: ChatRoomListVM by scopeViewModel()
    private lateinit var adapter: DelegateAdapter<ChatRoom>
    private lateinit var mldChatRoomItem: MutableLiveData<ChatRoomItem.Action>

    override fun initObjects(context: Context) {
        this.mldChatRoomItem = MutableLiveData()
        val chatRoomItem = ChatRoomItem(mldChatRoomItem)
                as DelegateAdapterItem<BaseViewHolder<ChatRoom>, ChatRoom>

        val diffCallback = DiffCallback<ChatRoom>()
        val adapter = DelegateAdapter.Builder<ChatRoom>()
            .add(chatRoomItem)
            .diffCallback(diffCallback)
            .build()

        this.adapter = adapter
    }

    override fun initObservers() {
        observe(vm.getState(), ::onStateChanged)
        observe(mldChatRoomItem, ::onActionChanged)
    }

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        with(view) {
            val rvChatRooms = findViewById<RecyclerView>(R.id.rvChatRooms)
            rvChatRooms.adapter = this@ChatRoomListFragment.adapter
            rvChatRooms.layoutManager = LinearLayoutManager(context)

            val fab: View = findViewById(R.id.fabNewChat)
            fab.setOnClickListener {
                vm.addRandomChatRoom() // TODO add adding animation item
            }
        }
    }

    override fun initData() {
        vm.getChatRooms()
    }

    private fun onStateChanged(state: ChatRoomListVM.State): Unit = when (state) {
        is ChatRoomListVM.State.OnLoading -> Unit // TODO add animation of Loading to content
        is ChatRoomListVM.State.OnGetChatRoomsSuccess -> adapter.swapData(state.chatRooms) // TODO show empty label when no chat rooms
        is ChatRoomListVM.State.OnGetChatRoomsError -> {
            UiUtils.showSnackbar(
                requireView(),
                state.throwable.localizedMessage
                    ?: "Unhandled error. Contact developer, please."
            )
        }
        is ChatRoomListVM.State.OnChatRoomAdded -> Unit
    }

    private fun onActionChanged(action: ChatRoomItem.Action) = when (action) {
        is ChatRoomItem.Action.ChatRoomClicked -> { // TODO add animation of fragment change

            val action =
                ChatRoomListFragmentDirections.actionChatRoomListFragmentToChatRoomFragment(action.chatRoom)
            findNavController().navigate(action)
        }
    }
}
