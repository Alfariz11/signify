package viewmodel

import androidx.compose.runtime.mutableStateOf
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
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth = Firebase.auth
) : ViewModel() {

    val currentUser = mutableStateOf<FirebaseUser?>(auth.currentUser)
    val isLoading = mutableStateOf(false)

    init {
        // Observe auth state changes to update currentUser in real-time
        auth.addAuthStateListener { auth ->
            currentUser.value = auth.currentUser
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
        isLoading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                currentUser.value = auth.currentUser
                onSuccess()
                isLoading.value = false
            }
            .addOnFailureListener { exception ->
                val errorMessage = when (exception) {
                    is FirebaseAuthInvalidUserException -> "Invalid user credentials."
                    is FirebaseAuthInvalidCredentialsException -> "Invalid password."
                    else -> exception.message ?: "Unknown error"
                }
                onError(errorMessage)
                isLoading.value = false
            }
    }

    fun signUp(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        isLoading.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                currentUser.value = auth.currentUser
                onSuccess()
                isLoading.value = false
            }
            .addOnFailureListener { exception ->
                val errorMessage = when (exception) {
                    is FirebaseAuthWeakPasswordException -> "Password is too weak."
                    is FirebaseAuthInvalidCredentialsException -> "Invalid email address."
                    is FirebaseAuthUserCollisionException -> "An account with this email already exists."
                    else -> exception.message ?: "Unknown error"
                }
                onError(errorMessage)
                isLoading.value = false
            }
    }

    fun signOut() {
        auth.signOut()
        currentUser.value = null
    }
}
