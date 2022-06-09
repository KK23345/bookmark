# bookmark



## 测试数据

```
对于书签/书签夹

{"title": "书签夹1",
	"ID": 1, "parentID": 0, "uid": 1, "type": 0, "url": "http://...",
	"isPublic": 1, "favorites": 0, "children": "[书签1]"}
	
{"title": "书签夹2",
	"ID": 2, "parentID": 0, "uid": 2, "type": 0, "url": "http://...",
	"isPublic": 1, "favorites": 0, "children": "[书签2, 书签3]"}

{"title": "书签1",
	"ID": 3, "parentID": 1, "uid": 1, "type": 1, "url": "http://...",
	"isPublic": 1, "favorites": 0, "children": ""}

{"title": "书签2",
	"ID": 4, "parentID": 2, "uid": 2, "type": 1, "url": "http://...",
	"isPublic": 1, "favorites": 0, "children": ""}
	
{"title": "书签3",
	"ID": 5, "parentID": 2, "uid": 2, "type": 1, "url": "http://...",
	"isPublic": 1, "favorites": 0, "children": ""}
```

![image-20220606170216706](README.assets/image-20220606170216706.png)

```sql
insert into book values(1,0,0,1,"书签夹1","http://...", "[{"title":"书签1","ID":3,"parentID":1,"uid":1,"type":1,"url":"http://...","isPublic":1,"favorites":0,"children":""}]", 1, 0);
insert into book values(2,0,0,2,"书签夹2","http://...", "[{"title":"书签2","ID":4,"parentID":2,"uid":2,"type":1,"url":"http://...","isPublic": 1, "favorites": 0, "children":""},{"title":"书签3","ID":5,"parentID":2,"uid":2,"type":1,"url":"http://...","isPublic": 1, "favorites":0,"children":""}]", 1, 0);
insert into book values(3,1,0,1,"书签1","http://...", "", 1, 0);
insert into book values(4,2,1,2,"书签2","http://...", "", 1, 0);
insert into book values(5,2,1,2,"书签3","http://...", "", 1, 0);
```

