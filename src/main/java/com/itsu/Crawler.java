package com.itsu;

import cn.hutool.core.collection.ListUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Crawler {

    private static final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

    static {
        Cookie c1 = new Cookie.Builder().domain("qcc.com").name("QCCSESSID").value("hctl26v7ol2v6t6raig3mdks74").build();
        Cookie c2 = new Cookie.Builder().domain("www.qcc.com").name("qcc_did").value("5d262da2-d2fe-49ee-afe2-85b2c62e0561").build();
        Cookie c3 = new Cookie.Builder().domain("qcc.com").name("MQCCSESSID").value("smokpdqpl84mh379jl02l1k6v6").build();
        Cookie c4 = new Cookie.Builder().domain("www.qcc.com").name("acw_tc").value("0ed717d316366446377566331e50354b10d0cb4ec5fad6c0320005c32f").build();
        Cookie c5 = new Cookie.Builder().domain("qcc.com").name("zg_did").value("%7B%22did%22%3A%20%2217d0f2d0712859-00b06cef9d5964-1f3e6650-1fa400-17d0f2d0713d4e%22%7D").build();
        Cookie c6 = new Cookie.Builder().domain("qcc.com").name("zg_294c2ba1ecc244809c552f8f6fd2a440").value("%7B%22sid%22%3A%201636637148950%2C%22updated%22%3A%201636645911480%2C%22info%22%3A%201636637148953%2C%22superProperty%22%3A%20%22%7B%5C%22%E5%BA%94%E7%94%A8%E5%90%8D%E7%A7%B0%5C%22%3A%20%5C%22%E4%BC%81%E6%9F%A5%E6%9F%A5%E7%BD%91%E7%AB%99%5C%22%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%7D%22%2C%22referrerDomain%22%3A%20%22%22%2C%22cuid%22%3A%20%22undefined%22%7D").build();
//        cookieStore.put("https://www.qcc.com/", ListUtil.of(c1, c2, c3, c4, c5));
        cookieStore.put(HttpUrl.parse("https://www.qcc.com/"), ListUtil.of(c1, c2, c4));
    }

    public static void main(String[] args) throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        System.err.println("httpurl:" + httpUrl);
                        cookieStore.put(httpUrl, list);
                        for (Cookie cookie : list) {
//                            cookie.toString()
                            System.err.println(cookie);
//                            System.err.println("cookie Path:" + cookie.path());
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
//                        List<Cookie> cookies = cookieStore.get(httpUrl);
//
//                        for (Cookie cookie : cookies) {
//                            System.err.println(cookie);
//                        }
                        List<Cookie> cookies = cookieStore.get(HttpUrl.parse("https://www.qcc.com/"));
                        return cookies != null ? cookies : new ArrayList<>();
                    }
                }).build();

        Request request = new Request.Builder()
                .url("https://www.qcc.com/")
                .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.54 Safari/537.36")
                .get().build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        System.out.println(response.body().string());
        System.err.println("-------------------------------------------------------------");
        request = new Request.Builder()
//                .url("https://www.qcc.com/firm/f058cc57ceb332c77f46fdd9dc76c772.html")
//                .url("https://www.qcc.com/firm/a9a529109b9ff6650f20ad54d1e193c3.html")
//                .url("https://www.qcc.com/api/datalist/touzilist?keyNo=f058cc57ceb332c77f46fdd9dc76c772&pageIndex=2")
                .url("https://www.qcc.com/api/datalist/touzilist?keyNo=f058cc57ceb332c77f46fdd9dc76c772&pageIndex=3")
                .url("https://www.qcc.com/web/search?key=https://www.qcc.com/web/search?key=深圳市引导基金投资有限公司")
//                .url("https://report.qichacha.com/ReportEngine/20211111132811173677971841_65602853/%E6%B7%B1%E5%9C%B3%E5%B8%82%E5%BC%95%E5%AF%BC%E5%9F%BA%E9%87%91%E6%8A%95%E8%B5%84%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8.xls")
                .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.54 Safari/537.36")
//                .addHeader("e5022d937a426642ea10", "c9b2643b2d2e89d0d7348814b03e4ef92cb93334d188177c4919a1aa9de2a47245801b0347c729c7c4d0a9bf95194a7b8201b14f1e38a4c2d60a222464a67fac")
                .get().build();
        call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.err.println("爬虫异常");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String contents = response.body().string();
                System.out.println(contents);
//                HtmlCleaner hc = new HtmlCleaner();
//                TagNode tn = hc.clean(contents);
//                String xpath = "/html/body/div[1]/div[2]/div[2]/div[3]/div/div[2]/div/table/tr[1]/td[3]/div/a[1]";
//                Object[] objects = new Object[0];
//                try {
//                    objects = tn.evaluateXPath(xpath);
//                } catch (XPatherException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(objects);
//                byte[] bytes = response.body().bytes();
//                FileUtil.mkdir("/Users/suben/Desktop/crawler");
//                Files.write(Paths.get("/Users/suben/Desktop/crawler/file.xlsx"), bytes, StandardOpenOption.CREATE);
                String[] split = contents.split("<a target=\"_blank\" href=\"https://www.qcc.com/firm/");
                System.err.println(split[1].substring(0, 37));
            }
        });
    }
}
