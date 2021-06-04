package app.first.my_deb.database

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertGame(game: GameEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertGamers(gamers: List<GamerEntity>)

    suspend fun upsertByReplacementGame(gameAndGamers: GameWithGamers) {
        val gameId = upsertGame(gameAndGamers.game)
        gameAndGamers.gamers.forEach { x -> x.gameId = gameId.toInt() }
        upsertGamers(gameAndGamers.gamers)
    }

    @Query("UPDATE game SET isActive = 0 WHERE id = :id;")
    suspend fun makeGameInactive(id: Int)

    @Transaction
    @Query("SELECT * FROM game WHERE isActive = 1 AND type = :type")
    suspend fun getActiveGame(type: String): GameWithGamers

    @Query("SELECT EXISTS (SELECT 1 FROM game WHERE isActive = 1 AND type = :type)")
    fun activeGameExists(type: String): Boolean

    @Transaction
    @Query("SELECT * FROM game WHERE isActive = 0")
    suspend fun getInactiveGame(): List<GameWithGamers>

    @Query("DELETE FROM game")
    suspend fun nukeTable()
}