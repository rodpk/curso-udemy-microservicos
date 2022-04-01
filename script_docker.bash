-- criando containers postgres

docker run --name auth-db -p 5432:5432 -e POSTGRES_DB=auth-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123456 postgres:11

docker run --name product-db -p 5433:5432 -e POSTGRES_DB=product-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123456 postgres:11


docker container stop auth-db // para o container
dcoker container prune -- remove todos containers parados da memoria


--mongo db
docker run --name sales-db -d -p 27017:27017 -p 28017:28017 -e MONGODB_USER="admin" -e MONGODB_DATABASE="sales" -e MONGODB_PASS="123456" tutum/mongodb

mongodb://admin:123456@locahost:27017/sales


db.sales
sales.sales
show collections
db.sales.insert({})
db.sales.find()


-- rabbit mq

docker run --name sales_rabbit -p 5672:5672 -p 25676:25676 -p 15672:15672 rabbitmq:3-management

-- 15672 p acessar no navegador
guest guest

-- docker-compose up --build 
-d pra nao ver o log