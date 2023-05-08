package skustra.focusflow.domain.usecase.playsound

import android.content.Context
import android.media.MediaPlayer
import javax.inject.Inject

class PlaySoundUseCase @Inject constructor(val context: Context) {

    fun play(soundId: Int) {
        val player = MediaPlayer.create(context, soundId)
        player.setOnCompletionListener { it.release() }
        player.start()
    }
}

