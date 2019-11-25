import com.zhongjiang.hotel.base.ui.basemvp.BasePresenter
import javax.inject.Inject

class TestFragmentPresenter @Inject constructor(mModel:SplashDataModel) : BasePresenter<TestPictureFragment>(mModel), TestFragmentContract.Presenter {
}