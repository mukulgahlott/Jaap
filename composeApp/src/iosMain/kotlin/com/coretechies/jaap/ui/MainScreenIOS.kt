import androidx.compose.ui.input.key.Key.Companion.Tab
import com.coretechies.jaap.screens.ListScreen // Ensure this is generated correctly

public struct MainScreenIos: View {
    @State private var selectedTab: Tab = .home // Use the dot notation for enum cases

    enum Tab {
        case home
                case list
                case menu
    }

    public var body: some View {
        TabView(selection: $selectedTab) {
        HomeScreen() // Make sure HomeScreen is correctly defined and accessible
            .tabItem {
                Label("Home", systemImage: "house")
            }
            .tag(Tab.home)

        // Use the generated Swift interface for ListScreen
        ListScreen() // Ensure this is the right usage for the generated Kotlin function
            .tabItem {
                Label("List", systemImage: "list.bullet")
            }
            .tag(Tab.list)

        MenuScreen() // Make sure MenuScreen is correctly defined and accessible
            .tabItem {
                Label("Menu", systemImage: "ellipsis.circle")
            }
            .tag(Tab.menu)
    }
        .accentColor(.blue) // Change color of selected tab icon
    }
}
