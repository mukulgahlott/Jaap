import SwiftUI
import com.coretechies.jaap.screens.ListScreen

struct ContentView: View {
    @State private var selectedTab: Tab = .home

    enum Tab {
        case home
                case list
                case menu
    }

    var body: some View {
        TabView(selection: $selectedTab) {
        HomeScreen()
            .tabItem {
                Label("Home", systemImage: "house")
            }
            .tag(Tab.home)

        ListScreen()
            .tabItem {
                Label("List", systemImage: "list.bullet")
            }
            .tag(Tab.list)

        MenuScreen()
            .tabItem {
                Label("Menu", systemImage: "ellipsis.circle")
            }
            .tag(Tab.menu)
    }
        .accentColor(.blue) // Change color of selected tab icon
    }
}
