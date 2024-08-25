import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.khedr.firebaselogin.R

class FireBaseViewModel(application: Application) : AndroidViewModel(application) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val googleSignInClient: GoogleSignInClient
    private val _currentUser: MutableState<FirebaseUser?> = mutableStateOf(firebaseAuth.currentUser)
    val currentUser: MutableState<FirebaseUser?> = _currentUser

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(application, gso)
    }

    fun getSignInIntent(): Intent = googleSignInClient.signInIntent

    fun handleSignInResult(data: Intent?, onSignInSuccess: () -> Unit, onSignInError: (String) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { idToken ->
                signInWithGoogle(idToken, onSignInSuccess, onSignInError)
            } ?: onSignInError("Google sign-in failed")
        } catch (e: ApiException) {
            Log.w("GoogleSignIn", "signInResult:failed code=" + e.statusCode)
            onSignInError("Google sign-in failed: ${e.localizedMessage}")
        }
    }

    private fun signInWithGoogle(idToken: String, onSignInSuccess: () -> Unit, onSignInError: (String) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _currentUser.value = firebaseAuth.currentUser
                    onSignInSuccess()
                } else {
                    onSignInError(task.exception?.message ?: "Unknown error")
                }
            }
            .addOnFailureListener { exception ->
                onSignInError(exception.message ?: "Unknown error")
            }
    }

    fun login(email: String, password: String, onLoginSuccess: () -> Unit, onLoginError: (String) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _currentUser.value = firebaseAuth.currentUser
                    onLoginSuccess()
                } else {
                    onLoginError(task.exception?.message ?: "Unknown error")
                }
            }
            .addOnFailureListener { exception ->
                onLoginError(exception.message ?: "Unknown error")
            }
    }

    fun register(email: String, password: String, onRegisterSuccess: () -> Unit, onRegisterError: (String) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _currentUser.value = firebaseAuth.currentUser
                    onRegisterSuccess()
                } else {
                    onRegisterError(task.exception?.message ?: "Unknown error")
                }
            }
            .addOnFailureListener { exception ->
                onRegisterError(exception.message ?: "Unknown error")
            }
    }

    fun signOut(onSignOut: () -> Unit) {
        firebaseAuth.signOut()
        _currentUser.value = null
        onSignOut()
    }
}
