package com.test.chatterwave.beta.features.main_activity.current_user_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chi.interngram.echo.R
import com.test.chatterwave.beta.data.source.local.preferences.AppPreferences
import com.chi.interngram.echo.databinding.FragmentCurrentUserBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.UserDomainModel
import com.test.chatterwave.beta.features.main_activity.MainActivityViewModel
import com.test.chatterwave.beta.utils.DIRECTION_ONE
import com.test.chatterwave.beta.utils.FOOTER_COUNT_ZERO
import com.test.chatterwave.beta.utils.NavigationEvent
import com.test.chatterwave.beta.utils.RECYCLERVIEW_SPAN_ONE
import com.test.chatterwave.beta.utils.RECYCLERVIEW_SPAN_THREE
import com.test.chatterwave.beta.utils.UIResponseState
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class CurrentUserFragment : Fragment() {

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>

    private val viewModel: CurrentUserViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[CurrentUserViewModel::class.java]
    }
    private val _adapter = UserPostsAdapter()
    private val header = PostsLoaderStateAdapter()
    private val footer = PostsLoaderStateAdapter()

    private var _binding: FragmentCurrentUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: WARNING!
        // i`m not sure that observeUserHeaderInformation() should be called from onViewCreated
        // but i need it to load info every time user open this screen,
        // also it help to fix problem with navigation from create_post graph to user_page graph
        observeButtonClick()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_current_user,container,false)
        initRecyclerView()
        initDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserHeaderInformation()

        binding.currentUserNickName.setOnClickListener {
            appPreferences.clearTokens()
            findNavController().navigate(R.id.action_currentUserFragment_to_authActivity2)
            requireActivity().finish()
        }

        binding.retryButton.setOnClickListener {
            viewModel.onRetryButtonClicked()
        }
        binding.userHeader.editProfileButton.setOnClickListener {
            viewModel.onEditButtonClicked()
        }
        mainViewModel.getCurrentUser()
    }

    private fun observeUserHeaderInformation() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.userHeaderInformationState.collect { uiState ->
                when (uiState) {
                    is UIResponseState.Success<*> -> {
                        uiState.content as UserDomainModel
                        //mainViewModel.setAvatarToBottomNavigation(uiState.content.photo)
                        viewModel.getUserPosts(uiState.content.id).collectLatest {
                            _adapter.submitData(it)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun observeButtonClick(){
        lifecycleScope.launchWhenStarted {
            viewModel.onButtonClickEvent.collect{ navEvent ->
                when(navEvent){
                    NavigationEvent.NavigateNextScreen -> findNavController().navigate(R.id.action_currentUserFragment_to_editProfileFragment)
                    else -> {}
                }
            }
        }
    }

    private fun initDataBinding() {
        with(binding) {
            mainActivityViewModel = mainViewModel
            userHeader.mainActivityViewModel = mainViewModel
            lifecycleOwner = this@CurrentUserFragment
        }
    }

    private fun initRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = _adapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = footer
        )
        val layoutManager = GridLayoutManager(context, RECYCLERVIEW_SPAN_THREE)
        recyclerView.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == _adapter.itemCount  && footer.itemCount > FOOTER_COUNT_ZERO) RECYCLERVIEW_SPAN_THREE else RECYCLERVIEW_SPAN_ONE
            }
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(DIRECTION_ONE) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    _adapter.retry()
                }
            }
        })

        _adapter.addLoadStateListener {
            val state = it.refresh
            val isEmpty = state is LoadState.NotLoading && _adapter.itemCount == 0
            binding.recyclerView.isVisible = !isEmpty
            binding.emptyPostsListText.isVisible = isEmpty
            binding.progressBar.isVisible = it.source.refresh is LoadState.Loading
        }

        recyclerView.setHasFixedSize(true)
    }

/*    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }*/
}