# datanucleus-rdbms

DataNucleus support for persistence to RDBMS Datastores. This plugin makes use of JDBC drivers for the datastores supported. 

This project is built using Maven, by executing `mvn clean install` which installs the built jar in your local Maven repository.


## KeyFacts

__License__ : Apache 2 licensed  
__Issue Tracker__ : http://github.com/datanucleus/datanucleus-rdbms/issues  
__Javadocs__ : [5.1](http://www.datanucleus.org/javadocs/store.rdbms/5.1/), [5.0](http://www.datanucleus.org/javadocs/store.rdbms/5.0/), [4.1](http://www.datanucleus.org/javadocs/store.rdbms/4.1/), [4.0](http://www.datanucleus.org/javadocs/store.rdbms/4.0/)  
__Download(Releases)__ : [Maven Central](http://central.maven.org/maven2/org/datanucleus/datanucleus-rdbms)  
__Download(Nightly)__ : [Nightly Builds](http://www.datanucleus.org/downloads/maven2-nightly/org/datanucleus/datanucleus-rdbms)  
__Dependencies__ : See file [pom.xml](pom.xml)  
__Support__ : [DataNucleus Support Page](http://www.datanucleus.org/support.html)  


## Datastore Adapters

Each supported datastore will have an associated "adapter" stored under 
[org.datanucleus.store.rdbms.adapter](https://github.com/datanucleus/datanucleus-rdbms/tree/master/src/main/java/org/datanucleus/store/rdbms/adapter), 
so if planning on supporting or improving support for an RDBMS database this is the place to look (as well as in 
[plugin.xml](https://github.com/datanucleus/datanucleus-rdbms/blob/master/src/main/resources/plugin.xml)).


## Mappings

DataNucleus RDBMS maps fields to columns using a _mapping_. Each Java type has a JavaTypeMapping variant, under
[org.datanucleus.store.rdbms.mapping.java](https://github.com/datanucleus/datanucleus-rdbms/tree/master/src/main/java/org/datanucleus/store/rdbms/mapping/java)
and each of these has 1+ DatastoreMapping (which map approximately onto JDBC types), under
[org.datanucleus.store.rdbms.mapping.datastore](https://github.com/datanucleus/datanucleus-rdbms/tree/master/src/main/java/org/datanucleus/store/rdbms/mapping/datastore).

Later DataNucleus releases are making less use of these mappings and more use of the internal _TypeConverter_ mechanism, so that we now only have mappings for
some of the more basic types or where complicated handling is required, with remaining types using TypeConverterMapping.


## SQL Generation

All SQL generated by datanucleus-rdbms uses an SQL API, located under 
[org.datanucleus.store.rdbms.sql](https://github.com/datanucleus/datanucleus-rdbms/tree/master/src/main/java/org/datanucleus/store/rdbms/sql).
There you have classes representing SELECT, UPDATE, DELETE and INSERT statements, providing an API for building the SQL.

### Table Groups

A __table group__ is a group of tables in the SQL statement. The SQL statement will be composed of 1 or more table groups.
A table group equates to an object in an object-based query language.
For example the candidate object will be in the first table group. When a relation is navigated the related object will be in its table group. 
And so on. All of the way down an inheritance tree will use the same table group; by that we mean that if you have a class Person and 
class Employee which extends Person and they have their own tables in the datastore, then when referring to the candidate object of type Employee, 
the tables PERSON and EMPLOYEE will be in the same table group.


### Table Naming

With the SQLStatement API a developer can define the aliases of tables in the SQL statement. If they don't define an alias then the aliases will 
be generated for them using a DataNucleus extension. The plugin-point `org.datanucleus.store.rdbms.sql_tablenamer` defines an interface
to be implemented by plugins for naming of tables. The default option is __alpha-scheme__.

__alpha-scheme__ will name tables based on the table group they are in and the number of the table within that group. So you will get table 
aliases like A0, A1, A2, B0, B1, C0, D0. In this case we have a candidate object in the query with 3 tables (A0, A1, A2) and relations to 
an object with 2 tables (B0, B1) and these have relations to other objects with a single table (C0), (D0).

__t-scheme__ will name tables based on the table number in the statement as a whole and doesn't use the table group for anything. So you get 
table aliases like T0, T1, T2, T3, T4, T5, etc.

__table-name__ will use the table name instead of an alias. Clearly this will not work if you have joins to the same table name, but is useful
in situations where, for example, the RDBMS doesn't support aliases in an UPDATE/DELETE statement.


To define which namer plugin you want to use, set the extension __table-naming-strategy__ on the SQLStatement and by the persistence
property __datanucleus.rdbms.sqlTableNamingStrategy__ at construction of the PMF/EMF.
