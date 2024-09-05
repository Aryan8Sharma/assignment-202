How to make a Microservice from scratch using JAVA, Spring Boot and Docker!
---
## **Step-by-Step Journey of Building "Hello World" Microservices Project

**1. Objective and Initial Setup**
The objective of this project was to develop two microservices: 
- `Hello Service`: returns the string "Hello"
- `World Service`: returns the string "World"

These two microservices were containerized using Docker, pushed to Docker Hub, and finally deployed on Kubernetes.

---
### **2. Setting Up the Development Environment**

#### **Programming Language and Framework Selection:**
I used **Java with Spring Boot** to implement the microservices. Spring Boot is easy and straightforward to work with when developing REST APIs.

1. **Installed Tools:**
	- JDK 
	- Spring Boot 
	- Docker 
	- Kubernetes using Minikube 

#### **Git Repository Setup:**
To track the project progress, I initialized a Git repository and added all project files:
- Initialized Git:
  ```bash
  git init
  ```
- Created a GitHub repository to store code:
  ```bash
  git remote add origin https://github.com/Aryan8Sharma/assignment-202.git
  ```

---

### **3. Creating the Microservices**

#### **Hello Service:**

1. Created a simple Spring Boot service:
Managed to create a Spring Boot project via the Spring Initializer. The dependencies added were not limited to adding `Spring Web` for creating REST APIs.

2. Hello Service Example :

```java
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
```
return "Hello";
       }
   }
   ```
   
#### **World Service:**

1. Secondly, a Spring Boot service was created based on the above directions.
   - It included an endpoint `GET /world` which returns a `String "World"`.

2. The World Service code is depicted below:

   ```java
   @RestController
   public class WorldController {
@GetMapping("/world")
       public String sayWorld() {
           return "World";
       }
   }
   ```
 
---
 
### **4. Containerization of Microservices Using Docker**
 
After running both services successfully locally, the next step was to containerize them using Docker.
 
#### **Dockerfile Creation:**
Each of these services had a corresponding `Dockerfile` which packaged the application into a Docker Image. Here is what the `Dockerfile` looks for both of these services:

```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the jar file built by Maven into the Docker image
COPY target/assignment1-microservice-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Command to run the application inside the container
ENTRYPOINT ["java", "-jar", "app.jar"]
``` 

#### **Challenges Overcome During Docker Setup:**

1. **Dockerfile Conflict Merge:**
When creating the Dockerfile, I faced a merge conflict-which included the sections <<<<<<< HEAD, =======, and >>>>>>>. Since there had been a conflict between the two branches, I did need to manually resolve it by selecting the correct version and deleting the markers that represented the conflict.

2. **Error with Wrong Tagging:
Initially, when tagging a Docker image, it returned an error stating `invalid tag format`. Well, this occurred simply because I had forgotten to correctly tag the image. Solution: I fixed it by providing proper syntax while tagging the images.

   **Solution:** The Docker tag command should be revised to the following:
   ```
docker tag hello-service aryansharma001/hello-service:latest
```

---

**5. Creating and Pushing Docker Images to Docker Hub**

After the Dockerfile setup, I created and pushed the Docker images to Docker Hub.

#### **Building the Docker Images:**

```bash
docker build -t aryansharma001/hello-service:latest .
docker build -t aryansharma001/world-service:latest .
```

#### **Pushing Images to Docker Hub:**

```bash
docker push aryansharma001/hello-service:latest
docker push aryansharma001/world-service:latest
```

#### **Problems Faced While Pushing:**

1. **Docker Image missing in Docker Hub
Even then, though, the image didn't show up in Docker Hub. I found that this was an issue with tagging the image. After fixing the tagging issue and re-pushing, it finally showed up on Docker Hub.

---
### **6. Deploying the Application on Kubernetes**

With the Docker images uploaded to Docker Hub, the next step was to deploy the application's microservices to a Kubernetes cluster via Minikube.

#### **Minikube Setup:**
I fired up a local Kubernetes cluster using Minikube. Once Minikube was up, I went ahead and created all Kubernetes manifests for both services.

1. **Hello Service Deployment YAML (`hello-deploy.yaml`)**:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: hello-deploy
spec:
  replicas: 1
  selector:
matchLabels:
         app: hello
     template:
       metadata:
         labels:
           app: hello
       spec:
         containers:
- name: hello-container
          image: aryansharma001/hello-service:latest
          ports:
          - containerPort: 8080
   ```
 



2. **Service YAML for Hello Service:**

   ```yaml
   apiVersion: v1
   kind: Service
metadata:
    name: hello-service
  spec:
    type: NodePort
    selector:
      app: hello
    ports:
    - protocol: TCP
      port: 8080
      targetPort: 8080
      nodePort: 30001
Created similar files for the `world-service` and applied them using the `kubectl` command.
```bash
kubectl apply -f hello-deploy.yaml
kubectl apply -f world-deploy.yaml
```

---
### **7. Testing the Microservices**
#### **Accessing the Services via Minikube:**
Once deployed, I used Minikube to get the cluster IP and NodePort to access the services:
```bash
minikube ip
```
Then I accessed both services using `curl`:
```bash
curl http://<minikube-ip>:30001/hello
curl http://<minikube-ip>:30002/world
```
#### **Problems Faced:**
1. **Connection Issues to NodePort Services:**
At the beginning, the services were unreachable from the outside via NodePort. A bit of debugging afterwards taught me, that I needed running **Minikube tunnel** to forward the NodePort services correctly.

**Solution:** I started the Minikube tunnel:

```bash
minikube tunnel
```

Once the tunnel was up, I was able to reach the services without any problems.

---
### **8. Integration and Final Testing

Finally, I had created a simple integration test invoking both services and printing "Hello World".

```bash
hello=$(curl -s http://<minikube-ip>:30001/hello)
world=$(curl -s http://<minikube-ip>:30002/world)
echo "$hello $world"
```

---
### **9. Conclusion and Documentation**

Once both the services were deployed, I documented the entire process along with the Docker Hub links for the images:

* **Hello Service:** `https://hub.docker.com/r/aryansharma001/hello-service`
* **World Service:** `https://hub.docker.com/r/aryansharma001/world-service`

I also listed the Docker pull commands in the README for future reference.
