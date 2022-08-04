package com.ddev.myapplication.view.fragment.bottomMenu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddev.myapplication.R
import com.ddev.myapplication.adapter.FavoriteAdapter
import com.ddev.myapplication.databinding.FragmentFavoriteBinding
import com.ddev.myapplication.model.FavoriteModel
import com.ddev.myapplication.listener.ClickListener
import com.ddev.myapplication.util.dialog.LoadingDialog
import com.ddev.myapplication.view.fragment.BaseFragment
import com.ddev.myapplication.view.viewmodel.DataReceiveViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.collect

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate),
    ClickListener<FavoriteModel> {
    private val adapter by lazy {
        FavoriteAdapter(this)
    }
    var list = ArrayList<FavoriteModel>()
    private lateinit var db: FirebaseFirestore
    private lateinit var currentUser: FirebaseUser
    private lateinit var currentUserId: String
    lateinit var loadingDialog: LoadingDialog


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

        loadingDialog = LoadingDialog(requireContext())

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
        loadingDialog.show()
        db.collection("Users").document(currentUserId).collection("Favorite").document(item.productId!!)
            .delete()
            .addOnCompleteListener {task ->
                loadingDialog.show()
                if (task.isSuccessful){
                    list.clear()
                    list.remove(item)
                    adapter.notifyDataSetChanged()
                    loadingDialog.dismiss()
                }
                Log.d("delete success", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e ->
                Log.w("delete failed", "Error deleting document", e) }
    }
}