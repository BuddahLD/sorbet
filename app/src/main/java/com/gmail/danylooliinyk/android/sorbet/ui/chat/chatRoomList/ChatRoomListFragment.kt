package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_chat_room_list.*

/**
 * ChatRoomListFragment
 */
class ChatRoomListFragment : BaseFragment(R.layout.fragment_chat_room_list) {

    private val vm: ChatRoomListVM by scopeViewModel()
    private lateinit var adapter: DelegateAdapter<ChatRoom>
    private lateinit var liveChatRoomItem: SingleLiveEvent<ChatRoomItem.Action>

    private lateinit var tvTitle: TextView
    private lateinit var pbLoading: View
    private lateinit var fabAddChatRoom: FloatingActionButton
    private lateinit var tvNoChatRooms: View

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

            this@ChatRoomListFragment.fabAddChatRoom = findViewById(R.id.fabNewChat)
            fabAddChatRoom.setOnClickListener {
                lockUi(true)
                vm.addRandomChatRoom()
            }
            this@ChatRoomListFragment.tvTitle = findViewById(R.id.tvTitle)
            this@ChatRoomListFragment.pbLoading = findViewById(R.id.pbLoading)
            this@ChatRoomListFragment.tvNoChatRooms = findViewById(R.id.tvNoChatRooms)
        }

        setupToolbar()
    }

    override fun initData() {
        if (adapter.itemCount == 0)
            vm.getChatRooms()
    }

    private fun setupToolbar() {
        tvTitle.text = getString(R.string.title)
    }

    private fun onGetChatRoomsStateChanged(state: ChatRoomListVM.StateGetChatRooms) {
        when (state) {
            is ChatRoomListVM.StateGetChatRooms.OnLoading -> showLoading(pbLoading, true)
            is ChatRoomListVM.StateGetChatRooms.OnGetChatRoomsSuccess -> {
                showLoading(pbLoading, false)
                if (state.chatRooms.isNotEmpty()) {
                    tvNoChatRooms.visibility = View.INVISIBLE
                    adapter.swapData(state.chatRooms)
                } else {
                    if(adapter.itemCount != 0)
                        adapter.clearItems()

                    tvNoChatRooms.visibility = View.VISIBLE
                }
            }
            is ChatRoomListVM.StateGetChatRooms.OnGetChatRoomsError -> {
                showLoading(pbLoading, false)
                UiUtils.showSnackbar(
                    requireView(),
                    state.throwable.localizedMessage
                        ?: "Unhandled error. Contact developer, please."
                )
            }
        }
    }

    private fun onAddChatRoomStateChanged(state: ChatRoomListVM.StateAddChatRoom) =
        when (state) {
            is ChatRoomListVM.StateAddChatRoom.OnLoading -> showLoading(pbLoading, true)
            is ChatRoomListVM.StateAddChatRoom.OnChatRoomAdded -> {
                lockUi(false)
                rvChatRooms.smoothScrollToPosition(0)
            }
            is ChatRoomListVM.StateAddChatRoom.OnGetChatRoomsError -> {
                showLoading(pbLoading, false)
                lockUi(false)
                UiUtils.showSnackbar(
                    requireView(),
                    state.throwable.localizedMessage
                        ?: "Unhandled error. Contact developer, please."
                )
            }
        }

    private fun onActionChanged(action: ChatRoomItem.Action) = when (action) {
        is ChatRoomItem.Action.ChatRoomClicked -> {
            val direction =
                ChatRoomListFragmentDirections.actionChatRoomListFragmentToChatRoomFragment(action.chatRoom)
            findNavController().navigate(direction)
        }
    }

    private fun lockUi(lock: Boolean) {
        val color = if (lock) {
            ContextCompat.getColor(requireContext(), R.color.gray_middle)
        } else {
            ContextCompat.getColor(requireContext(), R.color.accent)
        }
        fabAddChatRoom.backgroundTintList = ColorStateList.valueOf(color)

        fabAddChatRoom.isEnabled = !lock
        fabAddChatRoom.isClickable = !lock
    }
}
