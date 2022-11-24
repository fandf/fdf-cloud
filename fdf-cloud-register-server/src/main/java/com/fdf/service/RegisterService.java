package com.fdf.service;

import com.fdf.base.BaseService;
import com.fdf.container.RegisterContainer;
import com.fdf.entity.ServiceInstance;
import com.fdf.request.ReqInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author fandongfeng
 * @date 2022/11/24 09:58
 */
@RestController
@RequestMapping("/fdf")
public class RegisterService extends BaseService {

    @Resource
    RegisterContainer registerContainer;


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody ReqInfoDTO reqInfoDTO) {
        return setResult(registerContainer.put(reqInfoDTO));
    }

    @GetMapping("/activeService")
    public Map<String, HashSet<ServiceInstance>> activeService() {
        return registerContainer.getMapAddress();
    }

}
