package com.apex.codeassesment.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.apex.codeassesment.R
import com.apex.codeassesment.data.UserRepository
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ActivityMainBinding
import com.apex.codeassesment.di.MainComponent
import com.apex.codeassesment.ui.details.DetailsActivity
import com.bumptech.glide.Glide
import javax.inject.Inject

// TODO (5 points): Move calls to repository to Presenter or ViewModel.
// TODO (5 points): Use combination of sealed/Dataclasses for exposing the data required by the view from viewModel .
// TODO (3 points): Add tests for viewmodel or presenter.
// TODO (1 point): Add content description to images
// TODO (3 points): Add tests
// TODO (Optional Bonus 10 points): Make a copy of this activity with different name and convert the current layout it is using in
//  Jetpack Compose.
class MainActivity : AppCompatActivity(), UserAdapter.MyViewHolderClicks {

  // TODO (2 points): Convert to view binding

  @Inject lateinit var userRepository: UserRepository
  private lateinit var activityMainBinding: ActivityMainBinding

  private var randomUser: User = User()
    set(value) {
      // TODO (1 point): Use Glide to load images after getting the data from endpoints mentioned in RemoteDataSource
      Glide.with(this).load(value.picture).placeholder(R.drawable.no_image_found)
        .into(activityMainBinding.mainImage)
      activityMainBinding.mainName.text = value.name!!.first
      activityMainBinding.mainEmail.text = value.email
      field = value
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
    setContentView(activityMainBinding.root)
    sharedContext = this

    (applicationContext as MainComponent.Injector).mainComponent.inject(this)

//    val arrayAdapter = ArrayAdapter<User>(this, android.R.layout.simple_list_item_1)
//    userImageView = findViewById(R.id.main_image)
//    userNameTextView = findViewById(R.id.main_name)
//    userEmailTextView = findViewById(R.id.main_email)
//    seeDetailsButton = findViewById(R.id.main_see_details_button)
//    refreshUserButton = findViewById(R.id.main_refresh_button)
//    showUserListButton = findViewById(R.id.main_user_list_button)
//    userListView = findViewById(R.id.main_user_list)
//    userListView!!.adapter = arrayAdapter


    randomUser = userRepository.getSavedUser()

    activityMainBinding.mainSeeDetailsButton.setOnClickListener { navigateDetails(randomUser) }

    activityMainBinding.mainRefreshButton.setOnClickListener { randomUser = userRepository.getUser(true) }

    activityMainBinding.mainUserListButton.setOnClickListener {
      val users = userRepository.getUsers()
      val adapter = UserAdapter(users)
      activityMainBinding.mainUserList.layoutManager = LinearLayoutManager(this)
      activityMainBinding.mainUserList.adapter = adapter
    }
  }

  // TODO (2 points): Convert to extenstion function.
  private fun navigateDetails(user: User) {
    val putExtra = Intent(this, DetailsActivity::class.java).putExtra("saved-user-key", user)
    startActivity(putExtra)
  }

  companion object {
    @SuppressLint("StaticFieldLeak")
    var sharedContext: Context? = null
  }

  override fun userData(user: User) {
    navigateDetails(user)
  }
}
