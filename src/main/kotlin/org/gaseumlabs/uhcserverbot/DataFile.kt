package org.gaseumlabs.uhcserverbot

import java.io.File
import java.io.BufferedReader
import java.io.FileReader
import java.util.ArrayList

object DataFile {
	data class DataReturn(val roleName: String, val imagePath: String, val color: Int)

	fun getData(directoryPath: String): ArrayList<DataReturn>? {
		/* files required to create RoleToggleData exist in the directory */
		val files = File(directoryPath).listFiles()

		return ArrayList(files.mapNotNull { file ->
			val filename = file.toPath().fileName.toString()

			/* every ___.txt file is a RoleToggleData entry */
			if (filename.endsWith(".txt")) {
				val roleId = filename.substring(0, filename.length - 4)

				/* get the color of the role from the .txt file */
				/* get the display name of the role too */
				val reader = BufferedReader(FileReader(file))
				val color = reader.readLine().toInt(16)
				val roleName = reader.readLine()
				reader.close()

				/* get the associated image from an image file with the same name */
				val imagePath = files.find { file2 ->
					file2.toPath().fileName.toString() == "$roleId.png"
				}?.absolutePath ?: return null

				DataReturn(roleName, imagePath, color)

			} else {
				null
			}
		})
	}
}