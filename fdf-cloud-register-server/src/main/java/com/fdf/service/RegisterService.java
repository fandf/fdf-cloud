package com.fdf.service;

import com.fdf.base.BaseService;
import com.fdf.container.RegisterContainer;
import com.fdf.entity.ServiceInstance;
import com.fdf.request.ReqInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/11/24 09:58
 */
@RestController
@RequestMapping("/fdf")
public class RegisterService extends BaseService {

    @Resource
    RegisterContainer registerContainer;

    /**
     * 注册
     *
     * @param reqInfoDTO
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody ReqInfoDTO reqInfoDTO) {
        return setResult(registerContainer.put(reqInfoDTO));
    }

    /**
     * 查询活跃的服务
     *
     * @return
     */
    @GetMapping("/activeService")
    public Map<String, HashSet<ServiceInstance>> activeService() {
        return registerContainer.getMapAddress();
    }

    /**
     * 服务续约接口
     *
     * @return
     */
    @PostMapping("/renewal")
    public ResponseEntity renewal(@RequestBody ReqInfoDTO dto) {
        return setResult(registerContainer.renewal(dto));
    }


    /**
     * 正常宕机调用剔除服务接口
     *
     * @param reqInfoDTO
     * @return
     */
    @PostMapping("/remove")
    public ResponseEntity unRegister(@RequestBody ReqInfoDTO reqInfoDTO) {
        return setResult(registerContainer.remove(reqInfoDTO) != null);
    }

}
