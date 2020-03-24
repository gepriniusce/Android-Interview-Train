package pr.tongson.train_mvx.mvp.presenter;

import pr.tongson.train_mvx.mvp.model.IpInfo;
import pr.tongson.train_mvx.mvp.view.BaseView;

/**
 * <b>Create Date:</b> 2019-12-29<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public interface IpInfoContract {

    interface Presenter {
        void getIpInfo(String ip);
    }

    interface View extends BaseView<Presenter> {
        void setIpInfo(IpInfo ipInfo);

        void showLoading();

        void hideLoading();

        void showError();

        boolean isActive();
    }

}
