# Spring boot - automated pipeline setup and deployment for a spring boot application

This guide outlines the setup of a Jenkins pipeline on an AWS EC2 instance, including Jenkins installation, Rancher CLI configuration, GitHub webhook integration, and Kubernetes deployment for a Spring Boot application. Additionally, it covers project initialization, Dockerization, and the use of AWS RDS for database management.

# Jenkins

### Creating Jenkins pipeline:

1. Create a t2.medium Ec2 instance; 
    1. [https://www.jenkins.io/doc/tutorials/tutorial-for-installing-jenkins-on-AWS/#creating-a-security-group](https://www.jenkins.io/doc/tutorials/tutorial-for-installing-jenkins-on-AWS/#creating-a-security-group)
    2. Add a custom inbound rule for 8080 as mentioned above
2. Associate a Elastic Ip to it
3. Installing and configuring Jenkins:
    1. Install Java:
        
        ```bash
        sudo apt update
        sudo apt install openjdk-17-jre
        java -version
        ```
        
    2. Install Jenkins:
        1. [https://www.jenkins.io/doc/book/installing/linux/](https://www.jenkins.io/doc/book/installing/linux/)
        
        ```bash
        sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
          https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
        echo "deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc]" \
          https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
          /etc/apt/sources.list.d/jenkins.list > /dev/null
        sudo apt-get update
        sudo apt-get install jenkins
        ```
        
    3. Checking the installation and enabling Jenkins on boot:
        1. 
        
        ```bash
        sudo systemctl enable jenkins
        sudo systemctl start jenkins
        sudo systemctl status jenkins
        ```
        
    4. Jenkins is available at
        1. `http://<your_server_public_DNS>:8080` 
    5. Installing Rancher CLI on jenkins node:
        1. [https://ranchermanager.docs.rancher.com/reference-guides/cli-with-rancher/rancher-cli](https://ranchermanager.docs.rancher.com/reference-guides/cli-with-rancher/rancher-cli)
        2. Commands:
            1. 
            
            ```bash
            wget https://github.com/rancher/cli/releases/download/v2.8.3/rancher-linux-amd64-v2.8.3.tar.gz
            tar -xzvf rancher-linux-amd64-v2.8.3.tar.gz
            cd rancher-v2.8.3/
            chmod +x rancher
            sudo mv rancher /usr/local/bin/
            ```
            
        3. Verifying the installation:
            1. `rancher --version`
    6. Generate the API key and bearer token for the rancher cli

### Set Up GitHub Webhook for Jenkins:

To have Jenkins automatically build when you push to GitHub:

1. Go to **Settings** > **Webhooks** in your GitHub repository you want to run the pipeline for.
2. Click **Add webhook**.
3. For **Payload URL**, use **`http://<JENKINS_URL>/github-webhook/`** (replace **`<JENKINS_URL>`** with your actual Jenkins server's URL).
4. Select **application/json** for the content type.
5. Choose **Just the push event**.
6. Check **Active** and create the webhook.

### Jenkins pipeline Setup

- Create a job
- Select `free style project`
- Give a description
- choose github project
- add the github url 
- choose git as SCM
- add repo url
- choose credentials which are already stored in Jenkins Credentials
- make branch to blank so that it will take the default as main.
- choose github hook trigger.
- then add the docker build job as a shell script
    
    ```bash
    export DB_PASSWORD=$db_password
    mvn clean package
    echo "completed mvn build"
    docker build -t spring_crud_app:latest .
    echo "completed docker build"
    docker login -u $docker_usr -p $docker_pat
    docker tag spring_crud_app:latest uddeepyalamanchili/spring_crud_app:latest
    docker push uddeepyalamanchili/spring_crud_app:latest
    docker image rm uddeepyalamanchili/spring_crud_app:latest
    ```
    
    1. **Export Environment Variable**: The first line exports **`DB_PASSWORD`** as an environment variable that holds the database password, which is be used by the application.
    2. **Maven Build**: The **`mvn clean package`** command cleans the previous build artifacts and compiles the Java project, packaging it into an executable form (like a **`.jar`**).
    3. **Docker Image Build**: The **`docker build`** command creates a Docker image named **`spring_crud_app:latest`** using the Dockerfile in the current directory.
    4. **Docker Hub Login and Image Push**: The script logs into Docker Hub with the specified username and password, tags the built image with a specific repository name, and pushes it to Docker Hub.
    5. **Cleanup**: The final command removes the tagged Docker image from the local machine to free up space after it's been pushed to the repository.

• then next job is to deploy on kubernetes

```bash
rancher login <RANCHER_URL> --token $rancher_tok --skip-verify --context <CONTEXT>
num_pods=$(rancher kubectl get pods --no-headers -o custom-columns=":metadata.name" | wc -l)
echo $num_pods
if [[ $num_pods -gt 0 ]]
then
rancher kubectl delete ingress surveyformingress
rancher kubectl delete deployment surveyformapi
rancher kubectl delete svc surveyformclusterip
fi
rancher kubectl apply -f deployment.yaml
rancher kubectl apply -f clusterip.yaml
rancher kubectl apply -f myingress.yaml
```

• this script will connect to our cluster via rancher, remove the old deployment if exists and deploy with the updated yaml files

# Springboot: Development and Deployment of a Spring Boot Application

### **Project Initialization and Configuration**

1. **Spring Boot Starter Generation**:
    - Utilize the Spring Initializer available at [https://start.spring.io/](https://start.spring.io/) to create the base structure of the Spring Boot application. This platform facilitates specifying project metadata and adding necessary dependencies, such as database drivers for connectivity.
    - Ensure to include dependencies for the chosen database and JDBC to integrate database operations within the application.
2. **Project Setup in Eclipse IDE**:
    - Download and open the generated project file in Eclipse, treating it as a Maven project. This involves importing the project and resolving any dependencies identified by Maven.
    - Explore Spring Boot basics and setup guides available at [https://spring.io/guides/gs/spring-boot](https://spring.io/guides/gs/spring-boot) and [https://spring.io/guides/gs/rest-service](https://spring.io/guides/gs/rest-service) for RESTful web service implementation.

### **Development of RESTful Services and Data Access**

1. **RESTful API Development**:
    - Follow the guide at [https://spring.io/guides/gs/accessing-data-mysql](https://spring.io/guides/gs/accessing-data-mysql) to create data-accessible RESTful services.
    - Reference the Jakarta Persistence API documentation at [https://jakarta.ee/specifications/persistence/2.2/apidocs/javax/persistence/package-summary](https://jakarta.ee/specifications/persistence/2.2/apidocs/javax/persistence/package-summary) to understand entity and persistence management.
2. **Spring Boot Application Structure**:
    
- Develop the application across several layers, namely Model, Repository, Service, Controller, and Exception Handling:
    - **Model**: Define entity models that reflect database structures.
    - **Repository**: Use Spring Data JPA to abstract database interactions.
    - **Service and Implementation**: Create services to handle business logic.
    - **Controller**: Manage HTTP request mappings and responses.
    - **Exception Handling**: Implement global exception handling for cleaner error management.
- For an architectural overview, refer to internal documentation and guides that explain the layered structure, focusing on components like Hibernate ORM and controller roles.

### **Building and Dockerizing the Application**

1. **Project Compilation**:
    - Use Maven to build the project. Execute **`./mvnw clean package`** on Windows or **`mvn clean package`** on Linux to compile the application and generate an executable JAR file.
    - Execute the generated jar using java.
    - `mvn spring-boot:run`  to the springboot application
2. **Docker Integration**:
    - Dockerize the Spring Boot application following instructions from [https://www.geeksforgeeks.org/dockerize-spring-boot-application-with-postgressql/](https://www.geeksforgeeks.org/dockerize-spring-boot-application-with-postgressql/).
    - Build the Docker image using:
        
        ```bash
        
        docker build -t spring_crud_app:latest .
        ```
        
    - Run the application within a Docker container to ensure isolation and ease of deployment:
        
        ```bash
        docker run -d -p 8080:8080 --name my_spring_app spring_crud_app:latest
        ```
        
    - Manage Docker images efficiently by tagging and pushing them to a registry for accessibility:
        
        ```bash
        docker tag spring_crud_app:latest your_docker_hub_account/spring_crud_app:latest
        docker push your_docker_hub_account/spring_crud_app:latest
        ```

# Kubernetes


### **Overview**

This details the process of setting up Rancher on a single node using Docker, deployed on an AWS EC2 instance. The instance chosen for this purpose is a t2.medium running Ubuntu. Rancher will serve as a management layer for Kubernetes clusters.

### **Environment Setup**

1. **Instance Configuration**:
    - **Type**: t2.medium
    - **Memory**: 26 GB
    - **OS**: Ubuntu (AMI used in instance creation)
2. **AWS EC2 Launch**:
    - The EC2 instance is launched using the AWS Management Console.
    - A medium instance type is selected according to the requirements.
3. **Access Configuration**:
    - Secure access is configured using SSH. The connection command, obtained from the AWS connect portal, is:
        
        ```bash
        
        ssh -i "rancherKeypair.pem" <HOST>
        
        ```
        

### **Installation Process**

1. **System Updates and Docker Installation**:
    - Update system packages:
        
        ```bash
        
        sudo apt-get update
        
        ```
        
    - Install Docker:
        
        ```bash
        
        sudo apt install docker.io
        ```
        
    - Verify Docker installation:
        
        ```bash
        
        docker -v
        
        ```
        
2. **Rancher Installation**:
    - Run Rancher in a Docker container with necessary port mappings and privileges:
        
        ```bash
        
        sudo docker run --privileged=true -d --restart=unless-stopped -p 80:80 -p 443:443 rancher/rancher
        
        ```
        

### **Configuration and Access**

1. **Initial Access and Setup**:
    - The initial bootstrap password for Rancher is retrieved from the logs of the running container. This is done by first identifying the container ID and then extracting the password:
        
        ```bash
        
        docker ps
        docker logs <container-id> 2>&1 | grep "Bootstrap Password:"
        
        ```
        
2. **Accessing Rancher Dashboard**:
    
    
    - Rancher is accessible via the public IP address assigned to the EC2 instance.
        
    - Initial connection attempts might prompt a security warning due to SSL certificate verification issues. It's necessary to proceed despite these warnings to access the dashboard.

### **Setup of EC2 Instance for Kubernetes**


1. **EC2 Configuration for Kubernetes Nodes**:
    - **Instance Type**: t2.medium
    - **Storage**: 26GB EBS
    - **Operating System**: Ubuntu
2. **Software Installation and Configuration**:
    - Update existing packages on the instance:
        
        ```bash
        
        sudo apt-get update -y
        ```
        
    - Install Docker, which is required for container management:
        
        ```bash
        
        sudo apt-get install docker.io -y
        
        ```
        
3. **Integrating with Rancher**:
    - Execute the Rancher agent installation script to connect the Kubernetes node to the Rancher-managed cluster
    - Navigate to the Rancher dashboard and initiate the creation of a new Kubernetes cluster:
        - Choose a custom cluster type.
        - Follow the prompts and enter the registration command with the **`-insecure`** flag to avoid SSL validation, necessary for internal communications:
            
            ```bash
             
            curl --insecure -fL https://54.235.151.124/system-agent-install.sh | sudo sh -s - --server https://54.235.151.124 --token [token] --ca-checksum [checksum] --etcd --controlplane --worker
            
            ```
            
        - Execute the provided command on the EC2 instance designated for Kubernetes.
4. **Cluster Activation and Deployment**:
    - After executing the installation command, the cluster will register and become active within Rancher.
    - Deploy Kubernetes applications by uploading YAML files via the Rancher dashboard.

## Ingress

1. We have used the rancher to install Kubernetes, which installs one `Ingress Controller` by default. 
2. Then we have used `ec2 ipv4 DNS` as the hostname in the ingress without SSL.
3. Ingress is connected to backend with a `clusterip` 
    1. We have used following YAML definition to deploy the ClusterIP
    
    ```yaml
    apiVersion: v1
    kind: Service
    metadata:
      name: surveyformclusterip
      namespace: default
    spec:
      selector:
        app: surveyformapi
      ports:
      - name: http
        port: 8080
        targetPort: 8080
      type: ClusterIP
    ```
    

1. Then we have added all the paths to ingress to allow the user to connect to backend using the ClusterIP service.
2. Following is our Ingress definition.
    
    ```yaml
    apiVersion: networking.k8s.io/v1
    kind: Ingress
    metadata:
      name: surveyformingress
      namespace: default
    spec:
      rules:
        - host: <HOST_NAME>
          http:
            paths:
              - backend:
                  service:
                    name: surveyformclusterip
                    port:
                      number: 8080
                path: /api/students
                pathType: ImplementationSpecific
                - backend:
                  service:
                    name: surveyformclusterip
                    port:
                      number: 8080
                path: /api/students/.*
                pathType: ImplementationSpecific        
    ```
    

# RDS

1. We have used `FREE Tier` RDS with MySQL instance as our database for the RESTful API.
2. **Pre-requisite:** First make sure you have two subnets deployed on two different Availability Zones.
3. Search and Go to `RDS` in the AWS Home page.
4. Go to `CREATE DATABASE` and choose `Easy Create`.
5. Choose `MYSQL` as the engine option.
6. In the `Templates` section choose `Free Tier`.
7. In the settings change `Database Identifier` to the name of your database.
8. Choose `Self managed` in the `Credential Settings`.
9. Give `Master Password` of your choice and retype the same in the below box.
10. Leave the remaining to default and click on `Create`.        