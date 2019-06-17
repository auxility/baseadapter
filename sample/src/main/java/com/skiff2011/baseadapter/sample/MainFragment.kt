package com.skiff2011.baseadapter.sample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.skiff2011.baseadapter.sample.databinding.FragmentMainBinding

class MainFragment : Fragment() {

  private lateinit var binding: FragmentMainBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
    binding.baseAdapterBtn.setOnClickListener { btn ->
      btn.findNavController()
          .navigate(R.id.action_mainFragment_to_baseAdapterSampleFragment)
    }
    binding.filterableAdapterBtn.setOnClickListener { btn ->
      btn.findNavController()
          .navigate(R.id.action_mainFragment_to_filterableAdapterSampleFragment)
    }
    return binding.root
  }
}