import SwiftUI

struct ContentView: View {
    @State private var selectedTab: Tab = .home
    @ObservedObject var prefs: PreferencesViewModel // Assuming you have a view model for prefs

    var body: some View {
        TabView(selection: $selectedTab) {
        HomeView(prefs: prefs) // Replace with your actual Home view
            .tabItem {
                Image("home") // Use your image assets
                Text("Home")
            }
            .tag(Tab.home)

        ListView(prefs: prefs) // Replace with your actual List view
            .tabItem {
                Image("list") // Use your image assets
                Text("List")
            }
            .tag(Tab.list)

        MenuView(prefs: prefs) // Replace with your actual Menu view
            .tabItem {
                Image("menu") // Use your image assets
                Text("Menu")
            }
            .tag(Tab.menu)
    }
        .accentColor(Color.orange) // Set the color for the selected tab
    }
}

enum Tab {
    case home
    case list
    case menu
}
