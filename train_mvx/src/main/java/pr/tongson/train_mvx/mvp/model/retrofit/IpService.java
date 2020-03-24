package pr.tongson.train_mvx.mvp.model.retrofit;


import pr.tongson.train_mvx.mvp.model.IpInfo;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * <b>Create Date:</b> 2019-12-29<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public interface IpService {
    //http://ip.taobao.com/service/getIpInfo.php?ip=103.27.25.105
    //@GET("getIpInfo.php?ip=103.27.25.105")
    @GET("ip?ip=103.27.25.105&key=EGCBZ-GV7KF-IOEJZ-NUENQ-KUW4H-R7FNE")
    Call<IpInfo> getIpMsg();
}
