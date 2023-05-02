package de.hsflensburg.model

import com.benasher44.uuid.uuid4
import com.benasher44.uuid.uuidFrom
import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    val author: String,
    val title: String,
    val owner: String,
    val lentTo: String
){
    constructor(author: String, title: String, owner: String, lentTo: String) : this(uuidFrom(uuid4().toString()).toString(), author, title, owner, "noone")
}


