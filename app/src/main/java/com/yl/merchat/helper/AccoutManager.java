package com.yl.merchat.helper;

/**
 * 用户管理器
 * <p>
 * Created by zm on 2018/9/10.
 */
public class AccoutManager {
    private AccoutManager() {}

    private static final class AccoutManagerHolder {
        public static final AccoutManager INSTANCE = new AccoutManager();
    }

    public static AccoutManager getInstance() {
        return AccoutManagerHolder.INSTANCE;
    }
}
