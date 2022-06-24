package com.bookmark;

import com.alibaba.fastjson.JSONObject;
import com.bookmark.dao.BookmarkTreeDao;
import com.bookmark.pojo.BookmarkTree;
import com.bookmark.service.BookmarkTreeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;


@SpringBootTest
public class ControllerTest {
    @Resource
    BookmarkTreeDao btDao;

    @Resource
    BookmarkTreeService btService;

    @Test
    void testFrontAPI() { //测试前端的接口

        StringBuilder test = new StringBuilder();
        test.append("\"test").append("\"");
        System.out.println(test.toString());

        //System.out.println("copyBT res : " + btService.copyBT(3,7,4));

        //System.out.println(btService.createBT(1, 1, "test1"));

        //System.out.println(btService.deleteBT(3, 7));

        //BookmarkTree bt = btDao.getBookmarkTreeByID(19);
        //System.out.println(btService.publicBTOrNot(bt, 1));

        //System.out.println(btService.renameBT(2, 2,"lisi的书签夹"));
    }


    @Test
    void testObtainBT() {
        String res = btService.obtainBT(3); //"kk", rootID=7
        System.out.println(res);
    }

    @Test
    void testUploadBT() {
        String data = "{\n" +
                "  \"data\": {\n" +
                "    \"userName\": \"test03\",\n" +
                "    \"password\": \"12345678\",\n" +
                "    \"children\": [\n" +
                "      {\n" +
                "        \"title\": \"kk的收藏夹\",\n" +
                "        \"type\": \"0\",\n" +
                "        \"children\": [\n" +
                "          {\n" +
                "            \"title\": \"收藏夹栏\",\n" +
                "            \"type\": \"0\",\n" +
                "            \"children\": [\n" +
                "              {\n" +
                "                \"title\": \"核心介绍 - injected-script - 《Chrome插件(扩展)开发全攻略》 - 书栈网 · BookStack\",\n" +
                "                \"type\": \"1\",\n" +
                "                \"url\": \"https://www.bookstack.cn/read/chrome-plugin-develop/spilt.6.spilt.4.8bdb1aac68bbdc44.md\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"title\": \"Vite App\",\n" +
                "                \"type\": \"1\",\n" +
                "                \"url\": \"http://47.96.41.120:10028/login\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"title\": \"爱奇艺VIP会员限时优惠\",\n" +
                "                \"type\": \"1\",\n" +
                "                \"url\": \"https://vip.iqiyi.com/cps_pc.html?fv=eu_9cf981acdf1335b1&fr_version=euid%253D198386556\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"title\": \"爱淘宝PC新版\",\n" +
                "                \"type\": \"1\",\n" +
                "                \"url\": \"https://ai.taobao.com/?pid=mm_1183900030_1813100136_112219550255&union_lens=lensId%3APUB%401653623469%402104ee54_0a06_18103a3b432_0431%4001\"\n" +
                "              }\n" +
                "            ]\n" +
                "          },\n" +
                "          {\n" +
                "            \"title\": \"其他收藏夹\",\n" +
                "            \"type\": \"0\",\n" +
                "            \"children\": [\n" +
                "              {\n" +
                "                \"title\": \"哔哩哔哩 (゜-゜)つロ 干杯~-bilibili\",\n" +
                "                \"type\": \"1\",\n" +
                "                \"url\": \"https://www.bilibili.com/\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"title\": \"test01\",\n" +
                "                \"type\": \"0\",\n" +
                "                \"children\": [\n" +
                "                  {\n" +
                "                    \"title\": \"AcgFun资源网-动漫游戏下载 (=￣ω￣=)喵了个咪~\",\n" +
                "                    \"type\": \"1\",\n" +
                "                    \"url\": \"https://www.acgfun.net/\"\n" +
                "                  }\n" +
                "                ]\n" +
                "              },\n" +
                "              {\n" +
                "                \"title\": \"清单文件--扩展开发文档\",\n" +
                "                \"type\": \"1\",\n" +
                "                \"url\": \"https://open.chrome.360.cn/extension_dev/manifest.html#manifest_version\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"time\": 1654918236.067768\n" +
                "}";

        JSONObject jsonObject = JSONObject.parseObject(data);
        //System.out.println(jsonObject);
        jsonObject = jsonObject.getJSONObject("data");

        //测试upload
        System.out.println("cost:" + btService.uploadBT(jsonObject) + "ms");
        for(BookmarkTree bt : BookmarkTreeService.childList) {
            System.out.println(bt);
        }

        //测试fastjson
        /*System.out.println(jsonObject.getString("userName")); //kk
        System.out.println(jsonObject.getString("password")); //123456
        String children = jsonObject.getString("children"); //最外层children, 书签树的根
        System.out.println(children);
        JSONArray rootArray = JSONObject.parseArray(children);
        System.out.println(rootArray.size()); //最外层children解析成数组, 大小始终为1
        JSONObject childrenObject = rootArray.getJSONObject(0);
        //System.out.println(childrenObject);
        System.out.println(childrenObject.getString("title")); //""
        System.out.println(childrenObject.getString("type"));  //type = 0，继续解析下一层children

        children = childrenObject.getString("children");
        System.out.println(children);
        JSONArray jsonArray = JSONObject.parseArray(children); //第二层children，仍是数组
        System.out.println(jsonArray.size()); //2
        System.out.println(jsonArray.getJSONObject(0)); //当前json对象的type属性为0，继续解析
        System.out.println(jsonArray.getJSONObject(1));

        JSONObject child1 = jsonArray.getJSONObject(0);
        System.out.println(child1.getString("title")); //收藏夹栏
        System.out.println(child1.getString("type"));  //0
        children = child1.getString("children");
        jsonArray = JSONObject.parseArray(children);
        System.out.println(jsonArray.size());
        for(int i = 0; i < jsonArray.size(); i++) {
            System.out.println(jsonArray.getJSONObject(i));
            //System.out.println(jsonArray.getJSONObject(i).getString("type")); //如果有0，继续解析
        }*/


    }




}
