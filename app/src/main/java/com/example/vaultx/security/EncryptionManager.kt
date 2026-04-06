package com.example.vaultx.security
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.example.vaultx.Util.AppConstants.EncryptionConstants
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class EncryptionManager {

    private val alias = EncryptionConstants.ALIAS
    private val keystore = KeyStore.getInstance(EncryptionConstants.ANDROID_KEY_STORE).apply { load(null) }

    private fun getKey(): SecretKey {
        val existingKey = keystore.getEntry(alias,null) as? KeyStore.SecretKeyEntry
        if(existingKey != null){
            return existingKey.secretKey
        }

        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,
            EncryptionConstants.ANDROID_KEY_STORE)
        val spec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        keyGenerator.init(spec)

        return keyGenerator.generateKey()
    }

    fun encrypt(text: String): String{

        val cipher = Cipher.getInstance(EncryptionConstants.AES_TRANSFORMATION) //AES/GCM/NoPadding
        cipher.init(Cipher.ENCRYPT_MODE, getKey())

        val iv = cipher.iv
        val encrypted = cipher.doFinal(text.toByteArray())
        val combined = iv + encrypted

        return Base64.encodeToString(combined, Base64.DEFAULT)
    }

    fun decrypt(text: String): String{

        val decoded = Base64.decode(text, Base64.DEFAULT)
        val iv = decoded.copyOfRange(0,12)
        val encrypted = decoded.copyOfRange(12, decoded.size)
        val cipher = Cipher.getInstance(EncryptionConstants.AES_TRANSFORMATION) //AES/GCM/NoPadding
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), spec)

        return String(cipher.doFinal(encrypted))
    }
}