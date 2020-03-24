package pr.tongson.train_mvx.mvvm.ui.main.presenter;


import pr.tongson.train_mvx.mvvm.ui.main.model.IpInfo;
import pr.tongson.train_mvx.mvvm.ui.main.model.LoadTasksCallback;
import pr.tongson.train_mvx.mvvm.ui.main.model.NetTask;

/**
 * <b>Create Date:</b> 2019-12-29<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class IpInfoPresenter implements IpInfoContract.Presenter, LoadTasksCallback<IpInfo> {

    private NetTask netTask;

    private IpInfoContract.View addTaskView;

    public IpInfoPresenter(IpInfoContract.View addTaskView, NetTask netTask) {
        this.netTask = netTask;
        this.addTaskView = addTaskView;
    }


    @Override
    public void getIpInfo(String ip) {
        netTask.execute(ip, this);
    }

    @Override
    public void onSuccess(IpInfo ipInfo) {
        if (addTaskView.isActive()) {
            addTaskView.setIpInfo(ipInfo);
        }
    }

    @Override
    public void onStart() {
        if (addTaskView.isActive()) {
            addTaskView.showLoading();
        }
    }

    @Override
    public void onFailed() {
        if (addTaskView.isActive()) {
            addTaskView.showError();
            addTaskView.hideLoading();
        }
    }

    @Override
    public void onFinish() {
        if (addTaskView.isActive()) {
            addTaskView.hideLoading();
        }
    }
}
