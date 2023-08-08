package com.test.chatterwave.beta.features.main_activity.edit_profile_fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chi.interngram.echo.R
import com.chi.interngram.echo.databinding.FragmentEditProfileBinding
import com.test.chatterwave.beta.di.hilt.assisted.InjectingSavedStateViewModelFactory
import com.test.chatterwave.beta.domain.model.UpdateUserDomainModel
import com.test.chatterwave.beta.features.main_activity.MainActivityViewModel
import com.test.chatterwave.beta.features.main_activity.ui.MainActivity
import com.test.chatterwave.beta.utils.EMPTY_STRING
import com.test.chatterwave.beta.utils.NetworkResult
import com.test.chatterwave.beta.utils.UIResponseState
import com.test.chatterwave.beta.utils.callbacks.OnBackPressedListener
import com.test.chatterwave.beta.utils.encodeImage
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    @Inject
    lateinit var viewModelFactory: Lazy<InjectingSavedStateViewModelFactory>

    private val viewModel: EditProfileViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory.get().create(this)
        )[EditProfileViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeNicknameValidation()
        observeUpdateUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(layoutInflater)
        initDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            fullNameEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.validateUserFullName(text.toString())
            }
            nickNameEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.checkNicknameValidLocal(text.toString())
            }
            cityEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.validateCityLocal(text.toString())
            }
            changePhotoButton.setOnClickListener {
                mainViewModel.saveUserHeaderInfoForEditProfile(getUserNewInfo())
                findNavController().navigate(R.id.editProfileAvatarDialog)
            }
            addPhotoButton.setOnClickListener {
                mainViewModel.saveUserHeaderInfoForEditProfile(getUserNewInfo())
                findNavController().navigate(R.id.addAvatarDialog3)
            }
            nexButton.setOnClickListener {
                if (nickNameEditText.text.toString() == mainViewModel.userHeaderInfo.value.nickname){
                    viewModel.saveUserChanges(getUserNewInfo())
                } else viewModel.checkNicknameValid(nickNameEditText.text.toString())
            }
            cancelButton.setOnClickListener {
                findNavController().navigate(R.id.action_editProfileFragment_to_editProfileCancelDialog)
            }
        }
    }

    private fun observeNicknameValidation(){
        lifecycleScope.launchWhenStarted {
            viewModel.nicknameValidation.collect{
                when(it){
                    is NetworkResult.Success -> viewModel.saveUserChanges(getUserNewInfo())
                    else -> {}
                }
            }
        }
    }

    private fun observeUpdateUser(){
        lifecycleScope.launchWhenStarted {
            viewModel.userUpdateResultState.collect{
                when(it){
                    is NetworkResult.Success -> findNavController().popBackStack()
                    is NetworkResult.Exception -> {
                        val snackBarView = Snackbar.make(requireView(), getString(R.string.try_again_later) , Snackbar.LENGTH_LONG)
                        val view = snackBarView.view
                        val params = view.layoutParams as FrameLayout.LayoutParams
                        params.gravity = Gravity.TOP
                        view.layoutParams = params
                        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
                        snackBarView.show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getUserNewInfo(): UpdateUserDomainModel {
        with(binding){
            return UpdateUserDomainModel(
                bio = bioEditText.text.toString().ifEmpty { " " },
                city = cityEditText.text.toString().ifEmpty { " " },
                fullName = fullNameEditText.text.toString(),
                nickname = nickNameEditText.text.toString(),
                photo = if(mainViewModel.userEditProfileAvatar.value is UIResponseState.Success<*>){
                    val result = (mainViewModel.userEditProfileAvatar.value as UIResponseState.Success<Bitmap?>).content
                    result?.encodeImage() ?: EMPTY_STRING
                } else EMPTY_STRING
                /*(mainViewModel.userEditProfileAvatar.value as UIResponseState.Success<Bitmap>).content.encodeImage()*/
            )
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).setOnBackPressedListener(object : OnBackPressedListener {
            override fun onBackPressedClickListener() {
                findNavController().navigate(R.id.action_editProfileFragment_to_editProfileCancelDialog)
            }
        })
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).setOnBackPressedListener(null)
    }

    private fun initDataBinding() {
        with(binding) {
            mainActivityViewModel = mainViewModel
            editProfileViewModel = viewModel
            lifecycleOwner = this@EditProfileFragment
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}