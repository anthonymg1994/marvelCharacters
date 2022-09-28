package com.amg.marvel.ui.main.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amg.config.NetworkStatus
import com.amg.domain.model.character.Character
import com.amg.domain.model.comics.Comic
import com.amg.marvel.R
import com.amg.marvel.databinding.FragmentDetailBinding
import com.amg.marvel.ui.main.adapters.ComicsAdapter
import com.amg.marvel.ui.main.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var adapter: ComicsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       observers()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        if(bundle == null){
            Log.d(getString(R.string.tag_args_fragment), getString(R.string.error_fragment_not_params))
            return
        }
        val args = DetailFragmentArgs.fromBundle(bundle)
        if(args.character.isNullOrBlank()){
            view?.findNavController()?.navigateUp()
        }
        viewModel.getCharacterDetail(args.character.toInt())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun observers(){
        viewModel.character.observe(this) {
            when (it.status) {
                NetworkStatus.LOADING -> showLoader()
                NetworkStatus.SUCCESS -> setModel(it.data)
                NetworkStatus.SERVER_ERROR -> {
                    hideLoader()
                    Toast.makeText(activity?.applicationContext, "${it.message}", Toast.LENGTH_LONG).show()
                }
                else -> showLoader()
            }
        }
        viewModel.comics.observe(this) {
            when (it.status) {
                NetworkStatus.LOADING -> showLoader()
                NetworkStatus.SUCCESS -> initializeRv(it.data ?: emptyList(), requireContext())
                NetworkStatus.SERVER_ERROR -> {
                    hideLoader()
                    Toast.makeText(activity, "${it.message}", Toast.LENGTH_LONG).show()
                }
                else -> showLoader()
            }
        }
    }

    private fun initializeRv(list: List<Comic>, context: Context){
        adapter = ComicsAdapter(list)
        binding.rvComics.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvComics.adapter = adapter
        hideLoader()
    }

    private fun setModel(character: Character?){
        hideLoader()
        character.let {
            binding.character = it
            binding.executePendingBindings()
        }
    }

    private fun showLoader(){
        if(binding.loadingBar.visibility == View.GONE){
            binding.loadingBar.visibility = View.VISIBLE
        }
    }

    private fun hideLoader(){
        if(binding.loadingBar.visibility == View.VISIBLE){
            binding.loadingBar.visibility = View.GONE
        }
    }
}