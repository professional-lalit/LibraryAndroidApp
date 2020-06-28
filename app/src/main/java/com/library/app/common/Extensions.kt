import android.content.Context
import android.widget.Toast
import com.library.app.common.CustomApplication

fun makeToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}