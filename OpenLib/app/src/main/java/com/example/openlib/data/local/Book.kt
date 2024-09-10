package com.example.openlib.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Create a entity Book
 */
@Entity
data class Book(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,  // Valeur par défaut pour l'ID dans la base de données

    // Correspond au "title" dans la réponse JSON de l'API
    var title: String = "",

    // Correspond à "author_name" dans la réponse JSON
    @SerializedName("author_name")
    var author: List<String> = emptyList(),  // Liste des auteurs (chaque livre peut avoir plusieurs auteurs)

    // Correspond à "cover_i" dans la réponse JSON, c'est l'ID de la couverture
    @SerializedName("cover_i")
    var coverUrl: Int? = null,  // URL ou ID de la couverture, nullable

    // Correspond à "first_publish_year" dans la réponse JSON
    @SerializedName("first_publish_year")
    var firstPublishYear: Int? = null,  // Année de la première publication, nullable

    // Correspond à "publisher" dans la réponse JSON
    var publisher: List<String> = emptyList(),  // Liste des éditeurs, vide par défaut

    // Correspond à "description" dans la réponse JSON (pas toujours présent)
    var description: String? = null  // La description du livre, nullable si non présente
)