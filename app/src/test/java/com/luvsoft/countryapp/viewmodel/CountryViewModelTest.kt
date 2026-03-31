@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package com.luvsoft.countryapp.viewmodel
import androidx.lifecycle.Observer
import com.luvsoft.base.viewmodels.models.FinishActivityModel
import com.luvsoft.base.viewmodels.models.StartActionModel
import com.luvsoft.base.viewmodels.models.StartActivityModel
import com.luvsoft.core.repositories.CountryRepository
import com.luvsoft.core.ui.Country
import com.luvsoft.rooom.network.api.CountryApi
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.mock

class CountryViewModelTest {

    private lateinit var apiRoom: CountryApi
    private lateinit var repository: CountryRepository
    private lateinit var viewmodel: CountryViewModel

    @Mock
    lateinit var dataObserve: Observer<StartActivityModel>

    @Mock
    lateinit var closeViewObserver: Observer<FinishActivityModel>

    @Mock
    lateinit var startAction: Observer<StartActionModel>

    @Mock
    lateinit var loading: Observer<Boolean>

    @Mock
    lateinit var countriesToList: Observer<List<Country>>

    @Mock
    lateinit var error: Observer<Boolean>

    @Mock
    lateinit var load: Observer<Boolean>

    @Before
    fun setUp() {
        apiRoom = mock()
        init()
    }

    fun init() {
        viewmodel = CountryViewModel(repository, apiRoom)
        viewmodel.loaderState().observeForever(loading)
        viewmodel.startAction().observeForever(startAction)
        viewmodel.closeView().observeForever(closeViewObserver)
        viewmodel.startActivity().observeForever(dataObserve)
        viewmodel.onCountriesFoundChange().observeForever(countriesToList)
        viewmodel.onError().observeForever(error)
        viewmodel.onLoading().observeForever(load)
    }
}