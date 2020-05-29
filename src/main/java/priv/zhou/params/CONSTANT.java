package priv.zhou.params;


public interface CONSTANT {


    String DEFAULT_FILL = "0";

    String VISIT_KEY = "VISIT_KEY";


    /***********************************************************  REDIS KEYS  ***********************************************************/

    String REDIS_PREFIX = "REDIS_PREFIX";

    String BLOG_KEY = REDIS_PREFIX + "BLOG_KEY";

    String BLOG_TYPE_KEY = REDIS_PREFIX + "BLOG_TYPE_KEY";

    String BLOG_PV_KEY = REDIS_PREFIX + "BLOG_PV_KEY";


    String MENU_KEY = REDIS_PREFIX + "MENU_KEY";


}
