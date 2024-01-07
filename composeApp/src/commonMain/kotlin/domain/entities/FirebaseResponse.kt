package domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FirebaseResponseLogin(
    val kind: String,
    val idToken: String,
    val email: String,
    val refreshToken: String,
    val expiresIn: String,
    val displayName:String,
    val registered:Boolean,
    @SerialName("localId")val userId: String
)
@Serializable
data class FirebaseLoginRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean

)
@Serializable
data class FirebaseErrorResponse(
    val error: FirebaseErrorDetails
)
@Serializable
data class FirebaseErrorDetails(
    val code: Int,
    val message: String,
    val errors: List<FirebaseSpecificError>
)

@Serializable
data class FirebaseSpecificError(
    val message: String,
    val domain: String,
    val reason: String
)