package com.example.vaultx.security

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.vaultx.Util.AppConstants.AuthConstants

class AuthManager(private val activity: AppCompatActivity) {

    fun authenticate(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)

        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            }
        )

        val promptBuilder = BiometricPrompt.PromptInfo.Builder()
            .setTitle(AuthConstants.BIOMETRIC_PROMPT_TITLE)
            .setSubtitle(AuthConstants.BIOMETRIC_PROMPT_SUBTITLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            promptBuilder.setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
        } else {
            promptBuilder.setDeviceCredentialAllowed(true)
        }

        biometricPrompt.authenticate(promptBuilder.build())
    }
}