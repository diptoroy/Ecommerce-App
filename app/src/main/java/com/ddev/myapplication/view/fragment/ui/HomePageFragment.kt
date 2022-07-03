package com.ddev.myapplication.view.fragment.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ddev.myapplication.R
import com.ddev.myapplication.databinding.FragmentHomePageBinding
import com.ddev.myapplication.model.AddToCartModel
import com.ddev.myapplication.view.fragment.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.coroutines.delay


class HomePageFragment : BaseFragment<FragmentHomePageBinding>(FragmentHomePageBinding::inflate) {
    private lateinit var db: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser
    private lateinit var currentUserId: String
    private var cartList = ArrayList<AddToCartModel>()
    private var badgeNo: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()

        currentUser = FirebaseAuth.getInstance().currentUser!!
        currentUserId = currentUser.uid

        val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.fragmentContainerView2) as? NavHostFragment

        val navController = nestedNavHostFragment?.navController

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        if (navController != null) {
            bottomNavigationView.setupWithNavController(navController)
        } else {
            throw RuntimeException("Controller not found")
        }


        db.collection("Users").document(currentUserId).collection("AddToCart")
            .addSnapshotListener { value, error ->
                for (doc: DocumentChange in value!!.documentChanges) {
                    cartList.add(doc.document.toObject(AddToCartModel::class.java))
                    badgeNo = cartList.size.toString()
                }
            }

        fragmentBinding.toolbar.notificationContainer.text = badgeNo
        if (fragmentBinding.toolbar.notificationContainer.text == "0"){
            fragmentBinding.toolbar.notificationContainer.visibility = View.GONE
        }else{
            fragmentBinding.toolbar.notificationContainer.visibility = View.VISIBLE
            fragmentBinding.toolbar.notificationContainer.text = badgeNo
        }
    }

}