import UIKit
import SwiftUI
import ComposeApp

import SwiftUI

extension Color {
    static let customOrange = Color(red: 249 / 255, green: 168 / 255, blue: 38 / 255)
}
struct ContentView: View {
    var body: some View {
        ZStack {
            Color.customOrange // Elige el color deseado
                .edgesIgnoringSafeArea(.top) // Extiende el color a toda la pantalla

            ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
        }
    }
}
// Extensión y ViewModifier como se definió en el ejemplo anterior

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}