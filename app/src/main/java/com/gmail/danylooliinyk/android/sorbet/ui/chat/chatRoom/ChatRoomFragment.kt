package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom

import android.content.Context
import android.graphics.Point
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.danylooliinyk.android.base.extension.observe
import com.gmail.danylooliinyk.android.base.extension.scopeViewModel
import com.gmail.danylooliinyk.android.base.view.adapter.BaseViewHolder
import com.gmail.danylooliinyk.android.base.view.adapter.DelegateAdapter
import com.gmail.danylooliinyk.android.base.view.adapter.DelegateAdapterItem
import com.gmail.danylooliinyk.android.base.view.adapter.DiffCallback
import com.gmail.danylooliinyk.android.base.view.fragment.BaseFragmentBinding
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.model.Message
import com.gmail.danylooliinyk.android.sorbet.databinding.FragmentChatRoomBinding
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.adapter.MessageItemMe
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.adapter.MessageItemYou
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.dialog.DeleteChatRoomDialogFragment
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel.ChatRoomVM
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel.ChatRoomVMDefault
import com.gmail.danylooliinyk.android.sorbet.util.UiUtils
import com.gmail.danylooliinyk.android.sorbet.util.view.MenuPopup
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_chat_room.*
import org.koin.android.ext.android.inject

/**
 * ChatRoomFragment
 */
class ChatRoomFragment : BaseFragmentBinding(R.layout.fragment_chat_room) {

    private val vm: ChatRoomVM by scopeViewModel()
    private val auth: FirebaseAuth by inject()

    private lateinit var adapter: DelegateAdapter<Message>
    private lateinit var ivSend: ImageView
    private lateinit var ivBack: View
    private lateinit var civGroupPic: CircleImageView
    private lateinit var tvGroupName: TextView
    private lateinit var ivMore: View
    private lateinit var pbLoading: ProgressBar
    private lateinit var menuPopup: MenuPopup
    private lateinit var deleteDialog: DeleteChatRoomDialogFragment

    override fun initObjects(context: Context) {
        val messageMeItem = MessageItemMe(auth)
                as DelegateAdapterItem<BaseViewHolder<Message>, Message>
        val messageYouItem = MessageItemYou(auth)
                as DelegateAdapterItem<BaseViewHolder<Message>, Message>

        val diffCallback = DiffCallback<Message>()
        val adapter = DelegateAdapter.Builder<Message>()
            .add(messageMeItem)
            .add(messageYouItem)
            .diffCallback(diffCallback)
            .build()

        this.adapter = adapter
    }

    override fun initObservers() {
        observe(vm.messageEdit, ::onStateMessageEditChanged)
        observe(vm.liveGetMessages, ::onStateChanged)
        observe(vm.liveSendMessage, ::onStateSendMessageChanged)
        observe(vm.liveGetChatRoom, ::onStateGetChatRoomChanged)
        observe(vm.liveChatRoomDelete, ::onStateChatRoomDeleteChanged)
        observe(deleteDialog.liveDeleteDialog, ::onStateDeleteDialogChanged)
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        layoutResourceId: Int
    ): View {
        val binding: FragmentChatRoomBinding = DataBindingUtil.inflate(
            inflater,
            layoutResourceId,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.chatRoomVM = this.vm as ChatRoomVMDefault

        return binding.root
    }

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        with(view) {
            val etMessage = findViewById<EditText>(R.id.etMessage)
            val ivSend = findViewById<ImageView>(R.id.ivSend)
            ivSend.setOnClickListener {
                vm.sendMessage(etMessage.text.toString())
                etMessage.text.clear()
            }

            val rvMessages = findViewById<RecyclerView>(R.id.rvMessages)
            rvMessages.adapter = this@ChatRoomFragment.adapter
            rvMessages.layoutManager = LinearLayoutManager(context)

            this@ChatRoomFragment.ivSend = findViewById(R.id.ivSend)
            this@ChatRoomFragment.ivBack = findViewById(R.id.ivBack)
            this@ChatRoomFragment.civGroupPic = findViewById(R.id.civGroupPic)
            this@ChatRoomFragment.tvGroupName = findViewById(R.id.tvGroupName)
            this@ChatRoomFragment.ivMore = findViewById(R.id.ivMore)
            this@ChatRoomFragment.pbLoading = findViewById(R.id.pbLoading)
        }
        lockSendMessageUi(true)
        this.menuPopup = initMenu()
        this.deleteDialog = DeleteChatRoomDialogFragment()
    }

    override fun initData() {
        vm.currentChatRoom = ChatRoomFragmentArgs.fromBundle(requireArguments()).chatRoom

        vm.getChatRoom()
        vm.getMessages()
    }

