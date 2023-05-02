package de.hsflensburg.model

import com.benasher44.uuid.uuid4
import com.benasher44.uuid.uuidFrom
import kotlinx.serialization.Serializable

@Serializable
data class User(val id : String,
                val username : String,
                val password : String,
                val role : String){
    constructor(username: String, password: String, role: String) : this(uuidFrom(uuid4().toString()).toString(), username, password, role)
}
