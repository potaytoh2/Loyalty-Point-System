<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
<!-- ABOUT THE PROJECT -->
## About The Project
The following project is an IT Solution Architecture designed for managing and automating administrative and loyalty program operations in a business environment. The solution employs AWS cloud infrastructure to create a robust, scalable, and secure architecture that handles user authentication, point balance management, and logging activities through a microservices setup. It ensures high availability, fault tolerance, and security while providing seamless integration with third-party services for enhanced operational efficiency.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Notice

**Please Note:** This project is not meant for cloning and setting up locally for individual use. It serves a specific purpose and is intended for reference or contribution purposes only. For any inquiries or collaboration opportunities, please contact the project maintainers.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## AWS Architecture

![AWS-Architecture][aws]

The architecture includes various AWS services such as Amazon RDS, DynamoDB, Lambda, API Gateway, Elastic Load Balancing, and ECS for orchestrating microservices. 

Amazon RDS (Relational Database Service) plays a pivotal role in managing user and points data, offering scalable transaction handling and queries, along with automated backups, patch management, and failover capabilities that guarantee data integrity and availability. Similarly, Amazon DynamoDB is utilized for storing logs, providing fast and predictable performance with automatic data replication across multiple availability zones, ensuring durability and availability.

For handling asynchronous tasks such as sending email notifications, AWS Lambda is employed, which significantly reduces operational costs by charging only when the code is executed and scales automatically in response to each trigger. This is complemented by Amazon API Gateway, which manages all inbound API requests, bolstering security with built-in tools like AWS WAF to protect against common web exploits and providing efficient API management and monitoring.

The Elastic Load Balancing (ELB) is integral to distributing incoming application traffic across Amazon EC2 instances, enhancing fault tolerance and scalability by ensuring that only healthy instances receive traffic. It adjusts automatically to the incoming application traffic, distributing it across instances to maintain steady and optimal performance. Amazon ECS (Elastic Container Service) further aids in orchestrating the containerized microservices, simplifying deployment, scaling, and management of container applications and ensuring efficient use of computational resources.

AWS IAM (Identity and Access Management) ensures robust security measures by managing access to AWS services and resources securely, permitting only authenticated and authorized entities to access the resources. Additionally, AWS CloudWatch is used for monitoring the applications and services, offering insights into application performance and operational health, along with alerting mechanisms that notify or automatically adjust resources based on specific rules.

Together, these AWS components create a solid architecture that not only fulfills the non-functional requirements essential for our project but also aligns with our goals of delivering a resilient and efficient cloud-based solution. This architecture allows our application to remain performant and responsive as it scales, gracefully handles failures, and secures sensitive data and operations against potential threats, illustrating our commitment to implementing best practices in cloud architecture and operational excellence.
<!-- USAGE EXAMPLES -->
## Usage

This section showcases the UI/UX of the frontend

1. **Sign in**
![sign-in][sign_in]

2. **home**
![home][home]

3. **logs**
![logs][logs]

4. **Edit and Enroll user**
![edit][edit]
![enroll_user][enroll_user]

5. **Adjust points**
![points][points]
<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[sign_in]: images/sign_in.png
[edit]: images/edit_user.png
[enroll_user]: images/enroll_user.png
[home]: images/home.png
[points]: images/adjust_points.png
[logs]: images/logs.png 
[aws]: images/aws.png

