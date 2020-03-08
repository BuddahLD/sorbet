package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.util.SingleLiveEvent

class DeleteChatRoomDialogFragment : DialogFragment() {

    private val _liveDeleteDialog = SingleLiveEvent<StateDeleteDialog>()
    val liveDeleteDialog: LiveData<StateDeleteDialog>
        get() = _liveDeleteDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.delete_chat_room)
                .setPositiveButton(R.string.delete,
                    DialogInterface.OnClickListener { dialog, id ->
                        _liveDeleteDialog.value = StateDeleteDialog.OnDelete
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        dismiss()
                    })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    sealed class StateDeleteDialog {
        object OnDelete : StateDeleteDialog()
    }
}
