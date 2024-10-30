import UIKit
import SwiftUI
import ComposeApp // Ensure your shared Kotlin module is imported correctly

struct ComposeView: UIViewControllerRepresentable {
    let viewController: () -> UIViewController

    func makeUIViewController(context: Context) -> UIViewController {
        return viewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    @State private var selectedTab: Tab = .home
    
    var body: some View {
        TabView(selection: $selectedTab) {
            ComposeView(viewController: {
                MainViewControllerKt.HomeScreenViewController() // Kotlin function for HomeScreen
            })
            .tabItem {
                Image(systemName: "house.fill")
                Text("Home")
            }
            .tag(Tab.home)
            
            ComposeView(viewController: {
                MainViewControllerKt.ListScreenViewController() // Kotlin function for ListScreen
            })
            .tabItem {
                Image(systemName: "list.bullet")
                Text("List")
            }
            .tag(Tab.list)
            
            ComposeView(viewController: {
                MainViewControllerKt.MenuScreenViewController() // Kotlin function for MenuScreen
            })
            .tabItem {
                Image(systemName: "menucard.fill")
                Text("Menu")
            }
            .tag(Tab.menu)
        }
        .accentColor(.orange) // Optional color customization
    }
    
    enum Tab {
        case home
        case list
        case menu
    }
}
