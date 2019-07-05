package ca.auxility.baseadapter.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import ca.auxility.baseadapter.sample.R.layout
import ca.auxility.baseadapter.sample.databinding.FragmentMainBinding

class MainFragment : androidx.fragment.app.Fragment() {

  private lateinit var binding: FragmentMainBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(
        inflater,
        layout.fragment_main, container, false
    )
    binding.baseAdapterBtn.setOnClickListener { btn ->
      btn.findNavController()
          .navigate(
              R.id.action_mainFragment_to_baseAdapterSampleFragment
          )
    }
    binding.filterableAdapterBtn.setOnClickListener { btn ->
      btn.findNavController()
          .navigate(
              R.id.action_mainFragment_to_filterableAdapterSampleFragment
          )
    }
    return binding.root
  }
}