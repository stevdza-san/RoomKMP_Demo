package com.stevdza_san.demo.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.stevdza_san.demo.data.dao.BookDao
import com.stevdza_san.demo.domain.Book
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

@Database(
    entities = [Book::class],
    version = 1,
    exportSchema = true
)
@ConstructedBy(BookDatabaseConstructor::class)
@TypeConverters(BookTypeConverter::class)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor : RoomDatabaseConstructor<BookDatabase> {
    override fun initialize(): BookDatabase
}

class BookTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return Json.decodeFromString(
            ListSerializer(String.serializer()), value
        )
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Json.encodeToString(
            ListSerializer(String.serializer()), list
        )
    }
}