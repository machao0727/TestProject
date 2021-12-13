package com.machao.test_project.mvp.presenter

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.machao.test_project.mvp.contact.YearListContact
import com.machao.test_project.mvp.model.QuarterModel
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * create by：mc on 2021/12/13 9:58
 * email:
 *
 */
@RunWith(AndroidJUnit4::class)
class YearListPresenterTest : TestCase() {

    private lateinit var yearModel: QuarterModel

    private lateinit var yearListView: YearListContact.YearListView

    private lateinit var yearListPresenter:YearListPresenter

    @Before
    public override fun setUp() {
        super.setUp()
        yearModel=mock(QuarterModel::class.java)
        yearListView= mock(YearListContact.YearListView::class.java)
        yearListPresenter= YearListPresenter(yearListView)
    }

    @Test
    fun refreshing(){
        yearListPresenter.refresh()
        verify(yearListView).showLoading()
    }

    @Test
    fun refresh_success(){
        yearListPresenter.refresh()
        verify(yearListView).showContent()
    }

    @Test
    fun refresh_error(){
        yearListPresenter.refresh()
        verify(yearListView).showError()
    }

    @Test
    fun refresh_empty(){
        yearListPresenter.refresh()
        verify(yearListView).showEmpty()
    }

    public override fun tearDown() {}
}