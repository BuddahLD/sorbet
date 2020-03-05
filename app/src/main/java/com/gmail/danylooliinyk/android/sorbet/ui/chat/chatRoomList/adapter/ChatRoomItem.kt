/*
 * Copyright (c) 2015-2018, Virgil Security, Inc.
 *
 * Lead Maintainer: Virgil Security Inc. <support@virgilsecurity.com>
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     (1) Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *
 *     (2) Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *     (3) Neither the name of virgil nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.adapter

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.virgilsecurity.android.base.view.adapter.DelegateAdapterItemDefault
import de.hdodenhof.circleimageview.CircleImageView

/**
 * . _  _
 * .| || | _
 * -| || || |   Created by:
 * .| || || |-  Danylo Oliinyk
 * ..\_  || |   on
 * ....|  _/    7/27/18
 * ...-| | \    at Virgil Security
 * ....|_|-
 */

/**
 * ContactItem
 */
class ChatRoomItem(
        private val actionLiveData: MutableLiveData<Action>,
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
