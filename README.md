Micro-Book-Stack 🚀 Full-Stack Microservice Architecture for Books, Genres, and Images
Overview
Micro-Book-Stack is a modular, scalable full-stack system for book management, offering genre classification, optional image attachments, and secure access control. It combines backend microservices (Spring MVC, Play Framework) with a modern Angular frontend, ensuring a seamless user experience. The system provides secure authentication, efficient CRUD operations, and inter-service communication via NATS message broker and REST Template.

📌 Fully integrated backend and frontend for an intuitive user experience! 📌 Default ports can be customized based on your setup.

Architecture
🔹 Full-Stack: Backend + Angular Frontend 🔹 5 Backend Microservices: 📖 Book-Service (Play Framework) → Manages book CRUD operations 📷 Image-Service (Play Framework) → Manages book images 🎭 Genre-Service (Play Framework) → Handles genre classification 🔐 Auth-Service (Spring MVC) → Manages user authentication & roles ⚙ Gateway-Service (Spring MVC) → Entry point, request routing (Default port: 8082)

🔹 Angular Frontend → Responsive and dynamic UI for smooth user interaction

🔹 Inter-Service Communication: ✅ NATS Message Broker → Event-based asynchronous messaging ✅ REST Template → Synchronous HTTP requests between services

🔹 Security Features: ✅ Spring Security & JWT → Secure authentication and authorization ✅ Consul → Service discovery and health monitoring ✅ Optimized PostgreSQL database management with Hibernate ORM

📌 Note: Default ports can be adjusted according to your infrastructure.

Tech Stack
✅ Java – Core language for all backend services ✅ Spring MVC – Used in Gateway-Service & Auth-Service ✅ Play Framework – Backend for Book-Service, Image-Service & Genre-Service ✅ Angular – Powerful frontend for seamless user interaction ✅ TypeScript – Strongly typed programming for the frontend ✅ Spring Security – Role-based access control ✅ JWT – JSON Web Tokens for authentication ✅ Hibernate – ORM for efficient database interaction ✅ PostgreSQL – Relational database for persistent storage ✅ NATS – Message broker for asynchronous service communication ✅ REST Template – Synchronous HTTP communication between microservices ✅ Consul – Automatic service discovery and health checks

Features
✔ Full-Stack Integration – Complete system with backend microservices and a frontend application ✔ Modular Backend Architecture – Independent microservices for books, images, and genres ✔ Secure Authentication & Authorization – Managed via Spring Security & JWT ✔ Asynchronous Processing – Events published via NATS message broker ✔ Synchronous Service Calls – REST Template for direct inter-service communication ✔ Microservices Scalability – Designed for efficient service interaction ✔ Automated Service Discovery – Consul ensures reliable service health monitoring

Contact & Contributions
🚀 Micro-Book-Stack – Developed with dedication and expertise!

📌 For questions or collaboration, contact: ✉ Milan Radovanović → philip_rivers85@yahoo.com
