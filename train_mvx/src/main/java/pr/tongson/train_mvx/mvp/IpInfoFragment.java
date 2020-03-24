package pr.tongson.train_mvx.mvp;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import pr.tongson.train_mvx.R;
import pr.tongson.train_mvx.mvp.model.IpInfo;
import pr.tongson.train_mvx.mvp.presenter.IpInfoContract;
import pr.tongson.train_mvx.utils.GsonUtils;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IpInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IpInfoFragment extends Fragment implements IpInfoContract.View {


    private TextView mText;


    public IpInfoFragment() {
        // Required empty public constructor
    }

    public static IpInfoFragment newInstance() {
        IpInfoFragment fragment = new IpInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    private View rootView;

    private Dialog mDialog;
    private IpInfoContract.Presenter mPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_ip_info, container, false);
            initView();
        }
        return rootView;
    }

    private void initView() {
        mText = (TextView) rootView.findViewById(R.id.text);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDialog = new ProgressDialog(getActivity());
        mDialog.setTitle("获取数据中");
    }


    @Override
    public void setPresenter(IpInfoContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.getIpInfo("103.27.25.105");
    }

    @Override
    public void setIpInfo(IpInfo ipInfo) {
        if (ipInfo != null) {
            //            IpInfo.ResultBean ipData = ipInfo.getResult();
            //            mText.setText(ipData.getAd_info().getCity());
            mText.setText(GsonUtils.GsonString(ipInfo));
        }
    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
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
