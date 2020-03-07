package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.adapter

import android.widget.TextView
import com.gmail.danylooliinyk.android.base.view.adapter.DelegateAdapterItemDefault
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.data.model.Message
import com.google.firebase.auth.FirebaseAuth
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * MessageItemMe
 */
class MessageItemMe(
    private val auth: FirebaseAuth,
    override val layoutResourceId: Int = R.layout.item_message_me
) : DelegateAdapterItemDefault<Message>() {

    override fun onBind(item: Message, viewHolder: KViewHolder<Message>) =
            with(viewHolder.containerView) {
                findViewById<TextView>(R.id.tvMessage).text = item.body

                val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                val timestamp = dateFormat.format(item.createdAt.toDate())
                findViewById<TextView>(R.id.tvMessageTimestamp).text = timestamp
            }

    override fun onRecycled(holder: KViewHolder<Message>) {}

    override fun isForViewType(items: List<*>, position: Int): Boolean =
            (items[position] as Message).sender == auth.currentUser!!.uid
}
