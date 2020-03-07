package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.adapter

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.base.view.adapter.DelegateAdapterItemDefault
import com.gmail.danylooliinyk.android.sorbet.util.SingleLiveEvent
import de.hdodenhof.circleimageview.CircleImageView

/**
 * ContactItem
 */
class ChatRoomItem(
    private val actionLiveData: SingleLiveEvent<Action>,
    override val layoutResourceId: Int = R.layout.item_chat_room
) : DelegateAdapterItemDefault<ChatRoom>() {

    override fun onBind(item: ChatRoom, viewHolder: KViewHolder<ChatRoom>) =
            with(viewHolder.containerView) {
                val civGroupPic = findViewById<CircleImageView>(R.id.civGroupPic)
                val tvGroupName = findViewById<TextView>(R.id.tvGroupName)
                val tvLastMessage = findViewById<TextView>(R.id.tvLastMessage)
                val tvMembersCount = findViewById<TextView>(R.id.tvMembersCount)

                tvGroupName.text = item.friendlyName
                tvLastMessage.text = item.lastMessage
                tvMembersCount.text = item.membersCount

                Glide.with(context)
                        .load(item.picturePath)
                        .circleCrop()
                        .into(civGroupPic)

                setOnClickListener {
                    actionLiveData.value = Action.ChatRoomClicked(item)
//                    actionLiveData.value = Action.Idle // TODO remove?
                }
            }

    override fun onRecycled(holder: KViewHolder<ChatRoom>) {}

    override fun isForViewType(items: List<*>, position: Int): Boolean =
            items[position] is ChatRoom

    sealed class Action {
        data class ChatRoomClicked(val chatRoom: ChatRoom) : Action()
//        object Idle : Action() // TODO remove?
    }
}
