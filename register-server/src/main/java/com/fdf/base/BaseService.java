package com.fdf.base;

import com.alibaba.fastjson2.JSONObject;
import com.fdf.constant.RegisterConstant;
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
        result.put("code", RegisterConstant.RESULT_OK);
        result.put("msg", msg);
        return ResponseEntity.status(RegisterConstant.RESULT_OK).body(result);
    }

    public ResponseEntity setResultFail() {
        return setResultFail("fail");
    }

    public ResponseEntity setResultFail(String msg) {
        JSONObject result = new JSONObject();
        result.put("code", RegisterConstant.RESULT_FAIL);
        result.put("msg", msg);
        return ResponseEntity.status(RegisterConstant.RESULT_FAIL).body(msg);
    }
}