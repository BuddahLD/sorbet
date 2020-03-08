package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomEdit

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gmail.danylooliinyk.android.base.extension.observe
import com.gmail.danylooliinyk.android.base.extension.scopeViewModel
import com.gmail.danylooliinyk.android.base.view.fragment.BaseFragmentBinding
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.databinding.FragmentChatRoomEditBinding
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomEdit.viewmodel.ChatRoomEditVM
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomEdit.viewmodel.ChatRoomEditVMDefault
import com.gmail.danylooliinyk.android.sorbet.util.UiUtils
import de.hdodenhof.circleimageview.CircleImageView

/**
 * ChatRoomEditFragment
 */
class ChatRoomEditFragment : BaseFragmentBinding(R.layout.fragment_chat_room_edit) {

    private val vm: ChatRoomEditVM by scopeViewModel()

    private lateinit var civGroupPic: CircleImageView
    private lateinit var fabConfirmChanges: View
    private lateinit var ivBack: View // TODO change everywhere to View
    private lateinit var pbLoading: View
    private lateinit var etGroupName: EditText

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        layoutResourceId: Int
    ): View {
        val binding: FragmentChatRoomEditBinding = DataBindingUtil.inflate(
            inflater,
            layoutResourceId,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.chatRoomEditVM = vm as ChatRoomEditVMDefault

        return binding.root
    }

    override fun initObjects(context: Context) {}

    override fun initObservers() {
        observe(vm.liveChatRoomEditing, ::onStateChatRoomEditingChanged)
        observe(vm.liveChatRoomEdit, ::onStateChatRoomEditChanged)
    }

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        with(view) {
            this@ChatRoomEditFragment.civGroupPic = findViewById(R.id.civGroupPic)
            this@ChatRoomEditFragment.fabConfirmChanges = findViewById(R.id.fabConfirmChanges)
            this@ChatRoomEditFragment.pbLoading = findViewById(R.id.pbLoading)
            this@ChatRoomEditFragment.ivBack = findViewById(R.id.ivBack)
            this@ChatRoomEditFragment.etGroupName = findViewById(R.id.etGroupName)

            val chatRoom = ChatRoomEditFragmentArgs.fromBundle(requireArguments()).chatRoom
            Glide.with(this)
                .load(chatRoom.picturePath)
                .centerCrop()
                .into(civGroupPic)
            val newEditable = Editable.Factory.getInstance().newEditable(chatRoom.friendlyName)
            etGroupName.post {
                etGroupName.text = newEditable
            }

            fabConfirmChanges.setOnClickListener {
                vm.editChatRoom()
            }
        }
        setupToolbar()
    }

    override fun initData() {
        vm.currentChatRoom = ChatRoomEditFragmentArgs.fromBundle(requireArguments()).chatRoom
    }

    private fun setupToolbar() {
        ivBack.setOnClickListener {
            hideKeyboard()
            activity?.onBackPressed() // TODO crash on many times open chat room, then press back
        }
    }

    private fun onStateChatRoomEditingChanged(state: ChatRoomEditVM.StateChatRoomEditing) =
        when (state) {
            is ChatRoomEditVM.StateChatRoomEditing.OnChatRoomEdited -> {
                fabConfirmChanges.visibility = View.VISIBLE
            }
            is ChatRoomEditVM.StateChatRoomEditing.OnChatRoomNoChanges -> {
                fabConfirmChanges.visibility = View.INVISIBLE // TODO add show animation
            }
        }

    private fun onStateChatRoomEditChanged(state: ChatRoomEditVM.StateChatRoomEdit) = when (state) {
        is ChatRoomEditVM.StateChatRoomEdit.OnLoading -> showLoading(pbLoading, true)
        is ChatRoomEditVM.StateChatRoomEdit.OnChatRoomEditSuccess -> {
            showLoading(pbLoading, false)
            fabConfirmChanges.visibility = View.INVISIBLE
        }
        is ChatRoomEditVM.StateChatRoomEdit.OnChatRoomDeleteError -> {
            showLoading(pbLoading, false)
            UiUtils.showSnackbar(
                requireView(),
                state.throwable.localizedMessage
                    ?: "Unhandled error. Contact developer, please."
            )
        }
    }
}
