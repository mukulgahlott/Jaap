import com.coretechies.jaap.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

// In your Activity or ViewModel

class LoginViewModel(private val apiService: ApiService) {

    // Call to check if the user is verified
    suspend fun verifyUser(
        userName: String,
        passcode: Int,
        deviceId: String,
        fcmToken: String
    ): String {
        return withContext(Dispatchers.IO) {
            apiService.checkIsVerified(userName, passcode, deviceId, fcmToken)
        }
    }
}
