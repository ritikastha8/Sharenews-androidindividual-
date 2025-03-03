package com.example.newsnewshare

import com.example.newsnewshare.model.CategoryModel
import com.example.newsnewshare.repository.CategoryRepoImpl
import com.example.newsnewshare.repository.CategoryRepositoryImpl
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CategoryRepoTest {
    @Mock
    private lateinit var mockDatabase: FirebaseDatabase

    @Mock
    private lateinit var mockDatabaseRef: DatabaseReference

    @Mock
    private lateinit var mockTask: Task<Void>

    @Captor
    private lateinit var captor: ArgumentCaptor<OnCompleteListener<Void>>

    private lateinit var categoryRepo: CategoryRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(mockDatabase.reference).thenReturn(mockDatabaseRef)
        categoryRepo = CategoryRepositoryImpl(mockDatabase)
    }

    @Test
    fun testAddCategory_Successful() {
        val categoryModel = CategoryModel("sa", 1,"jk")
        var expectedResult = "Initial Value"

        val id = "testCategoryId"
        `when`(mockDatabaseRef.push()).thenReturn(mockDatabaseRef)
        `when`(mockDatabaseRef.key).thenReturn(id)
        `when`(mockDatabaseRef.child(id)).thenReturn(mockDatabaseRef)
        `when`(mockDatabaseRef.setValue(categoryModel)).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(true)

        val callback = { success: Boolean, message: String ->
            expectedResult = message
        }

        categoryRepo.addCategory(categoryModel, callback)

        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        assertEquals("Category Added successfully", expectedResult)
    }
}
