package com.ddev.myapplication.view.fragment.bottomMenu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.FavoriteAdapter
import com.ddev.myapplication.databinding.FragmentFavoriteBinding
import com.ddev.myapplication.model.AddToCartModel
import com.ddev.myapplication.model.FavoriteModel
import com.ddev.myapplication.util.ClickListener
import com.ddev.myapplication.view.fragment.BaseFragment
import com.ddev.myapplication.view.viewmodel.DataReceiveViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.flow.collect

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate),ClickListener<FavoriteModel> {
    private val adapter by lazy {
        FavoriteAdapter(this)
    }
    var list = ArrayList<FavoriteModel>()
    private lateinit var db: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser
    private lateinit var currentUserId: String

    private val favoriteViewModel: DataReceiveViewModel by navGraphViewModels(R.id.bottom_nav) {
        SavedStateViewModelFactory(
            requireActivity().application,
            requireParentFragment()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser!!
        currentUserId = currentUser.uid

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            favoriteViewModel.getFavoriteProducts()
            favoriteViewModel.favoriteViewState.collect {favoriteList ->
                if (favoriteList != null) {
                    adapter.addItems(favoriteList)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        fragmentBinding.favoriteRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        fragmentBinding.favoriteRecyclerView.setHasFixedSize(true)
        fragmentBinding.favoriteRecyclerView.adapter = adapter
    }

    override fun onClick(item: FavoriteModel, position: Int) {
        db.collection("Users").document(currentUserId).collection("Favorite").document(item.productId!!)
            .delete()
            .addOnCompleteListener {
                adapter.notifyItemRemoved(position)
                adapter.notifyDataSetChanged()
                Log.d("delete success", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e ->
                Log.w("delete failed", "Error deleting document", e) }
    }
}