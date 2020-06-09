package priv.zhou.params;


public interface CONSTANT {


    String DEFAULT_FILL = "0";


    /***********************************************************  REDIS KEYS  ***********************************************************/

    String REDIS_PREFIX = "BLOG_REDIS_PREFIX";


    //  独立key
    String BLOG_PV_KEY = REDIS_PREFIX + "BLOG_PV_KEY";

    String MENU_KEY = REDIS_PREFIX + "MENU_KEY";

    String ACCESS_BLOCK_KEY = REDIS_PREFIX + "ACCESS_BLOCK_KEY";


    //  需要拼接
    String REPEAT_KEY = REDIS_PREFIX + "CHECK_REPEAT_KEY_";

    String BLOG_KEY = REDIS_PREFIX + "BLOG_KEY_";

    String BLOG_TYPE_KEY = REDIS_PREFIX + "BLOG_TYPE_KEY_";

    String DICT_DATA_KEY = REDIS_PREFIX + "DICT_DATA_KEY_";

    String VISITOR_KEY = REDIS_PREFIX + "VISITOR_KEY_";


}
