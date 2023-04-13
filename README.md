
# java-explore-with-me
**Description**

Contemporary social network providing customers with ability to share common travelling experience. Microservice architecture (HTTP REST API based interaction) consisted of main service and statistics service. Technology stack – Docker, Spring Boot 2.7.3, Postgres, H2 (testing purpose only), Feign, Hibernate, Query DSL.

**Feature**

As an additional feature, the functional capability of adding "like/dislike" to events by authorized users has been selected.
The functionality is implemented by adding a linking table between users and events with a composite primary key, ensuring the uniqueness of the relationship between entities.
The event rating is automatically calculated based on the number of likes.
Only published events are subject to assessment. The functional capability of sorting events by rating in search results has been added.

**Database schema**

![Explore with me database schema](/assets/images/db_scheme.png)

