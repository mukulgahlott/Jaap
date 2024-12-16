import Foundation
import FirebaseMessaging


@objc
 public class FcmTokenProvider: NSObject {
    @objc
     public static func getToken() async -> String {
        do {
            // Attempt to get the token asynchronously
            let token = try await Messaging.messaging().token()

            // Directly return the token since it's non-optional
            return token
        } catch {
            // Handle error (e.g., return an empty string on error)
            print("Error fetching FCM token: \(error)")
            return ""
        }
    }
    }
