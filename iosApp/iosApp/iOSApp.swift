import SwiftUI
import Firebase
import FirebaseAnalytics
import FirebaseCrashlytics

@main
struct iOSApp: App {
    
    @State private var isActive = false

    init(){
            FirebaseApp.configure()
            Crashlytics.crashlytics().setCrashlyticsCollectionEnabled(true)
        
        Analytics.logEvent("app_started", parameters: [
                   "launch_time": Date().description
               ])
        }
    var body: some Scene {
        WindowGroup {
                     ContentView()
                    .edgesIgnoringSafeArea(.all)
              
                     }
                 }
             }
