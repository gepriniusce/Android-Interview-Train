package pr.tongson.train_mvx.mvvm.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import pr.tongson.train_mvx.databinding.MainFragmentBinding;
import pr.tongson.train_mvx.mvvm.ui.main.model.IpInfo;
import pr.tongson.train_mvx.mvvm.ui.main.model.IpInfoTask;
import pr.tongson.train_mvx.mvvm.ui.main.presenter.IpInfoContract;
import pr.tongson.train_mvx.mvvm.ui.main.presenter.IpInfoPresenter;

public class MainFragment extends Fragment implements IpInfoContract.View {

    private View rootView;
    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private IpInfoContract.Presenter mPresenter;
    private MainFragmentBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IpInfoTask ipInfoTask = IpInfoTask.getInstance();
        IpInfoPresenter infoPresenter = new IpInfoPresenter(this, ipInfoTask);
        setPresenter(infoPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mBinding == null) {
            //inflater.inflate(R.layout.main_fragment, container, false);
            mBinding = MainFragmentBinding.inflate(inflater, container, false);
        }
        if (rootView == null) {
            rootView = mBinding.getRoot();
            initView();
        }
        return rootView;
    }

    private void initView() {
        mBinding.btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getIpInfo("198.46.126.11");
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void setPresenter(IpInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setIpInfo(IpInfo ipInfo) {
        if (ipInfo != null) {
            mBinding.setIpdata(ipInfo);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), "网络出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

}
