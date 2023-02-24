package com.gamestudiolab.psgaming.extension

import android.app.Activity
import android.app.AlertDialog
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun Activity.getExtensionPath(): String? {
    val fileName = "${packageName}.obb"
    val path = "${Environment.getExternalStorageDirectory().absolutePath}/Android/obb"
    val file = File("$path/$fileName")

    if (file.exists()) {
        return file.absolutePath
    }

    return try {
        val assetManager = applicationContext.assets
        val assetFileDescriptor = assetManager.openFd(fileName)
        val obbFilePath = "${filesDir.absolutePath}/$fileName"

        val inputStream = assetFileDescriptor.createInputStream()
        val outputStream = FileOutputStream(obbFilePath)

        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()

        obbFilePath
    } catch (e: IOException) {
        null
    }
}

fun Activity.getGamePath(): String? {
    val fileName = "${packageName}.iso"
    val gameFilePath = "${filesDir.absolutePath}/$fileName"

    return if (File(gameFilePath).exists()) {
        gameFilePath
    } else {
        try {
            val assetManager = applicationContext.assets
            val inputStream = assetManager.open(fileName)
            val outputStream = FileOutputStream(gameFilePath)

            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()

            gameFilePath
        } catch (e: IOException) {
            null
        }
    }
}