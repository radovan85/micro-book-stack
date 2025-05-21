Micro-Book-Stack
🚀 Scalable Microservice Architecture for Books, Genres, and Images

Overview
Micro-Book-Stack is a modular, scalable microservice system designed for book management, featuring genre classification, optional image attachments, and secure access control. Built using Spring MVC and Play Framework, it ensures secure authentication, efficient CRUD operations, and inter-service communication via NATS message broker, REST Template, and WebSocket Client.

The system consists of five independent microservices, supporting high-performance operations.

📌 Default ports are customizable based on your setup.

Architecture
🔹 5 Microservices:

📖 Book-Service (Play Framework) → Handles book CRUD operations

📷 Image-Service (Play Framework) → Manages book images

🎭 Genre-Service (Play Framework) → Manages genre classification

🔐 Auth-Service (Spring MVC) → Manages user authentication & roles

⚙ Gateway-Service (Spring MVC) → Entry point, request routing (Default port: 8082)

🔹 Communication Methods:

NATS Message Broker → Event-based async messaging

REST Template → Synchronous HTTP requests between services

WebSocket Client → Real-time updates

🔹 Secure access control using Spring Security & JWT 🔹 Consul for service discovery and health checks 🔹 Optimized PostgreSQL database management with Hibernate ORM

📌 Note: Default ports can be changed based on your setup.

Tech Stack
✅ Java – Core language for all services ✅ Spring MVC – Used for Gateway-Service & Auth-Service ✅ Play Framework – Used for Book-Service, Image-Service & Genre-Service ✅ Spring Security – Role-based access management ✅ JWT – JSON Web Tokens for authentication ✅ Hibernate – ORM for database interaction ✅ PostgreSQL – Relational database for persistent storage ✅ NATS – Message broker for async communication ✅ REST Template – Synchronous HTTP communication ✅ WebSocket Client – Real-time event handling ✅ Consul – Service discovery & health monitoring

Features
✔ Complete book management system – CRUD operations for books, genres, and images ✔ Asynchronous processing – Events published via NATS message broker ✔ Synchronous service calls – Using REST Template for inter-service communication ✔ WebSocket Client integration – For real-time updates ✔ Secure authentication & authorization – Managed via Spring Security & JWT ✔ Microservices scalability – Designed for efficient service communication ✔ Consul integration – For automatic service discovery and health checks

Contact & Contributions
🚀 Micro-Book-Stack Team 💡 Built with ❤️ by passionate developers!

📌 For questions or collaboration, contact: ✉ Milan Radovanović → philip_rivers85@yahoo.com
