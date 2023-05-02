package de.hsflensburg.common

actual fun getPlatformName(): String {
    return "Android"
}

actual fun navigateAdmin() {
    state.value = "Admin"
}

actual fun navigateAllBooks(){
    state.value = "AllBooks"
}

actual fun navigateCreateBook(){
    state.value = "CreateBook"
}
actual fun navigateHome() {
    //state.value = "Home"
}
actual fun navigateLandingPage(){
    state.value = "LandingPage"
}

actual fun navigateLendedBooks(){
    state.value = "LendedBooks"
}

actual fun navigateLogin(){
    state.value = "Login"
}

actual fun navigateMyBooks(){
    state.value = "MyBooks"
}

actual fun navigateOverview(){
    state.value = "Overview"
}

actual fun navigateRegister(){
    state.value = "Register"
}
