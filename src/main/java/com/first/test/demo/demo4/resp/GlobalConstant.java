package com.first.test.demo.demo4.resp;

/**
 * @author chris
 */
public interface GlobalConstant {

    enum RestResponseEnum {
        /**
         * 错误类型
         */
        NETWORK_ERROR(-1000, "网络异常"),
        PARAMS_ERROR(-1001, "参数错误"),
        DATA_ERROR(-1002, "数据异常");

        private Integer code;
        private String desc;

        RestResponseEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

    enum IsNewestEnum {
        /**
         * 是否是最新版本
         */
        OLD_VERSION(0, "老版本"),
        NEW_VERSION(1, "新版本");
        private Integer code;
        private String desc;

        IsNewestEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
}