    private fun setupToolbar(chatRoom: ChatRoom) {
        ivBack.setOnClickListener {
            hideKeyboard()
            activity?.onBackPressed()
        }
        Glide.with(this)
            .load(chatRoom.picturePath)
            .centerCrop()
            .into(civGroupPic)

        tvGroupName.text = chatRoom.friendlyName
        ivMore.setOnClickListener {
            val screenWidth = requireView().width
            val statusBarHeight = UiUtils.getStatusBarHeight(requireActivity().window)
            val menuWidth = UiUtils.dpToPixels(MenuPopup.MENU_WIDTH, requireContext())
            val positionX = screenWidth - menuWidth - UiUtils.dpToPixels(4, requireContext())
            val positionY = statusBarHeight + UiUtils.dpToPixels(4, requireContext())
            val pointToShow = Point(positionX, positionY)

            menuPopup.showPopup(pointToShow)
        }
    }

    private fun initMenu(): MenuPopup {
        val menuPopup = MenuPopup()
        menuPopup.setOnClickListener {
            when (it.id) {
                R.id.tvMenuItemEdit -> {
                    menuPopup.dismiss()
                    val direction =
                        ChatRoomFragmentDirections.actionChatRoomFragmentToChatRoomEditFragment(vm.currentChatRoom)
                    findNavController().navigate(direction)
                }
                R.id.tvMenuItemDelete -> {
                    menuPopup.dismiss()
                    deleteDialog.show(requireFragmentManager(), null)
                }
            }
        }
        menuPopup.setupPopup(requireContext())

        return menuPopup
    }

    private fun onStateChanged(state: ChatRoomVM.StateGetMessages) = when (state) {
        is ChatRoomVM.StateGetMessages.OnLoading -> showLoading(pbLoading, true)
        is ChatRoomVM.StateGetMessages.OnGetMessagesSuccess -> {
            showLoading(pbLoading, false)
            adapter.swapData(state.messages)
            rvMessages.scrollToPosition(adapter.itemCount - 1)
        }
        is ChatRoomVM.StateGetMessages.OnGetMessagesError -> {
            showLoading(pbLoading, false)
            UiUtils.showSnackbar(
                requireView(),
                state.throwable.localizedMessage
                    ?: "Unhandled error. Contact developer, please."
            )
        }
    }

    private fun onStateMessageEditChanged(state: String) = when {
        state.isEmpty() -> {
            lockSendMessageUi(true)
        }
        else -> {
            lockSendMessageUi(false)
        }
    }

    private fun onStateSendMessageChanged(state: ChatRoomVM.StateSendMessage) = when (state) {
        is ChatRoomVM.StateSendMessage.OnLoading -> showLoading(pbLoading, true)
        is ChatRoomVM.StateSendMessage.OnMessageSent -> Unit
        is ChatRoomVM.StateSendMessage.OnSendMessageError -> {
            showLoading(pbLoading, false)
            UiUtils.showSnackbar(
                requireView(),
                state.throwable.localizedMessage
                    ?: "Unhandled error. Contact developer, please."
            )
        }
    }

    private fun onStateGetChatRoomChanged(state: ChatRoomVM.StateGetChatRoom) {
        when (state) {
            is ChatRoomVM.StateGetChatRoom.OnLoading -> showLoading(pbLoading, true)
            is ChatRoomVM.StateGetChatRoom.OnGetChatRoomSuccess -> {
                showLoading(pbLoading, false)
                setupToolbar(state.chatRoom)
            }
            is ChatRoomVM.StateGetChatRoom.OnGetChatRoomError -> {
                showLoading(pbLoading, false)
                UiUtils.showSnackbar(
                    requireView(),
                    state.throwable.localizedMessage
                        ?: "Unhandled error. Contact developer, please."
                )
            }
            is ChatRoomVM.StateGetChatRoom.OnChatRoomDeleted -> {
                activity?.onBackPressed()
            }
        }
    }

    private fun onStateChatRoomDeleteChanged(state: ChatRoomVM.StateChatRoomDelete) {
        when (state) {
            is ChatRoomVM.StateChatRoomDelete.OnLoading -> showLoading(pbLoading, true)
            is ChatRoomVM.StateChatRoomDelete.OnChatRoomDeleteSuccess -> {
                showLoading(pbLoading, false)
                activity?.onBackPressed()
            }
            is ChatRoomVM.StateChatRoomDelete.OnChatRoomDeleteError -> {
                showLoading(pbLoading, false)
                UiUtils.showSnackbar(
                    requireView(),
                    state.throwable.localizedMessage
                        ?: "Unhandled error. Contact developer, please."
                )
            }
        }
    }

    private fun onStateDeleteDialogChanged(state: DeleteChatRoomDialogFragment.StateDeleteDialog) {
        when (state) {
            is DeleteChatRoomDialogFragment.StateDeleteDialog.OnDelete -> {
                vm.deleteChatRoom()
            }
        }
    }

    private fun lockSendMessageUi(lock: Boolean) {
        val color = if (lock) {
            ContextCompat.getColor(requireContext(), R.color.gray_light_trans)
        } else {
            ContextCompat.getColor(requireContext(), R.color.accent)
        }
        ivSend.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)

        ivSend.isEnabled = !lock
        ivSend.isClickable = !lock
    }
}
