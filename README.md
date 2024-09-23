"# Promissory-Notes-Android" 

Here is a simple Android app for handling personal promissory notes (basically, a "you promise to pay me back for X") note taking app. It was built as an exercise in Kotlin using the Android Kotlin plugin (org.jetbrains.kotlin.android), with some Java helper functionality.

It was built using the MVVM (MOdel View ViewModel) android design pattern. Record storage is handled locally as a relatively simple database on the android device via SQLite, using Room as an operational abstraction layer. For the UX, the lifecycle-viewmodel-ktx dependency was used to manage UI data in a lifecycle context.

 