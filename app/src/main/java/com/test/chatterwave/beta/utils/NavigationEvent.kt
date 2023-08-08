package com.test.chatterwave.beta.utils

sealed class NavigationEvent {
    object NavigatePreviousScreen : NavigationEvent()
    object NavigateNextScreen : NavigationEvent()
    object NavigationToTermsScreen : NavigationEvent()
    object NavigateBirthdayPecker : NavigationEvent()
    object NavigationToRecoveryPassword : NavigationEvent()
    object NavigationToCreateAnAccount : NavigationEvent()
    object NavigationToLoginWithGoogle : NavigationEvent()
    object NavigationToLoginWithFacebook : NavigationEvent()
    object NavigateToDialog : NavigationEvent()
    object NavigateFeedScreen: NavigationEvent()
    object NavigateToDeleteDialog: NavigationEvent()
    object Retry: NavigationEvent()
}