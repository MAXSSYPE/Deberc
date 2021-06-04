package app.first.my_deb.database

import androidx.room.*

@Entity(tableName = "game")
data class GameEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        var type: String? = null,
        @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
        var isActive: Boolean? = null
)

@Entity(tableName = "gamer",
foreignKeys = [ForeignKey(
        entity = GameEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("gameId"),
        onDelete = ForeignKey.CASCADE)])
data class GamerEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        var gameId: Int? = null,
        var number: Int? = null,
        var name: String? = null,
        var score: Int? = null,
        @TypeConverters(Convertor::class)
        var gameScore: MutableList<String>? = null
)

data class GameWithGamers(
        @Embedded val game: GameEntity,
        @Relation(
                parentColumn = "id",
                entityColumn = "gameId"
        )
        val gamers: List<GamerEntity>
)
