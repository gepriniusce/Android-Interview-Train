package pr.tongson.train_mvx.mvvm.ui.main.model;

import java.io.Serializable;

/**
 * <b>Create Date:</b> 2019-12-29<br>
 * <b>Email:</b> 289286298@qq.com<br>
 * <b>Description:</b>  <br>
 *
 * @author tongson
 */
public class IpInfo implements Serializable {

    /**
     * status : 0
     * message : query ok
     * result : {"ip":"61.135.17.68","location":{"lat":39.90469,"lng":116.40717},"ad_info":{"nation":"中国","province":"北京市","city":"北京市","district":"","adcode":110000}}
     */

    private int status;
    private String message;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * ip : 61.135.17.68
         * location : {"lat":39.90469,"lng":116.40717}
         * ad_info : {"nation":"中国","province":"北京市","city":"北京市","district":"","adcode":110000}
         */

        private String ip;
        private LocationBean location;
        private AdInfoBean ad_info;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public AdInfoBean getAd_info() {
            return ad_info;
        }

        public void setAd_info(AdInfoBean ad_info) {
            this.ad_info = ad_info;
        }

        public static class LocationBean {
            /**
             * lat : 39.90469
             * lng : 116.40717
             */

            private double lat;
            private double lng;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }

        public static class AdInfoBean {
            /**
             * nation : 中国
             * province : 北京市
             * city : 北京市
             * district :
             * adcode : 110000
             */

            private String nation;
            private String province;
            private String city;
            private String district;
            private int adcode;

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public int getAdcode() {
                return adcode;
            }

            public void setAdcode(int adcode) {
                this.adcode = adcode;
            }
        }
    }
}
