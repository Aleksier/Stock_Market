package com.example.stockmarket

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class CompanyAdapter(
        val context: Context,
        var companies: List<Company>,
        private val addToFavoritesPosition: (Int) -> Unit,
        val view: View
) : RecyclerView.Adapter<CompanyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE
        return CompanyViewHolder(inflater.inflate(R.layout.item_company, parent, false), addToFavoritesPosition, context)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(companies[position])
    }

    override fun getItemCount(): Int = companies.size

}

data class Company(
    val name: String,
    val ticker: String,
    val quote: String,
    val previousQuote: String
)

class CompanyViewHolder(
    view: View,
    private val addToFavoritesPosition: (Int) -> Unit,
    val context: Context
) : RecyclerView.ViewHolder(view) {
    private val quote: TextView = view.findViewById(R.id.tv_company_quote)
    private val ticker: TextView = view.findViewById(R.id.tv_company_ticker)
    private val name: TextView = view.findViewById(R.id.tv_company_name)
    private val difference: TextView = view.findViewById(R.id.tv_company_diff_in_quote)
    private val addToFavorites: ImageView = view.findViewById(R.id.add_to_favorites)
    // private var listener: AddingToFavorites? = null

    init {
        addToFavorites.setOnClickListener {
            var position: Int? = null
            outer@for (companyN in Data.listOfNames) {
                if (name.text == companyN) {
                    position = Data.listOfNames.indexOf(companyN)
                        break@outer
                }
            }
            val sharedPreferences: SharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            val map = sharedPreferences.all
            if(map.values.contains(position)){
                editor.remove("$position")
                editor.commit()
                it.setBackgroundResource(R.drawable.ic_baseline_star_border)
            }
            else {
                addToFavoritesPosition(position!!)
                it.setBackgroundResource(R.drawable.ic_baseline_star)
            }
        }
    }
    fun bind (company: Company) {
        ticker.text = company.ticker
        quote.text = "$${company.quote}"
        name.text = company.name

        val diffrenceInQuote = company.quote.substring(0, company.quote.length - 1).toFloat() - company.previousQuote.substring(0, company.previousQuote.length -1).toFloat() + 0.0000000001
        val percentageDiffrence = diffrenceInQuote / company.quote.toFloat() + 0.000000000001
        val diffrenceString = diffrenceInQuote.toString().substringBefore('.') + "." +  diffrenceInQuote.toString().substringAfter('.').substring(0, 1)
        val percentageDiffrenceString = percentageDiffrence.toString().substringBefore('.') + "." +  percentageDiffrence.toString().substringAfter('.').substring(0, 2)

        if (diffrenceInQuote > 0) {
            difference.text = "+S" + diffrenceString + " ($percentageDiffrenceString)"
            difference.setTextColor(Color.GREEN)
        }
        if (diffrenceInQuote < 0) {
            difference.text = "-S" + diffrenceString + " ($percentageDiffrenceString)"
            difference.setTextColor(Color.RED)
        }
        if (diffrenceInQuote == 0.0) {
            difference.text = "+S" + diffrenceString + " ($percentageDiffrenceString)"
            difference.setTextColor(Color.YELLOW)
        }
        var position: Int? = null
        outer@for (companyN in Data.listOfNames) {
            if (name.text == companyN) {
                position = Data.listOfNames.indexOf(companyN)
                break@outer
            }
        }

        val sharedPreferences: SharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        val map = sharedPreferences.all
        if(map.values.contains(position)){
            addToFavorites.setBackgroundResource(R.drawable.ic_baseline_star)
        }
        else {
            addToFavorites.setBackgroundResource(R.drawable.ic_baseline_star_border)
        }

    }

}
/*
interface AddingToFavorites {
    fun addToFavorites()
}
*/