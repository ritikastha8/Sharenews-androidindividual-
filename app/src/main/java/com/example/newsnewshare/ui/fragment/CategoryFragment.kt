package com.example.newsnewshare.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsnewshare.R
import com.example.newsnewshare.adapter.CategoryAdapter
import com.example.newsnewshare.databinding.FragmentCategoryBinding
import com.example.newsnewshare.repository.CategoryRepositoryImpl
import com.example.newsnewshare.ui.activity.AddcategoryActivity
import com.example.newsnewshare.ui.activity.LogoutActivity
import com.example.newsnewshare.ui.activity.OutputActivity
import com.example.newsnewshare.viewmodel.CategoryViewModel
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {


    private lateinit var binding: FragmentCategoryBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnaddcategory.setOnClickListener {
            val intent = Intent(requireContext(), AddcategoryActivity::class.java)
            startActivity(intent)
        }


        binding.btnview.setOnClickListener {
            val intent = Intent(requireContext(), OutputActivity        ::class.java)
            startActivity(intent)
        }

        binding.btnloggout.setOnClickListener {
            val intent = Intent(requireContext(), LogoutActivity        ::class.java)
            startActivity(intent)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}