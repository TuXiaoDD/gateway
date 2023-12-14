package com.lym;

import com.lym.config.BaseConfig;
import com.lym.config.ConfigLoader;
import com.lym.container.NettyContainer;

public class Bootstrap {

    public static void main(String[] args) {

        // 加载配置类
        BaseConfig baseConfig = ConfigLoader.getInstants().getBaseConfig();


        NettyContainer container = new NettyContainer(baseConfig);
        container.start();

    }
}