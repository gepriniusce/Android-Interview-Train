package pr.tongson.train_okhttp.mine;

import java.util.HashMap;
import java.util.Map;


/**
 * <b>Create Date:</b> 2020-03-04<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class Request2 {

    private static final String GET = "GET";
    private static final String POST = "POST";
    private Map<String, String> mHeaderList = new HashMap<>();

    private String url;
    private String requestMethod = GET;

    private RequestBody2 mRequestBody2;


    public Request2() {
        this(new Builder());
    }

    public Request2(Builder builder) {
        this.url = builder.url;
        this.mHeaderList = builder.mHeaderList;
        this.requestMethod = builder.requestMethod;
        this.mRequestBody2 = builder.requestBody2;
    }

    public Map<String, String> getHeaderList() {
        return mHeaderList;
    }

    public String getUrl() {
        return url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public RequestBody2 getRequestBody2() {
        return mRequestBody2;
    }

    public static class Builder {
        private String url;
        private String requestMethod = GET;
        private Map<String, String> mHeaderList = new HashMap<>();
        private RequestBody2 requestBody2;


        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder get() {
            this.requestMethod = GET;
            return this;
        }

        public Builder post(RequestBody2 requestBody2) {
            this.requestMethod = POST;
            this.requestBody2 = requestBody2;
            return this;
        }

        public Builder addRequestHeader(String key, String value) {
            mHeaderList.put(key, value);
            return this;
        }


        public Request2 build() {
            if (url == null) {
                throw new IllegalStateException("url == null");
            }
            return new Request2(this);
        }
    }
}
