package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.adapter

import android.widget.TextView
import com.gmail.danylooliinyk.android.base.view.adapter.DelegateAdapterItemDefault
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.data.model.Message
import com.google.firebase.auth.FirebaseAuth

/**
 * MessageItemMe
 */
class MessageItemYou(
    private val auth: FirebaseAuth,
    override val layoutResourceId: Int = R.layout.item_message_you
) : DelegateAdapterItemDefault<Message>() {

    override fun onBind(item: Message, viewHolder: KViewHolder<Message>) =
        with(viewHolder.containerView) {
            findViewById<TextView>(R.id.tvMessage).text = item.body
        }

    override fun onRecycled(holder: KViewHolder<Message>) {}

    override fun isForViewType(items: List<*>, position: Int): Boolean =
        (items[position] as Message).sender != auth.currentUser!!.uid
}
