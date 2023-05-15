import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.recipemastermind.R
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(requireActivity(), this, hour, minute, true)
    }

    override fun onTimeSet(view: android.widget.TimePicker?, hourOfDay: Int, minute: Int) {
        val hoursStr = formatTimeUnit(hourOfDay)
        val minutesStr = formatTimeUnit(minute)
        val time = "$hoursStr:$minutesStr:00"

        val textViewCountdown = activity?.findViewById<TextView>(R.id.text_countdown)
        textViewCountdown?.text = time
    }

    private fun formatTimeUnit(value: Int): String {
        return if (value in 0..9) {
            "0$value"
        } else {
            value.toString()
        }
    }
}
