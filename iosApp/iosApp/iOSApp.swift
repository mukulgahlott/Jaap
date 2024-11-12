import SwiftUI
import Firebase

@main
struct iOSApp: App {
    
    @State private var isActive = false

    init(){
            FirebaseApp.configure()
        }
    var body: some Scene {
        WindowGroup {
            if isActive {
                     // Replace with your main content view
                     ContentView()
                    .edgesIgnoringSafeArea(.all)
                 } else {
                     // The splash screen view
                     VStack {
                         Image("Splash") // Ensure you have an image asset named "splash"
                             .resizable()
                             .scaledToFill()
                             .edgesIgnoringSafeArea(.all)
                     }
                     .onAppear {
                         // Simulate a delay, similar to the Kotlin code
                         DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
                             withAnimation {
                                 isActive = true
                             }
                         }
                     }
                 }
             }
        }
    }
