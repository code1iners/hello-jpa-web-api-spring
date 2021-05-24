# JPA Shop

# What is this?
### This is a simple Spring boot web & api application with JPA (Review).

# Libraries
## 1. Frontend
	* Thymeleaf.
	* Bootstrap.

## 2. Backend
	* Spring (MVC).
	* JPA with Hibernate.
    * Connection Pool (HikariCP).
	* H2 Database.
	* p6spy.

## 3. Other
    * SLF4J with LogBack.

# Build and Deploy.

## 1. Build project.
```
Path : /jpashop-review/ (root)
```

### Command
```
./gradlew builc

or

./gradlew clean build
```


## 2. Deploy project.
```
Path : /jpashop-review/build/libs
```

### Command
```
java -jar *.jar
```


## Requirements.

### Features.

1. Member
    * Save.
    * Find.
2. Item
    * Save.
    * Update.
    * Find.
3. Order
    * Order item.
    * Order history inquiry.
    * Order cancel.
4. Others
    * Manage item stock.
    * There is item category Album, Book, Movie.
    * Search item by item category.
    * Able to enter delivery information when order item.
    * Restful API service.