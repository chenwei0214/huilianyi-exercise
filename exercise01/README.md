## 接口说明

### 用户接口

1. 用户认证
````
curl -X POST \
  http://localhost:8080/oauth/token \
  -H 'Authorization: Basic Y2xpZW50XzI6MTIzNDU2' \
  -H 'Cache-Control: no-cache' \
  -F username=1001 \
  -F password=111111 \
  -F grant_type=password \
  -F scope=select
````
2. 删除用户
````
curl -X DELETE \
  http://localhost:8080/user/del/5 \
  -H 'Authorization: Bearer ff4cc574-7e82-4187-a4ec-f2883cce8f88' \
  -H 'Cache-Control: no-cache'
````

3. 注册用户
````
curl -X PUT \
  'http://localhost:8080/user/register?departId=2' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -d '{
	"username":"wangwu",
	"password":"111111",
	"employeeId":"1003",
	"email":"abcd@qq.com",
	"phone":"15825923412"
}'
````

4. 搜索用户
````
curl -X GET \
  'http://localhost:8080/user/search_lu?keyword=' \
  -H 'Authorization: Bearer ff4cc574-7e82-4187-a4ec-f2883cce8f88' \
  -H 'Cache-Control: no-cache'
````

5. 更新用户
````
curl -X POST \
  'http://localhost:8080/user/update?id=1' \
  -H 'Authorization: Bearer a1f902cf-be70-4a62-9696-9639ade8545c' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -d '{
	"email":"hello@qq.com"
}'
````

6. 初始化索引库
````
可以在application.yml文件中指定lucene存放路径
curl -X GET \
  http://localhost:8080/user/index \
  -H 'Authorization: Bearer a1f902cf-be70-4a62-9696-9639ade8545c' \
  -H 'Cache-Control: no-cache'
````

### 部门接口

1. 添加部门
````
curl -X PUT \
  'http://localhost:8080/depart/add?parentId=2' \
  -H 'Authorization: Bearer a1f902cf-be70-4a62-9696-9639ade8545c' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json'
  -d '{
	"departmentName":"abcd",
	"departmentCode":"1004"
}'
````

2. 删除部门
````
curl -X DELETE \
  http://localhost:8080/depart/del/10000 \
  -H 'Authorization: Bearer a1f902cf-be70-4a62-9696-9639ade8545c' \
  -H 'Cache-Control: no-cache'
````

3. 查看部门结构
````
curl -X GET \
  http://localhost:8080/depart/tree \
  -H 'Authorization: Bearer a1f902cf-be70-4a62-9696-9639ade8545c' \
  -H 'Cache-Control: no-cache'
````

4. 更新部门
````
curl -X POST \
  http://localhost:8080/depart/update/1 \
  -H 'Authorization: Bearer a1f902cf-be70-4a62-9696-9639ade8545c' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -d '{
	"departmentName":"abcd",
	"departmentCode":"1001"
}'
````

5. 获取部门人数
````
curl -X GET \
  http://localhost:8080/depart/num/1 \
  -H 'Authorization: Bearer a1f902cf-be70-4a62-9696-9639ade8545c' \
  -H 'Cache-Control: no-cache'
````