package pr.tongson.train_okhttp.mine;


import okhttp3.Dispatcher;

/**
 * <b>Create Date:</b> 2020-03-04<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class OkHttpClient2 {
    Dispatcher2 dispatcher;
    int recount;

    public OkHttpClient2(Builder builder) {
        this.dispatcher = builder.dispatcher;
    }

    public static final class Builder {

        Dispatcher2 dispatcher;

        /**
         * 重複次數
         */
        int recount = 3;

        public Builder() {
            this.dispatcher = new Dispatcher2();
        }

        public Builder dispatcher(Dispatcher2 dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }

        public Builder setRecount(int recount) {
            this.recount = recount;
            return this;
        }

        public OkHttpClient2 build() {
            return new OkHttpClient2(this);
        }
    }

    public Call2 newCall(Request2 request) {
        return RealCall2.newRealCall(this, request, false);
    }

    public Dispatcher2 dispatcher() {
        return dispatcher;
    }

    public int getRecount() {
        return recount;
    }
}
