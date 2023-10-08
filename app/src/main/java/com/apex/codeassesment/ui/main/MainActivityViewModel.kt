package com.apex.codeassesment.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.apex.codeassesment.data.UserRepository
import javax.inject.Inject

@ViewModelFactoryDsl
class MainActivityViewModel @Inject constructor(
    var userRepository: UserRepository
) : ViewModel() {

    fun getRandomUsers() = userRepository.getSavedUser()
    fun getRefreshedUsers() = userRepository.getUser(true)
    fun getUser() = userRepository.getUsers()
}