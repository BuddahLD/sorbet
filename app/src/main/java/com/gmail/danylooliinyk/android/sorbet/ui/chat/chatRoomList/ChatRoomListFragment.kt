package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.danylooliinyk.android.base.extension.observe
import com.gmail.danylooliinyk.android.base.extension.scopeViewModel
import com.gmail.danylooliinyk.android.base.view.adapter.BaseViewHolder
import com.gmail.danylooliinyk.android.base.view.adapter.DelegateAdapter
import com.gmail.danylooliinyk.android.base.view.fragment.BaseFragment
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.adapter.ChatRoomItem
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel.ChatRoomListVM
import com.gmail.danylooliinyk.android.sorbet.util.UiUtils
import com.virgilsecurity.android.base.view.adapter.DelegateAdapterItem
import com.virgilsecurity.android.base.view.adapter.DiffCallback

/**
 * ChatRoomListFragment
 */
class ChatRoomListFragment : BaseFragment(R.layout.fragment_chat_room_list) {

    private val vm: ChatRoomListVM by scopeViewModel()
    private lateinit var adapter: DelegateAdapter<ChatRoom>
    private lateinit var mldChatRoomItem: MutableLiveData<ChatRoomItem.Action>
    private lateinit var controller: NavController

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

        this.controller =
            Navigation.findNavController(activity as Activity, R.id.my_nav_host_fragment)
    }

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        with(view) {
            val rvChatRooms = findViewById<RecyclerView>(R.id.rvChatRooms)
            rvChatRooms.adapter = this@ChatRoomListFragment.adapter
            rvChatRooms.layoutManager = LinearLayoutManager(context)

            val fab: View = findViewById(R.id.fabNewChat)
            fab.setOnClickListener {
                vm.addRandomChatRoom()
            }

        }
    }

    override fun initData() {
        vm.getChatRooms()

        observe(vm.getState(), ::onStateChanged)
        observe(mldChatRoomItem, ::onActionChanged)
    }

    private fun onStateChanged(state: ChatRoomListVM.State): Unit = when (state) {
        is ChatRoomListVM.State.OnLoading -> Unit // TODO add animation of Loading to content
        is ChatRoomListVM.State.OnGetChatRoomsSuccess -> adapter.swapData(state.chatRooms)
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
            controller.navigate(R.id.action_chatRoomListFragment_to_chatRoomFragment)
        }
    }
}
