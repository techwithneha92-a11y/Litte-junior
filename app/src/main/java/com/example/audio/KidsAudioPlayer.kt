package com.example.audio

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.speech.tts.TextToSpeech
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class KidsAudioPlayer(context: Context) : TextToSpeech.OnInitListener {

    private val appContext = context.applicationContext
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private val _isSoundEnabled = MutableStateFlow(true)
    val isSoundEnabled: StateFlow<Boolean> = _isSoundEnabled.asStateFlow()

    private val _isSlowMode = MutableStateFlow(false)
    val isSlowMode: StateFlow<Boolean> = _isSlowMode.asStateFlow()

    private var toneGen: ToneGenerator? = null

    init {
        try {
            tts = TextToSpeech(appContext, this)
            toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 75)
        } catch (e: Exception) {
            Log.e("KidsAudioPlayer", "Error starting TTS/Audio", e)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.setLanguage(Locale.getDefault())
            }
            applyVoiceSettings()
            isInitialized = true
        } else {
            Log.w("KidsAudioPlayer", "TTS Init failed code $status")
        }
    }

    private fun applyVoiceSettings() {
        tts?.let {
            // Warm, clear child-friendly pitch
            it.setPitch(1.22f)
            // Cheerful, understandable pacing
            val rate = if (_isSlowMode.value) 0.70f else 0.90f
            it.setSpeechRate(rate)
        }
    }

    fun toggleSound(enabled: Boolean? = null) {
        _isSoundEnabled.value = enabled ?: !_isSoundEnabled.value
        if (!_isSoundEnabled.value) {
            stop()
        }
    }

    fun toggleSlowMode(slow: Boolean? = null) {
        _isSlowMode.value = slow ?: !_isSlowMode.value
        applyVoiceSettings()
    }

    fun speak(text: String, queueMode: Int = TextToSpeech.QUEUE_FLUSH) {
        if (!_isSoundEnabled.value) return
        if (isInitialized && tts != null) {
            applyVoiceSettings()
            tts?.speak(text, queueMode, null, "KIDS_AUDIO_${System.currentTimeMillis()}")
        }
    }

    fun playSuccessSound() {
        if (!_isSoundEnabled.value) return
        try {
            toneGen?.startTone(ToneGenerator.TONE_PROP_BEEP2, 180)
        } catch (e: Exception) {
            // Tone error ignored
        }
    }

    fun playTapSound() {
        if (!_isSoundEnabled.value) return
        try {
            toneGen?.startTone(ToneGenerator.TONE_PROP_ACK, 70)
        } catch (e: Exception) {
            // Tone error ignored
        }
    }

    fun playCheer(message: String = "Great Job!") {
        playSuccessSound()
        speak(message)
    }

    fun stop() {
        try {
            tts?.stop()
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
            toneGen?.release()
        } catch (e: Exception) {
            // Ignore
        }
    }
}
