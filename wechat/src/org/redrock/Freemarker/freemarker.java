package org.redrock.Freemarker;

import freemarker.template.*;
import java.util.*;
import java.io.*;

public class freemarker {

    public static void main(String[] args) throws Exception {

        /* ------------------------------------------------------------------------ */
        /* You should do this ONLY ONCE in the whole application life-cycle:        */

        /* Create and adjust the configuration singleton */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(new File("/where/you/store/templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        /* ------------------------------------------------------------------------ */
        /* You usually do these for MULTIPLE TIMES in the application life-cycle:   */

        /* Create a data-model */
//        Map root = new HashMap();
//        root.put("user", "Big Joe");
//        Map latest = new HashMap();
//        root.put("latestProduct", latest);
//        latest.put("url", "products/greenmouse.html");
//        latest.put("name", "green mouse");

        Map<String, Object> root = new HashMap<>();
        Map<String, Object> latestProduct = new HashMap<>();
        class Product {
            private String url;

            // As per the JavaBeans spec., this defines the "url" bean property
            public String getUrl() {
                return url;
            }
        }
        //Product latestProducts =  getLatestProductFromDatabaseOrSomething();
        root.put("latestProduct", latestProduct);

        /* Get the template (uses cache internally) */
        Template temp = cfg.getTemplate("freemarker.ftl");

        /* Merge data-model with template */
        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);
        // Note: Depending on what `out` is, you may need to call `out.close()`.
        // This is usually the case for file output, but not for servlet output.
    }


}