package com.fdf.service;

import com.fdf.container.RegisterContainer;
import com.fdf.entity.ReqInfoEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/11/24 09:58
 */
@RestController
@RequestMapping("/fdf")
public class RegisterService {

    @Resource
    RegisterContainer registerContainer;


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody ReqInfoEntity reqInfo) {
        boolean result = registerContainer.put(reqInfo);
        return result ? ResponseEntity.status(200).body("ok") : ResponseEntity.status(200).body("fail");
    }

    @GetMapping("/activeService")
    public Map<String, List<String>> activeService() {
        return registerContainer.getMapAddress();
    }

}
