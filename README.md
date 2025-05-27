🚀 Micro-Book-Stack – Full-Stack Microservice Architecture for Books, Genres, and Images
Overview
Micro-Book-Stack is a modular, scalable full-stack system for managing books, offering genre classification, optional image attachments, and secure access control. It combines backend microservices (Spring MVC, Play Framework) with a modern Angular frontend, ensuring an intuitive user experience. The system provides secure authentication, efficient CRUD operations, inter-service communication via NATS message broker and REST Template, and now supports Prometheus and Grafana for performance monitoring and data visualization.

📌 Fully integrated backend and frontend for an intuitive user experience! 📌 Default ports can be customized based on your setup!

🏗 Architecture
🔹 Full-Stack: Backend + Angular Frontend
🔹 Backend Microservices:
📖 Book-Service (Play Framework) → Handles book CRUD operations 📷 Image-Service (Play Framework) → Manages book images 🎭 Genre-Service (Play Framework) → Handles genre classification 🔐 Auth-Service (Spring MVC) → Manages user authentication & roles ⚙ Gateway-Service (Spring MVC) → Entry point, request routing (Default port: 8080)

🔹 Angular Frontend → Responsive and dynamic UI for smooth user interaction (Default port: 4200)
🔹 Inter-Service Communication
✅ NATS Message Broker → Event-based asynchronous messaging ✅ REST Template → Synchronous HTTP requests between services

🔹 Monitoring & Observability
✅ Prometheus → Automatic collection of microservice metrics and performance data ✅ Grafana → Data visualization and real-time dashboards (Default port: 3000)

🔹 Security Features
✅ Spring Security & JWT → Secure authentication and authorization ✅ Consul → Service discovery and health monitoring ✅ PostgreSQL → Optimized database management with Hibernate ORM

📌 The default port for the main application is now 8080!

🛠 Tech Stack
✅ Java – Core language for all backend services ✅ Spring MVC – Used in Gateway-Service & Auth-Service ✅ Play Framework – Backend for Book-Service, Image-Service & Genre-Service ✅ Angular – Powerful frontend for seamless user interaction ✅ TypeScript – Strongly typed programming for the frontend ✅ Spring Security & JWT – Role-based access control ✅ Hibernate & PostgreSQL – Optimized database interaction ✅ NATS & REST Template – Asynchronous and synchronous service communication ✅ Consul – Automatic service discovery ✅ Prometheus & Grafana – Advanced performance monitoring, metrics, and visualization

⭐ Key Features
✔ Full-Stack Integration – Complete system with backend microservices and a frontend application ✔ Modular Architecture – Independent microservices for books, images, and genres ✔ Secure Authentication & Authorization – Managed via Spring Security & JWT ✔ Asynchronous Processing – Event-based communication via NATS message broker ✔ Synchronous Service Calls – REST Template for direct inter-service communication ✔ Microservices Scalability – Designed for efficient service interaction ✔ Automated Service Discovery – Consul ensures reliable service health monitoring ✔ Monitoring & Analytics – Prometheus tracks metrics, Grafana visualizes data

📩 Contact & Contributions
🚀 Micro-Book-Stack – Developed with dedication and expertise!

📌 For questions or collaboration, contact: ✉ Milan Radovanović → philip_rivers85@yahoo.com
