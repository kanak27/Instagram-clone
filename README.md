# Instagram Clone - Android App

This project is an Android application that emulates Instagram, allowing users to view and share images. It uses a Parse Server hosted on AWS EC2 for backend services, ensuring a scalable and reliable platform.

## Features

- **View Posts**: Browse a feed to see posts from other users.
- **Create Posts**: Share your own images with captions.
- **User Authentication**: Sign up and log in to manage your account and posts.
- **Scalable Backend**: Utilizes Parse Server on AWS EC2 for robust backend operations.

## Technologies Used

- **Android Development**: Java/Kotlin and Android Studio
- **Backend**: Parse Server hosted on AWS EC2
- **Database**: MongoDB (managed by Parse Server)
- **Storage**: EC2 instance storage for images
- **Authentication**: Managed by Parse Server

## Getting Started

### Prerequisites

- **Android Studio** installed on your development machine.
- **Java Development Kit (JDK)** installed.
- **AWS Account** with EC2 instance configured.
- **MongoDB Instance** (local or hosted).

### Setup Instructions

#### 1. Clone the Repository

Clone the project from GitHub to your local machine.

```bash
git clone https://github.com/your-username/Instagram-clone.git
cd Instagram-clone
```

#### 2. Open the Project in Android Studio

- Open **Android Studio**.
- Select **Open an existing Android Studio project**.
- Navigate to the cloned repository and select the project folder.

#### 3. Configure AWS EC2

- Create an AWS account and set up an EC2 instance.
- Install Parse Server on your EC2 instance. You can follow [this guide](https://docs.parseplatform.org/parse-server/guide/#getting-started) for detailed instructions.
- Ensure your EC2 instance has the necessary security groups to allow incoming connections.
- Obtain your EC2 instance's public DNS or IP address.

#### 4. Set Up Parse Server

- Deploy a Parse Server instance on your EC2 instance.
- In the `app/src/main/res/values/strings.xml` file, update the placeholders with your Parse Server details:

```xml
<string name="parse_server_url">http://your-ec2-instance-public-dns-or-ip:1337/parse</string>
<string name="parse_app_id">your-parse-app-id</string>
<string name="parse_client_key">your-parse-client-key</string>
```

#### 5. Configure MongoDB

- Ensure your MongoDB instance is accessible by the Parse Server.
- Update your Parse Server configuration to use your MongoDB connection string.

#### 6. Build and Run the Application

- Connect your Android device or start an emulator.
- Click on the **Run** button in Android Studio to build and install the app on your device.

## Usage

- **Sign Up / Log In**: Create a new account or log in with existing credentials.
- **View Posts**: Browse the feed to see posts from other users.
- **Create Posts**: Use the "Share" feature to upload images.

## Deployment

To deploy the app to users:

1. Ensure all Parse Server and AWS EC2 configurations are correctly set up for production.
2. Generate a release build of the app.
3. Distribute the APK file or publish it to the Google Play Store.

## Contributing

We welcome contributions! To contribute:

1. Fork the repository.
2. Create a new branch (``git checkout -b feature/your-feature-name``).
3. Make your changes and commit them (``git commit -m 'Add some feature'``).
4. Push to the branch (``git push origin feature/your-feature-name``).
5. Open a pull request.

Please make sure your code follows the project's coding standards and includes relevant tests.

## Contact

For any questions, issues, or suggestions, please open an issue or contact the project maintainer at [kanaklohiya.lohiya@gmail.com].
