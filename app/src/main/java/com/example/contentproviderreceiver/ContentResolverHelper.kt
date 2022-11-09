package com.example.contentproviderreceiver

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log

class ContentResolverHelper(context: Context) {

    private var contentResolver: ContentResolver = context.contentResolver

    /**
     * select all items
     */
    fun getAllItems() : List<Item>{

        val cursor = contentResolver.query(ReceiverContract.CONTENT_URI, null, null, null, null)
        val result : MutableList<Item> = mutableListOf()

        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {

                val itemIdIndex = cursor.getColumnIndex("itemId")
                val titleIndex = cursor.getColumnIndex("key")
                val contentIndex = cursor.getColumnIndex("value")

                val id = cursor.getLong(itemIdIndex)
                val title = cursor.getString(titleIndex)
                val content = cursor.getString(contentIndex)

                Log.v(">>>", "@# id[$id] key[$title] value[$content]")

                result.add(Item(id, title, content))
            }
        }

        return result
    }

    /**
     * select single item
     */
    fun getItem(id: Long) {

        val cursor = contentResolver.query(ReceiverContract.CONTENT_URI, null, "id", arrayOf("$id"), null)

        if (cursor != null && cursor.count > 0) {

            while (cursor.moveToNext()) {

                val itemIdIndex = cursor.getColumnIndex("itemId")
                val titleIndex = cursor.getColumnIndex("key")
                val contentIndex = cursor.getColumnIndex("value")

                val id = cursor.getLong(itemIdIndex)
                val title = cursor.getString(titleIndex)
                val content = cursor.getString(contentIndex)

                Log.v(">>>", "@# id[$id] key[$title] content[$content]")
            }
        }
    }

    /**
     * Insert
     */
    fun insertItem(title: String, content: String) {

        val contentValues = generateItem(title, content)
        contentResolver.insert(ReceiverContract.CONTENT_URI, contentValues)
    }

    /**
     * Remove
     */
    fun removeItem(id: Long) {

        val uriInfo = "${ReceiverContract.URI_STRING}/$id"

        contentResolver.delete(Uri.parse(uriInfo), "id", arrayOf("$id"))
    }

    /**
     * Item 생성 (ContentValues)
     */
    private fun generateItem(title: String, content: String): ContentValues {

        val values = ContentValues()
        values.put("key", title)
        values.put("value", content)

        return values
    }

    /**
     * 커스텀 메서드 - id 가져오기
     */
    fun buildingDataBaseOfGiverApp(): String? {
        var value: String? = null

        val bundle: Bundle? = contentResolver.call(ReceiverContract.CONTENT_URI, "getId", null, null)

        bundle?.let {

            val id = it.getString("id")
            Log.v(">>>", "customMethodGetId : $id")
            value = id
        }

        return value
    }
}