package priv.zhou.params;


public interface CONSTANT {

    /***********************************************************  REDIS KEYS  ***********************************************************/

    String REDIS_PREFIX = "BLOG_REDIS_PREFIX";

    String BLOG_PV_KEY = REDIS_PREFIX + "BLOG_PV_KEY";

    String MENU_KEY = REDIS_PREFIX + "MENU_KEY";

    String BLOG_KEY = REDIS_PREFIX + "BLOG_KEY_";

    String BLOG_TYPE_KEY = REDIS_PREFIX + "BLOG_TYPE_KEY_";

    String DICT_DATA_KEY = REDIS_PREFIX +"DICT_DATA_KEY_";


}
