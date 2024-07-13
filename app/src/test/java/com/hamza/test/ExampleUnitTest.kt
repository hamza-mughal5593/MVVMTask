package com.hamza.test

import com.hamza.test.ui.feature.categories.MedCategoriesViewModel
import com.mycomposeproj.data.repository.MedicineRepository
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }



    @MockK
    private lateinit var userDao: UserDao

    @MockK
    private lateinit var medicineRepository: MedicineRepository

    private lateinit var viewModel: MedCategoriesViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = MedCategoriesViewModel(userDao, medicineRepository)
    }

    @Test
    fun login_shouldStoreUserInDb() = runBlocking {
        val user = User("test@example.com", "Test User")

        viewModel.login(user.email, user.name)

        verify { userDao.insertUser(user) }
    }

    @Test
    fun fetchMedicines_shouldUpdateLiveData() = runBlocking {
        val medicines = listOf(Medicine("Med1", "1 dose", "10mg"))

        coEvery { medicineRepository.getMedicines() } returns medicines

        viewModel.fetchMedicines()

        assertEquals(medicines, viewModel.medicines.value)
    }

    @Test
    fun userLiveData_shouldBeUpdatedOnLogin() = runBlocking {
        val user = User("test@example.com", "Test User")

        viewModel.login(user.email, user.name)

        assertEquals(user, viewModel.user.value)
    }


}