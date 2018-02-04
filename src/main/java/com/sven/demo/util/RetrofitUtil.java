package com.sven.demo.util;

import com.sven.demo.module.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 获取Retrofit调用API返回结果工具类
 * @author: sven
 * @date: 04/02/2018
 */
@Component
public class RetrofitUtil<T> {

    public static final int CODE = 200;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public T fetch(Call<T> call) {
        return fetch(call, null);
    }

    public T fetch(Call<T> call, T defaultValue) {
        T result = defaultValue;
        try {
            Response<T> respose = call.execute();
            result = respose.body();
        } catch (Exception e) {
            logger.error("fail", e);
            e.printStackTrace();
        }
        return result;
    }

    public T fetchApiResult(Call<ApiResult<T>> call) {
        return fetchApiResult(call, CODE, null);
    }

    public T fetchApiResult(Call<ApiResult<T>> call, int code) {
        return fetchApiResult(call, code, null);
    }

    public T fetchApiResult(Call<ApiResult<T>> call, int code, T defaultValue) {
        T result = defaultValue;
        try {
            Response<ApiResult<T>> respose = call.execute();
            ApiResult<T> apiResult = respose.body();
            result = apiResult != null && apiResult.getCode() == code? apiResult.getData(): defaultValue;
        } catch (Exception e) {
            logger.error("fail", e);
            e.printStackTrace();
        }
        return result;
    }
}
