package com.fdf.config.base;

import cn.hutool.json.JSONObject;
import com.fdf.config.constant.ConfigConstant;
import org.springframework.http.ResponseEntity;

/**
 * @author dongfengfan
 */
public class BaseService {
    public ResponseEntity setResultOk() {
        return setResultOk("ok");
    }

    public ResponseEntity setResult(boolean result) {
        return result ? setResultOk() :
                setResultFail();
    }

    public ResponseEntity setResultOk(String msg) {
        JSONObject result = new JSONObject();
        result.set("code", ConfigConstant.RESULT_OK);
        result.set("msg", msg);
        return ResponseEntity.status(ConfigConstant.RESULT_OK).body(result);
    }

    public ResponseEntity setResultOk(String msg, Object data) {
        JSONObject result = new JSONObject();
        result.set("code", ConfigConstant.RESULT_OK);
        result.set("msg", msg);
        if (data != null) {
            result.set("data", data);
        }
        return ResponseEntity.status(ConfigConstant.RESULT_OK).body(result);
    }

    public ResponseEntity setResultOk(String msg, Object data, Integer code) {
        JSONObject result = new JSONObject();
        result.set("code", code == null ? ConfigConstant.RESULT_OK : code);
        result.set("msg", msg);
        if (data != null) {
            result.set("data", data);
        }
        return ResponseEntity.status(ConfigConstant.RESULT_OK).body(result);
    }


    public ResponseEntity setResultFail() {
        return setResultFail("fail");
    }

    public ResponseEntity setResultFail(String msg) {
        JSONObject result = new JSONObject();
        result.put("code", ConfigConstant.RESULT_FAIL);
        result.put("msg", msg);
        return ResponseEntity.status(ConfigConstant.RESULT_FAIL).body(msg);
    }
}