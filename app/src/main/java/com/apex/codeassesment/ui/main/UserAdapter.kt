package com.apex.codeassesment.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.apex.codeassesment.R
import com.apex.codeassesment.data.model.User


class UserAdapter(private val mList: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    var mListener: MyViewHolderClicks? = null
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_users, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        holder.parentView.setOnClickListener {
            mListener?.userData(itemsViewModel)
        }
        // sets the image to the imageview from our itemHolder class

        // sets the text to the textview from our itemHolder class
        holder.textView.text = "Name:"+itemsViewModel.name?.first + " email:${itemsViewModel.email}"+ " age:${itemsViewModel.dob?.age}"

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: AppCompatTextView = itemView.findViewById(R.id.textTitle)
        val parentView: ConstraintLayout = itemView.findViewById(R.id.parentView)
    }

    interface MyViewHolderClicks {
        fun userData(user: User)
    }
}