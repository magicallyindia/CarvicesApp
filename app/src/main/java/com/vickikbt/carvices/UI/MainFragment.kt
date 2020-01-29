package com.vickikbt.carvices.UI


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceOnClickListener
import com.vickikbt.carvices.R
import com.vickikbt.carvices.ViewModels.MainFragmentViewModel
import com.vickikbt.carvices.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)

        binding.spaceBottomNavigation.initWithSaveInstanceState(savedInstanceState)
        binding.spaceBottomNavigation.addSpaceItem(SpaceItem("SETTINGS", R.drawable.ic_menu))
        binding.spaceBottomNavigation.addSpaceItem(SpaceItem("PROFILE", R.drawable.ic_person))

        binding.spaceBottomNavigation.setSpaceOnClickListener(object :
            SpaceOnClickListener {
            override fun onCentreButtonClick() {
                Toast.makeText(activity, "onCentreButtonClick", Toast.LENGTH_SHORT).show()
            }

            override fun onItemClick(itemIndex: Int, itemName: String) {
                when (itemIndex) {
                    0 -> {
                        val fragment: Fragment = SettingsFragment()
                        val transaction = fragmentManager!!.beginTransaction()
                        transaction.replace(R.id.myNavHostFragment, fragment).commit()
                    }
                    1 -> {
                        val fragment: Fragment = ProfileFragment()
                        val transaction = fragmentManager!!.beginTransaction()
                        transaction.replace(R.id.myNavHostFragment, fragment).commit()
                    }
                }
            }

            override fun onItemReselected(itemIndex: Int, itemName: String) {
                when (itemIndex) {
                    0 -> {
                        val fragment: Fragment = SettingsFragment()
                        val transaction = fragmentManager!!.beginTransaction()
                        transaction.replace(R.id.myNavHostFragment, fragment).commit()
                    }
                    1 -> {
                        val fragment: Fragment = ProfileFragment()
                        val transaction = fragmentManager!!.beginTransaction()
                        transaction.replace(R.id.myNavHostFragment, fragment).commit()
                    }
                }
            }
        })

        return binding.root
    }


}
