package com.thoughtworks.herobook.gateway.clients;

import com.thoughtworks.herobook.auth.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "authservice")
public interface UserApiClient extends UserApi {

}
