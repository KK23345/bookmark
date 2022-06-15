[测试数据](./bookmark.sql)

```java
//书签树相关接口

/**
* Description: 重命名书签(夹)
* @param bt: 要操作的书签(夹)
* @param newName: 新名字
* @return:  Map<String, String> response:
*           k-v:  "msg"       :  操作成功 / 权限不够 / 操作失败
*                 "errorCode" :  1 / -1 / -2
*                 "httpCode"  :  200
*/
@GetMapping("/renameBT/{newName}")
public @ResponseBody Object renameBT(@RequestBody BookmarkTree bt, @PathVariable("newName") String newName) 

/**
* Description: 公开或私密 某个书签夹(其子书签夹也一致)
* @param bt: 要操作的书签树
* @param isPublic: isPublic == 0 ? 私密 : 公开(1)
* @return:  Map<String, String> response:
*           k-v:  "msg"       :  操作成功 / 权限不够 / 操作失败
*                 "errorCode" :  1 / -1 / -2
*                 "httpCode"  :  200
*/
@GetMapping("/publicBTOrNot/{isPublic}")
public @ResponseBody Object publicBTOrNot(@RequestBody BookmarkTree bt, @PathVariable("isPublic") Integer isPublic) 

/**
* Description:  删除某个书签夹(其子书签(夹)也一致)
* @param bt: 要操作的书签树
* @return:  Map<String, String> response:
*           k-v:  "msg"       :  操作成功 / 权限不够 / 操作失败
*                 "errorCode" :  1 / -1 / -2
*                 "httpCode"  :  200
*/
@GetMapping("/deleteBT")
public @ResponseBody Object deleteBT(@RequestBody BookmarkTree bt) 

/**
* Description:  在某个书签夹下创建书签夹
* @param parentBT: 要操作的书签树
* @param btName: 名称               
* @return:  Map<String, String> response:
*           k-v:  "msg"       :  操作成功 / 操作失败
*                 "errorCode" :  1 / -1 
*                 "httpCode"  :  200
*/
@GetMapping("/createBT/{btName}")
public @ResponseBody Object createBT(@RequestBody BookmarkTree parentBT, @PathVariable("btName") String btName) 

/**
* Description:  复制某个书签夹(其子书签(夹)也一致)到当前用户(uid)根书签夹下
* @param bt: 要操作的书签树
* @return:  Map<String, String> response:
*           k-v:  "msg"       :  操作成功 / 权限不够 / 操作失败
*                 "errorCode" :  1 / -1 / -2
*                 "httpCode"  :  200
*/
@GetMapping("/copyBT/{uid}")
public @ResponseBody Object copyBT(@RequestBody BookmarkTree bt, @PathVariable("uid") Integer uid) 

/**
* Description:  上传书签树
* @param data:  json 格式书签树
* @return:  Map<String, String> response:
*           k-v:  "msg"       :  操作成功 / 操作失败
*                 "errorCode" :  1 / -1
*                 "httpCode"  :  200
*                 "data"      :  上传时间(操作成功才有该属性)
*/
@GetMapping("uploadBT")
public @ResponseBody Object uploadBT(@RequestBody String data) 

/**
* Description: 获取当前用户所有的书签夹信息
* @param uid:  当前用户
* @return:  Map<String, String> response:
*           k-v:  "msg"       :  操作成功 / 操作失败
*                 "errorCode" :  1 / -1
*                 "httpCode"  :  200
*                 "data"      :  json格式的书签树字符串(操作成功才有该属性)
*/
@GetMapping("obtainBT/{uid}")
public @ResponseBody Object obtainBT(@PathVariable("uid") Integer uid) 

```

