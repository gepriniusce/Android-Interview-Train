package pr.tongson.train_okhttp.demo;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Create Date:</b> 2020-03-16<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class Bean implements Serializable {


    /**
     * translation : ["车"]
     * basic : {"us-phonetic":"kɑːr","phonetic":"kɑː(r)","uk-phonetic":"kɑː(r)","explains":["n. 汽车；车厢","n. (Car)人名；(土)贾尔；(法、西)卡尔；(塞)察尔"]}
     * query : car
     * errorCode : 0
     * web : [{"value":["汽车","小汽车","嵌合抗原受体"],"key":"Car"},{"value":["紧凑型轿车","小型汽车","小型车"],"key":"compact car"},{"value":["车身","轿车车身","车体"],"key":"Car body"}]
     */

    private BasicBean basic;
    private String query;
    private int errorCode;
    private List<String> translation;
    private List<WebBean> web;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public List<WebBean> getWeb() {
        return web;
    }

    public void setWeb(List<WebBean> web) {
        this.web = web;
    }

    public static class BasicBean implements Serializable {
        /**
         * us-phonetic : kɑːr
         * phonetic : kɑː(r)
         * uk-phonetic : kɑː(r)
         * explains : ["n. 汽车；车厢","n. (Car)人名；(土)贾尔；(法、西)卡尔；(塞)察尔"]
         */

        @SerializedName("us-phonetic")
        private String usphonetic;
        private String phonetic;
        @SerializedName("uk-phonetic")
        private String ukphonetic;
        private List<String> explains;

        public String getUsphonetic() {
            return usphonetic;
        }

        public void setUsphonetic(String usphonetic) {
            this.usphonetic = usphonetic;
        }

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public String getUkphonetic() {
            return ukphonetic;
        }

        public void setUkphonetic(String ukphonetic) {
            this.ukphonetic = ukphonetic;
        }

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }
    }

    public static class WebBean implements Serializable {
        /**
         * value : ["汽车","小汽车","嵌合抗原受体"]
         * key : Car
         */

        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
