package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
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
import com.gmail.danylooliinyk.android.sorbet.util.SingleLiveEvent
import com.gmail.danylooliinyk.android.sorbet.util.UiUtils

/**
 * ChatRoomListFragment
 */
class ChatRoomListFragment : BaseFragment(R.layout.fragment_chat_room_list) {

    private val vm: ChatRoomListVM by scopeViewModel()
    private lateinit var adapter: DelegateAdapter<ChatRoom>
    private lateinit var liveChatRoomItem: SingleLiveEvent<ChatRoomItem.Action>

    private lateinit var tvTitle: TextView
    private lateinit var pbLoading: ProgressBar

    override fun initObjects(context: Context) {
        this.liveChatRoomItem = SingleLiveEvent()
        val chatRoomItem = ChatRoomItem(liveChatRoomItem)
                as DelegateAdapterItem<BaseViewHolder<ChatRoom>, ChatRoom>

        val diffCallback = DiffCallback<ChatRoom>()
        val adapter = DelegateAdapter.Builder<ChatRoom>()
            .add(chatRoomItem)
            .diffCallback(diffCallback)
            .build()

        this.adapter = adapter
    }

    override fun initObservers() {
        observe(vm.liveGetChatRoom, ::onGetChatRoomsStateChanged)
        observe(vm.liveAddChatRoom, ::onAddChatRoomStateChanged)
        observe(liveChatRoomItem, ::onActionChanged)
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
            this@ChatRoomListFragment.tvTitle = findViewById(R.id.tvTitle)
            this@ChatRoomListFragment.pbLoading = findViewById(R.id.pbLoading)
        }

        setupToolbar()
    }

    override fun initData() {
        if (adapter.itemCount == 0) // TODO check whether data is loaded from local cache first
            vm.getChatRooms()
    }

    private fun setupToolbar() {
        tvTitle.text = getString(R.string.title)
    }

    private fun onGetChatRoomsStateChanged(state: ChatRoomListVM.StateGetChatRooms) =
        when (state) {
            is ChatRoomListVM.StateGetChatRooms.OnLoading -> showLoading(pbLoading, true)
            is ChatRoomListVM.StateGetChatRooms.OnGetChatRoomsSuccess -> {
                showLoading(pbLoading, false)
                adapter.swapData(state.chatRooms)
            } // TODO show empty label when no chat rooms
            is ChatRoomListVM.StateGetChatRooms.OnGetChatRoomsError -> {
                showLoading(pbLoading, false)
                UiUtils.showSnackbar(
                    requireView(),
                    state.throwable.localizedMessage
                        ?: "Unhandled error. Contact developer, please."
                )
            }
        }

    private fun onAddChatRoomStateChanged(state: ChatRoomListVM.StateAddChatRoom) =
        when (state) {
            is ChatRoomListVM.StateAddChatRoom.OnLoading -> showLoading(pbLoading, true)
            is ChatRoomListVM.StateAddChatRoom.OnChatRoomAdded -> Unit
            is ChatRoomListVM.StateAddChatRoom.OnGetChatRoomsError -> {
                showLoading(pbLoading, false)
                UiUtils.showSnackbar(
                    requireView(),
                    state.throwable.localizedMessage
                        ?: "Unhandled error. Contact developer, please."
                )
            }
        }

    private fun onActionChanged(action: ChatRoomItem.Action) = when (action) {
        is ChatRoomItem.Action.ChatRoomClicked -> { // TODO add animation of fragment change

            val direction =
                ChatRoomListFragmentDirections.actionChatRoomListFragmentToChatRoomFragment(action.chatRoom)
            findNavController().navigate(direction)
        }
    }
}
