package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.home.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.R
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.Quote
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils.Coroutines
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils.hide
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val factory: QuotesViewModelFactory by instance()
/*
    companion object {
        fun newInstance() = QuotesFragment()
    }
*/

    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(QuotesViewModel::class.java)

        bindUI()
//        Coroutines.main{
//            // Calling the LiveDate quotes from ViewModel inside coroutines.
//            val quotes = viewModel.quotes.await() // "await" should be called inside coroutines.
//            /**
//             *  observing the quotes.
//             *  If onDestroyView() is called, but onDestroy() is not, you will continue observing the LiveData,
//             * perhaps crashing when you try populating a non-existent RecyclerView.
//             * By using "viewLifecycleOwner", you avoid that risk. So don't use this.
//             */
//            quotes.observe(viewLifecycleOwner, Observer {
//                context?.toast(it.size.toString())
//            })
//        }
    }

    private fun bindUI() = Coroutines.main {
        // Observing the LiveData
        viewModel.quotes.await().observe(viewLifecycleOwner, Observer {
            progress_bar.hide()
            // Using extension function(toQuoteItem) to convert List<Quote> to List<QuoteItem>
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun initRecyclerView(toQuoteItem: List<QuoteItem>) {
        // creating adapter
        val mAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(toQuoteItem)
        }

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    // Extension function to convert List<Quote> to List<QuoteItem>
    private fun List<Quote>.toQuoteItem(): List<QuoteItem> {
        return this.map {
            QuoteItem(it)
        }
    }

}
