package com.apex.codeassesment.ui.details

import android.content.Intent
import android.os.Parcel
import androidx.appcompat.app.AppCompatActivity
import com.apex.codeassesment.data.model.User
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailsActivityTest : AppCompatActivity() {

    @Mock
    lateinit var mockIntent: Intent
    @Mock
    private lateinit var imageValidator: ImageValidator
    @Mock
    lateinit var parcel: Parcel



    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(mockIntent.getParcelableExtra<User>("key"))
            .thenReturn(User.Companion.createRandom())
    }

    @Test
    fun testValidImageUrl() {
        // Arrange
        val imageUrl = "https://example.com/image.jpg"

        // Mock the behavior of the isValidImageUrl method
        `when`(imageValidator.isValidImageUrl(imageUrl)).thenReturn(true)

        // Act
        val isValid = imageValidator.isValidImageUrl(imageUrl)

        // Assert
        assertTrue(isValid)
    }

    @Test
    fun testInvalidImageUrl() {
        // Arrange
        val imageUrl = "https://example.com/document.pdf"

        // Mock the behavior of the isValidImageUrl method
        `when`(imageValidator.isValidImageUrl(imageUrl)).thenReturn(false)

        // Act
        val isValid = imageValidator.isValidImageUrl(imageUrl)

        // Assert
        assertFalse(isValid)
    }


    @Test
    fun `validate Data And Image Load`() {
        val originalData = User.Companion.createRandom()

        // Mock the behavior of the Parcel object for writing
        `when`(parcel.writeInt(Mockito.anyInt())).thenReturn(Unit)
        `when`(parcel.writeString(Mockito.anyString())).thenReturn(Unit)

        // Write the Parcelable object to the Parcel
        originalData.writeToParcel(parcel, 0)

        // Reset the Parcel to read mode
        `when`(parcel.readInt()).thenReturn(1)
        `when`(parcel.readString()).thenReturn("John")

        // Create a new Parcelable object by reading from the Parcel
        val createdData = User.createRandom()

        // Assert that the original and created Parcelable objects are equal
        assertEquals(originalData, createdData)
    }

}

class ImageValidator {
    fun isValidImageUrl(url: String): Boolean {
        // Implement your validation logic here
        // For example, you can use regular expressions to check if the URL ends with an image file extension
        val regex = Regex("\\.(jpeg|jpg|png|gif|bmp)\$", RegexOption.IGNORE_CASE)
        return regex.containsMatchIn(url)
    }
}