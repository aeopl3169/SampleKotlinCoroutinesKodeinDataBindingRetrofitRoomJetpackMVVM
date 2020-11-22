package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.home.quotes

import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.R
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.Quote
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.databinding.SingleRowQuoteBinding
import com.xwray.groupie.databinding.BindableItem

//import com.xwray.groupie.databinding.BindableItem

/**
 * Groupie needs List of quotes
 *
 * @property quote
 */
class QuoteItem(private val quote: Quote) : BindableItem<SingleRowQuoteBinding>() {

    override fun getLayout(): Int = R.layout.single_row_quote

    override fun bind(viewBinding: SingleRowQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }

}