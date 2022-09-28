package com.amg.marvel.ui.main.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.amg.config.NetworkStatus
import com.amg.domain.model.character.Character
import com.amg.marvel.R
import com.amg.marvel.databinding.FragmentMainBinding
import com.amg.marvel.ui.main.viewmodel.MainViewModel
import com.amg.marvel.ui.main.adapters.CharacterAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: CharacterAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.shows.observe(this) {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        switchListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCharactersList()
    }


    private fun initializeRv(list: List<Character>, context: Context){
        adapter = CharacterAdapter(list, goDetail())
        binding.rvCatalog.layoutManager = GridLayoutManager(context, 2)
        binding.rvCatalog.adapter = adapter
        hideLoader()
    }

    private fun goDetail() : (Character) -> Unit{
        return {
            val directions = MainFragmentDirections.actionFragmentListToFragmentDetail(it.id.toString())
            view?.findNavController()?.navigate(directions)
        }
    }

    private fun showLoader(){
        if(binding.loadingBar.visibility == View.GONE){
            binding.loadingBar.visibility = View.VISIBLE
            binding.rvCatalog.visibility = View.GONE
        }
    }

    private fun hideLoader(){
        if(binding.loadingBar.visibility == View.VISIBLE){
            binding.loadingBar.visibility = View.GONE
            binding.rvCatalog.visibility = View.VISIBLE
        }
    }
    private fun switchListener(){
        binding.switchList?.setOnCheckedChangeListener { _, isChecked ->
            var message: String
            if (isChecked) {
                message = getString(R.string.label_layout_grid_type)
                binding.rvCatalog.layoutManager = LinearLayoutManager(context)
                binding.rvCatalog.adapter?.notifyDataSetChanged()
            }else {
                message = getString(R.string.label_layout_list_type)
                binding.rvCatalog.layoutManager = GridLayoutManager(context, 2)
                binding.rvCatalog.adapter?.notifyDataSetChanged()
            }
            binding.switchList.text = message
        }
    }
}