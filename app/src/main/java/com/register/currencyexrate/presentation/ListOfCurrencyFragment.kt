package com.register.currencyexrate.presentation

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.register.currencyexrate.R
import com.register.currencyexrate.databinding.FragmentListOfCurrencyBinding
import com.register.currencyexrate.databinding.FragmentSplashBinding
import com.register.currencyexrate.domain.entities.CurrencyInfo
import com.register.currencyexrate.presentation.adapters.CurrencyListAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class ListOfCurrencyFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as CurrencyApp).component
    }

    private var _binding: FragmentListOfCurrencyBinding? = null
    private val binding: FragmentListOfCurrencyBinding
        get() = _binding ?: throw RuntimeException("FragmentListOfCurrencyBinding == null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListOfCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar = Calendar.getInstance()
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
        val adapter = CurrencyListAdapter()
        binding.rvCurrency.adapter = adapter
        binding.rvCurrency.layoutManager = GridLayoutManager(requireContext(), 3)
        viewModel.currencyListDate.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        adapter.setOnItemClickListener(object: CurrencyListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val currentList = adapter.currentList
                val clickedItem = currentList[position]
                viewModel.getClickedItemName(clickedItem.name)
                viewModel.getClickedItemCount(clickedItem.value)
                viewModel.getClickedItemCharCode(clickedItem.charCode)
                launchCurrencyInfoFragment()
            }
        })
        binding.textInputEtData.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
                val selectedDateCalendar = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }
                val selectedDate = SimpleDateFormat("d MMMM yyyy", Locale("ru")).format(selectedDateCalendar.time)
                binding.textInputEtData.setText(selectedDate)

                val currentDate = Calendar.getInstance()
                if (selectedYear == currentDate.get(Calendar.YEAR) &&
                    selectedMonth == currentDate.get(Calendar.MONTH) &&
                    selectedDay == currentDate.get(Calendar.DAY_OF_MONTH)
                ) {
                    viewModel.getCurrency()
                } else {
                    val yearStr = selectedYear.toString()
                    val monthStr = (selectedMonth + 1).toString().padStart(2, '0')
                    val dayStr = selectedDay.toString().padStart(2, '0')
                    viewModel.getCurrencyWithDate(yearStr, monthStr, dayStr)
                }
            }, year, month, day)
            datePickerDialog.show()
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if(it) {
                binding.errorText.visibility = View.VISIBLE
            } else {
                binding.errorText.visibility = View.GONE
            }
        }
    }

    private fun launchCurrencyInfoFragment() {
        findNavController().navigate(R.id.action_listOfCurrencyFragment_to_currencyInfoFragment)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}