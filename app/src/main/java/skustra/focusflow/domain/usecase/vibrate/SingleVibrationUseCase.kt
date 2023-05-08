package skustra.focusflow.domain.usecase.vibrate

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import javax.inject.Inject

class SingleVibrationUseCase @Inject constructor(private val context: Context) {

    fun vibrate() {
        if (Build.VERSION.SDK_INT >= 31) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator = vibratorManager.defaultVibrator
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(200)
        }
    }
}