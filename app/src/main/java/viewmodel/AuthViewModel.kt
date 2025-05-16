package viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class AuthViewModel(
    private val auth: FirebaseAuth = Firebase.auth
) : ViewModel() {

    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        // Observe auth state changes to update currentUser in real-time
        auth.addAuthStateListener { auth ->
            _currentUser.value = auth.currentUser
            _isLoading.value = false
        }
    }

    fun firebaseAuthWithGoogle(idToken: String, onResult: (Boolean, String?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful, task.exception?.message)
            }
    }

    // Kirim link ke email
    fun sendResetPasswordEmail(email: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception.message ?: "Unknown error")
            }
    }

    fun signIn(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _currentUser.value = auth.currentUser
                onSuccess()
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                val errorMessage = mapFirebaseAuthException(exception)
                onError(errorMessage)
                _isLoading.value = false
            }
    }

    fun signUp(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        _isLoading.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _currentUser.value = auth.currentUser
                onSuccess()
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                val errorMessage = mapFirebaseAuthException(exception)
                onError(errorMessage)
                _isLoading.value = false
            }
    }

    fun signOut() {
        auth.signOut()
        _currentUser.value = null
    }

    private fun mapFirebaseAuthException(exception: Exception): String {
        return when (exception) {
            is FirebaseAuthWeakPasswordException -> "Kata sandi terlalu lemah."
            is FirebaseAuthInvalidCredentialsException -> "Email atau kata sandi tidak valid."
            is FirebaseAuthUserCollisionException -> "Email sudah digunakan."
            is FirebaseAuthInvalidUserException -> "Pengguna tidak ditemukan."
            else -> "Terjadi kesalahan: ${exception.localizedMessage}"
        }
    }
}
