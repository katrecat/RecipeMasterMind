import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView

class CountdownTimer(private val durationMillis: Long, private val intervalMillis: Long) {
    private var countDownTimer: CountDownTimer? = null
    private var isRunning = false
    private var timeRemainingMillis: Long = 0

    fun start(
        textView: TextView,
        startButton: Button,
        stopButton: Button
    ) {
        countDownTimer = object : CountDownTimer(timeRemainingMillis, intervalMillis) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemainingMillis = millisUntilFinished
                updateTimerText(textView)
            }

            override fun onFinish() {
                isRunning = false
                updateTimerText(textView)
                startButton.text = "Start"
            }
        }

        countDownTimer?.start()
        isRunning = true
        startButton.text = "Pause"
        stopButton.isEnabled = true
    }

    fun pause() {
        countDownTimer?.cancel()
        isRunning = false
    }

    fun stop(
        textView: TextView,
        startButton: Button,
        stopButton: Button
    ) {
        countDownTimer?.cancel()
        isRunning = false
        timeRemainingMillis = durationMillis
        updateTimerText(textView)
        startButton.text = "Start"
        stopButton.isEnabled = false
    }

    fun isRunning(): Boolean {
        return isRunning
    }

    private fun updateTimerText(textView: TextView) {
        val hours = (timeRemainingMillis / (1000 * 60 * 60)).toInt()
        val minutes = ((timeRemainingMillis % (1000 * 60 * 60)) / (1000 * 60)).toInt()
        val seconds = ((timeRemainingMillis % (1000 * 60)) / 1000).toInt()

        val hoursStr = formatTimeUnit(hours)
        val minutesStr = formatTimeUnit(minutes)
        val secondsStr = formatTimeUnit(seconds)

        textView.text = "$hoursStr:$minutesStr:$secondsStr"
    }

    private fun formatTimeUnit(value: Int): String {
        return if (value in 0..9) {
            "0$value"
        } else {
            value.toString()
        }
    }
}
