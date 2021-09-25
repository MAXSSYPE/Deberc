package app.first.my_deb.database

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertGame(game: GameEntity): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertGamers(gamers: List<GamerEntity>)

    @Transaction
    suspend fun upsertByReplacementGame(gameAndGamers: GameWithGamers) {
        val gameId = upsertGame(gameAndGamers.game)
        gameAndGamers.gamers.forEach { x -> x.gameId = gameId.toInt() }
        upsertGamers(gameAndGamers.gamers)
    }

    @Transaction
    suspend fun addGameToInactive(id: Int) {
        val oldGames = ArrayList<GameWithGamers>(getInactiveGames())

        while (oldGames.size >= 10) {
            nukeGame(oldGames.last().game.id!!)
            oldGames.removeLast()
        }

        makeGameInactive(id)
    }

    @Query("UPDATE game SET isActive = 0 WHERE id = :id;")
    suspend fun makeGameInactive(id: Int)

    @Query("UPDATE game SET isActive = 1 WHERE id = :id;")
    suspend fun makeGameActive(id: Int)

    @Query("UPDATE game SET endTimestamp = :date WHERE id = :id;")
    suspend fun setEndTime(id: Int, date: Long)

    @Transaction
    @Query("SELECT * FROM game WHERE isActive = 1 AND type = :type")
    suspend fun getActiveGame(type: String): GameWithGamers

    @Query("SELECT EXISTS (SELECT 1 FROM game WHERE isActive = 1 AND type = :type)")
    fun activeGameExists(type: String): Boolean

    @Transaction
    @Query("SELECT * FROM game WHERE isActive = 0 ORDER BY endTimestamp DESC")
    suspend fun getInactiveGames(): List<GameWithGamers>

    @Query("DELETE FROM game")
    suspend fun nukeTable()

    @Query("DELETE FROM game WHERE id = :id")
    suspend fun nukeGame(id: Int)
}