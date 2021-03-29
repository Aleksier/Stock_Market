package com.example.stockmarket

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import java.util.*


class CompanyListFragment : Fragment(), Observer<List<Company>> {
    private var recycler: RecyclerView? = null
    private var button: ImageView? = null
    var displayList: MutableList<Company> = mutableListOf()
    private var isOpenedListIsFavorite: Boolean = false
    lateinit var viewModel: CompaniesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.list_of_stocks_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        button = view.findViewById(R.id.favorite_button)
        val searchView = view.findViewById<SearchView>(R.id.search_view)
        viewModel = ViewModelProvider(this).get(CompaniesViewModel::class.java)

        viewModel.companies.observe(this.viewLifecycleOwner, this)

        recycler = view.findViewById(R.id.rv_companies)
        recycler?.layoutManager = LinearLayoutManager(requireContext())
        setOnButtonClickListener()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                val map = sharedPreferences.all
                if(newText!!.isNotEmpty()) {
                    displayList.clear()
                    val search = newText.toLowerCase(Locale.getDefault())
                    if (isOpenedListIsFavorite == true) {
                        for (i in (0..Data.listOfNames.size - 1)) {
                            if (Data.listOfCompanies[i].name.toLowerCase(Locale.getDefault()).contains(search) && map.values.contains(i)) {
                                displayList.add(Data.listOfCompanies[i])
                            }
                        }
                    }
                    if (isOpenedListIsFavorite == false) {
                        for (i in (0..Data.listOfNames.size - 1)) {
                            if (Data.listOfCompanies[i].name.toLowerCase(Locale.getDefault()).contains(search)) {
                                displayList.add(Data.listOfCompanies[i])
                            }
                        }
                    }
                    recycler?.adapter = CompanyAdapter(requireContext(), displayList, this@CompanyListFragment::add, view)
                    recycler?.adapter!!.notifyDataSetChanged()
                }

                else {
                    displayList.clear()
                    displayList.addAll(Data.listOfCompanies)
                    recycler?.adapter = CompanyAdapter(requireContext(), displayList, this@CompanyListFragment::add, view)
                    recycler?.adapter!!.notifyDataSetChanged()
                }

                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }
        })
    }

    private fun setOnButtonClickListener() {
        button?.setOnClickListener {
            isOpenedListIsFavorite = !isOpenedListIsFavorite
            if (isOpenedListIsFavorite == true) {
                val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                val map = sharedPreferences.all
                displayList.clear()
                for (i in map.values) {
                    displayList.add(Data.listOfCompanies[i.toString().toInt()])
                }
                recycler?.adapter = CompanyAdapter(requireContext(), displayList, this@CompanyListFragment::add, requireView())
                recycler?.adapter!!.notifyDataSetChanged()
                button?.setBackgroundResource(R.drawable.ic_baseline_star)
            }
            else {
                button?.setBackgroundResource(R.drawable.ic_baseline_star_border)
                displayList = Data.listOfCompanies.toList().toMutableList()
                recycler?.adapter = CompanyAdapter(requireContext(), displayList, this@CompanyListFragment::add, requireView())
                recycler?.adapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onChanged(t: List<Company>) {
        recycler?.adapter = CompanyAdapter(requireContext(), t, this::add, requireView())
    }


    fun add(position: Int) {
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt("$position", position)
        editor.apply()
    }
}

