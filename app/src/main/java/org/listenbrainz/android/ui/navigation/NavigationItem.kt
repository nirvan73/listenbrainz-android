package org.listenbrainz.android.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface NavigationItem :NavKey{

    sealed interface OnboardingScreens: NavigationItem {
        @Serializable
        data object IntroductionScreen : OnboardingScreens

        @Serializable
        data object PermissionScreen : OnboardingScreens

        @Serializable
        data object LoginConsentScreen : OnboardingScreens

        @Serializable
        data object LoginScreen : OnboardingScreens

        /* Temporary token based login */
        @Serializable
        data object TempLoginScreen : OnboardingScreens

        @Serializable
        data object ListeningAppScreen : OnboardingScreens
    }

    @Serializable
    data object HomeScreen: NavigationItem

    @Serializable
    data object CreateAccountScreen: NavigationItem
}