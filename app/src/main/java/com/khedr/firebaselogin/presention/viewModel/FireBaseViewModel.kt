import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.khedr.firebaselogin.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FireBaseViewModel(application: Application) : AndroidViewModel(application) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val googleSignInClient: GoogleSignInClient
    private val _currentUser: MutableState<FirebaseUser?> = mutableStateOf(firebaseAuth.currentUser)
    val currentUser: MutableState<FirebaseUser?> = _currentUser
    private val _isLoadingLogin = MutableStateFlow(false)
    val isLoadingLogin: StateFlow<Boolean> get() = _isLoadingLogin
    private val _isLoadingSignUp = MutableStateFlow(false)
    val isLoadingSignUp: StateFlow<Boolean> get() = _isLoadingSignUp
    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(application, gso)
    }

    fun getSignInIntent(): Intent = googleSignInClient.signInIntent

    fun handleSignInResult(data: Intent?, onSignInSuccess: () -> Unit, onSignInError: (String) -> Unit) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { idToken ->
                signInWithGoogle(idToken, onSignInSuccess, onSignInError)
            } ?: onSignInError("Google sign-in failed: No ID token received.")
        } catch (e: ApiException) {
            Log.e("GoogleSignIn", "signInResult:failed code=${e.statusCode}", e)
            onSignInError("Google sign-in failed: ${e.localizedMessage}")
        } catch (e: Exception) {
            Log.e("GoogleSignIn", "Unexpected error during sign-in", e)
            onSignInError("Unexpected error during sign-in: ${e.localizedMessage}")
        }
    }

    private fun signInWithGoogle(idToken: String, onSignInSuccess: () -> Unit, onSignInError: (String) -> Unit) {
        _isLoadingLogin.value = true
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _currentUser.value = firebaseAuth.currentUser
                        onSignInSuccess()
                    } else {
                        Log.e("FirebaseAuth", "signInWithCredential:failure", task.exception)
                        onSignInError(task.exception?.message ?: "Google sign-in failed: Unknown error.")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("FirebaseAuth", "signInWithCredential:failure", exception)
                    onSignInError(exception.message ?: "Google sign-in failed: Unknown error.")
                }
        } catch (e: Exception) {
            Log.e("FirebaseAuth", "Unexpected error during Google sign-in", e)
            onSignInError("Unexpected error during Google sign-in: ${e.localizedMessage}")
        }finally {
            _isLoadingLogin.value = false
        }
    }

    fun login(email: String, password: String, onLoginSuccess: () -> Unit, onLoginError: (String) -> Unit) {
        _isLoadingLogin.value = true
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _currentUser.value = firebaseAuth.currentUser
                        onLoginSuccess()
                    } else {
                        Log.e("FirebaseAuth", "login:failure", task.exception)
                        onLoginError(task.exception?.message ?: "Login failed: Unknown error.")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("FirebaseAuth", "login:failure", exception)
                    onLoginError(exception.message ?: "Login failed: Unknown error.")
                }
        } catch (e: Exception) {
            Log.e("FirebaseAuth", "Unexpected error during login", e)
            onLoginError("Unexpected error during login: ${e.localizedMessage}")
        }finally {
            _isLoadingLogin.value = false
        }
    }

    fun register(email: String, password: String, onRegisterSuccess: () -> Unit, onRegisterError: (String) -> Unit) {
        _isLoadingSignUp.value = true
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _currentUser.value = firebaseAuth.currentUser
                        onRegisterSuccess()
                    } else {
                        Log.e("FirebaseAuth", "register:failure", task.exception)
                        onRegisterError(task.exception?.message ?: "Registration failed: Unknown error.")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("FirebaseAuth", "register:failure", exception)
                    onRegisterError(exception.message ?: "Registration failed: Unknown error.")
                }
        } catch (e: Exception) {
            Log.e("FirebaseAuth", "Unexpected error during registration", e)
            onRegisterError("Unexpected error during registration: ${e.localizedMessage}")
        }finally {
            _isLoadingSignUp.value=false
        }
    }

    fun signOut(onSignOut: () -> Unit) {
        try {
            firebaseAuth.signOut()
            _currentUser.value = null
            onSignOut()

        } catch (exception: Exception) {
            Log.e("FirebaseAuth", "signOut:failure", exception)
        }
    }

    fun updatePassword(
        oldPassword: String,
        newPassword: String,
        onUpdateSuccess: () -> Unit,
        onUpdateError: (String) -> Unit
    ) {
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email // Assuming the user is logged in with email/password

        if (user != null && email != null) {
            val credential = EmailAuthProvider.getCredential(email, oldPassword)
            user.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        Log.d("FirebaseAuth", "Re-authentication successful.")

                        user.updatePassword(newPassword)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    Log.d("FirebaseAuth", "Password updated successfully.")
                                    onUpdateSuccess()
                                } else {
                                    Log.e("FirebaseAuth", "Password update failed", updateTask.exception)
                                    onUpdateError(updateTask.exception?.message ?: "Password update failed: Unknown error.")
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.e("FirebaseAuth", "Password update failed", exception)
                                onUpdateError(exception.message ?: "Password update failed: Unknown error.")
                            }
                    } else {
                        Log.e("FirebaseAuth", "Re-authentication failed", reauthTask.exception)
                        onUpdateError(reauthTask.exception?.message ?: "Re-authentication failed: Unknown error.")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("FirebaseAuth", "Re-authentication failed", exception)
                    onUpdateError(exception.message ?: "Re-authentication failed: Unknown error.")
                }
        } else {
            onUpdateError("No authenticated user found.")
        }
    }


}
