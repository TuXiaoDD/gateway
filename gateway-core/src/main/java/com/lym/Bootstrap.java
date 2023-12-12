package com.lym;

import com.lym.config.BaseConfig;
import com.lym.config.ConfigLoader;

public class Bootstrap {

    public static void main(String[] args) {

        // 加载配置类
        BaseConfig baseConfig = ConfigLoader.getInstants().getBaseConfig();




    }
}